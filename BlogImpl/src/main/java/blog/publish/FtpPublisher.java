package blog.publish;

import blog.Checksum;
import blog.Config;
import blog.SiteMetadata;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpPublisher extends Publisher {
    private static final Logger logger = LoggerFactory.getLogger( FtpPublisher.class );
    private static final long SLEEP_TIME = 500;
    
    private FTPClient ftp;
    private Config config;
    private SiteMetadata site;
    private File siteDir;
    
    public FtpPublisher( File siteDir, Config config, SiteMetadata site ) throws IOException{
        super( getRoot( config ) );
        this.siteDir = siteDir;
        this.config = config;
        this.site = site;
    }
    
    private static String getRoot( Config config ) {
        String dir = config.getFtpDirectory();
        if( !StringUtils.isBlank( dir ) ) {
            if( dir.charAt( 0 ) != '/' ){
                dir = "/" + dir;
            }
            return dir;
        }
        return "/";
    }

    public void publish() {
        try {
            if( ftp == null ){
                ftp = connect();
            }

            
            
            String checksumFile = getRoot( config );
            String feed = site.getFeed();
            if( !StringUtils.isBlank( feed ) ){
                checksumFile += "/" + feed;
            }
            checksumFile += "/checksums.json";

            logger.info( "Looking for checksums: " + checksumFile );
            InputStream input = ftp.retrieveFileStream( checksumFile );
            if( input != null ){
                String checksumJson = IOUtils.toString( input );
                IOUtils.closeQuietly( input );
                
                if( !StringUtils.isBlank( checksumJson ) ){
                    Gson gson = new Gson();
                    Checksum checksums = gson.fromJson( checksumJson, Checksum.class );

                    logger.info( "    got checksums; doing partial push" );
                    doPartialPush( siteDir, checksums );
                    ftp.logout();
                    
                    return;
                }
            }
            
            logger.info( "No checksums found; doing complete push" );
            doFullPush( siteDir );
            ftp.logout();
        }
        catch( Throwable t ) {
            logger.error( "Error publishing site", t );
        }
        finally{
            if( ftp != null ){
                try{
                    ftp.logout();
                }
                catch( Throwable t ){
                    logger.error( "Error closing FTP client", t );
                }
            }
        }
    }
    
    private FTPClient connect() throws IOException {
        FTPClient ftp;
        
        String host = config.getFtpHost();
        int port = config.getFtpPort();
        String user = config.getFtpUser();
        String pass = config.getFtpPassword();
        
        if( StringUtils.isBlank( user ) ){
            System.out.print( "FTP user name: " );
            user = System.console().readLine();
        }
        
        if( !StringUtils.isBlank( user ) && StringUtils.isBlank( pass ) ){
            System.out.print( "Password for " + user + "@" + host + ": " );
            char[] tmp = System.console().readPassword();
            pass = new String(tmp);
        }

        String proxyHost = config.getFtpProxyHost();
        if( !StringUtils.isBlank( proxyHost ) ) {
            int proxyPort = config.getFtpProxyPort();
            String proxyUser = config.getFtpProxyUser();
            
            String proxyPass = config.getFtpProxyPassword();
            ftp = new FTPHTTPClient( proxyHost, proxyPort, proxyUser, proxyPass );
        }
        else {
            ftp = new FTPClient();
        }
        
        connect( ftp, host, port, user, pass );
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return ftp;
    }
    
    private void connect( FTPClient ftp, String host, int port, String user, String pass ) throws IOException {
        if( port != -1 ){
            logger.info( "Connecting to: host: " + host + ":" + port );
        }
        else{
            logger.info( "Connecting to: host: " + host );
        }
        
        if( port == -1 ){
            ftp.connect( host );
        }
        else {
            ftp.connect( host, port );
        }
        
        if( !StringUtils.isBlank( user ) ) {
            ftp.login( user, pass );
        }
    }

    @Override
    public boolean makeRemoteDir( String dir ) throws IOException {
        if( isVerbose() ){
            logger.info( "Making remote dir: " + dir );
        }
        
        return ftp.makeDirectory( dir );
    }

    @Override
    public void writeFile( File source, String dest ) throws IOException {
        try( FileInputStream input = new FileInputStream( source ) ){
            ftp.deleteFile( dest );
            ftp.storeFile( dest, input );
            IOUtils.closeQuietly( input );
        }
    }

    @Override
    public void sleep(){
        try{
            Thread.sleep(SLEEP_TIME);
        } catch( Throwable t ){}
    }
}
