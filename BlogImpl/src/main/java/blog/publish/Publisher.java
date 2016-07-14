package blog.publish;

import blog.Checksum;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.io.FileUtils.checksumCRC32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Publisher
{
    private static final Logger logger = LoggerFactory.getLogger( Publisher.class );
    public static final int MAX_RETRIES = 5;
    private final String root;
    private List<String> directories = new ArrayList();
    private List<String> createdDirectories = new ArrayList();
    private boolean verbose;
    
    
    public Publisher( String root ){
        this.root = root;
    }

    //TODO: Delete checksums file after pull, retore after push complete
    
    public void doFullPush( File siteDir ) throws IOException{
        clearDirectories();
        makeRemoteDir( root );
        publish( siteDir, false, null );
    }
    
    public void doPartialPush( File siteDir, Checksum checksums ) throws IOException{
        clearDirectories();
        makeRemoteDir( root );
        publish( siteDir, false, checksums );
    }
    
    private void clearDirectories(){
        directories.clear();
        directories.add( root );
    }
    
    private void popDirectory(){
        directories.remove( directories.size() -1 );
        if( directories.isEmpty() ){
            directories.add( root );
        }
    }
    
    private void publish( File file, boolean createDir, Checksum checksums ) throws IOException{
        if( skip( file ) ){
            return;
        }
        
        String name = file.getName();
        
        if( file.isDirectory() ){
            Checksum currentCheck;
            
            if( createDir ){
                directories.add( name );
                
                makeRemoteDir( directories );
                currentCheck = getCheck( checksums, name );
            }
            else{
                currentCheck = checksums;
            }
            
            File[] children = file.listFiles();
            for( File child : children ){
                publish( child, true, currentCheck );
            }
            
            if( createDir ){
                popDirectory();
            }
        }
        else{
            Checksum destChecksum = getCheck( checksums, name );
            long sourceChecksum = destChecksum == null ? -1 : checksumCRC32( file );
            
            if( destChecksum == null
                || destChecksum.getChecksum() != sourceChecksum ) {
                String path = getFullPath( directories );
                String fileName = path;
                if( !fileName.endsWith( "/" ) && !name.startsWith( "/" ) ){
                    fileName += "/";
                }
                fileName += name;
                
                if( isVerbose() ){
                    if( destChecksum != null ){
                        logger.info( "Writing: src: (" + sourceChecksum + ") " + file.getAbsolutePath()  );
                        logger.info( "        dest: (" + destChecksum.getChecksum() + ") " + fileName  );
                    }
                    else{
                        logger.info( "Writing: src: " + file.getAbsolutePath()  );
                        logger.info( "        dest: " + fileName  );
                    }
                }
                writeFile( file, fileName );
            }
            else{
                if( isVerbose() ){
                    logger.info( "Found matching checksum for file: " + file.getAbsolutePath() );
                }
            }
        }
    }
    
    public boolean skip( File file ){
        return file.getName().startsWith( "." );
    }
    
    private Checksum getCheck( Checksum current, String name ) {
        if( current == null ) {
            return null;
        }

        for( Checksum check : current.getChildren() ) {
            if( name.equals( check.getName() ) ) {
                return check;
            }
        }

        return null;
    }
    
    private String getFullPath( List<String> dirs ) {
        String fullPath = "";
        for( String dir : directories ) {
            fullPath += dir + "/";
        }
        return fullPath;
    }
    
    private void makeRemoteDir( List<String> dirs ) throws IOException{
        String dirName = "";
        for( String dir : dirs ) {
            dirName += dir + "/";
        }
        
        if( createdDirectories.contains( dirName ) ){
            return;
        }
        
        makeRemoteDir( dirName );
        createdDirectories.add( dirName );
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose( boolean verbose ) {
        this.verbose = verbose;
    }
    
    public abstract boolean makeRemoteDir( String dir ) throws IOException;
    
    public abstract void writeFile( File source, String dest ) throws IOException;
    
    public abstract void sleep();
}
