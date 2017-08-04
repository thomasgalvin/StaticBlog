package blog;

import galvin.SystemUtils;
import galvin.ZipUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitImpl
{
    private static final Logger logger = LoggerFactory.getLogger( InitImpl.class );
    private static final String TEMPLATE_STANDARD_ZIP = "blog/src.zip";
    private static final String TEMPLATE_DARK_ZIP = "blog/src-dark.zip";
    private static final String TEMPLATE_45B_ZIP = "blog/src-h5b.zip";
    private static final String TEMPLATE_BOOT_ZIP = "blog/src-boot.zip";
    
    public void prompt( File rootDir ) throws Exception{
        System.out.println("Chose a theme:");
        System.out.println("  [1] Standard (simple)");
        System.out.println("  [2] Dark (simple)");
        System.out.println("  [3] HTML 5 Boilerplate");
        System.out.println("  [4] Boostrap");
        System.out.print("Theme [1]: ");
        
        String result = System.console().readLine();
        if( "2".equals(result) ){
            init( rootDir, TEMPLATE_DARK_ZIP );
        }
        else if( "3".equals(result) ){
            init( rootDir, TEMPLATE_45B_ZIP );
        }
        else if( "4".equals(result) ){
            init( rootDir, TEMPLATE_BOOT_ZIP );
        }
        else{
            init( rootDir, TEMPLATE_STANDARD_ZIP );
        }
        
    }
    
    public void init( File rootDir )throws IOException {
        init( rootDir, TEMPLATE_STANDARD_ZIP );
    }
    
    public void init( File rootDir, String zipFile )throws IOException {
        System.out.println("extracting zip file: " + zipFile);
        
        logger.info( "Creating a skeleton blog project ..." );
        logger.info( "    Creating root directory: " + rootDir.getAbsolutePath() + " ..." );
        rootDir.mkdirs();
        
        logger.info( "    Copying data ..." );
        File tmp = SystemUtils.getRandomTempDir();
        tmp.mkdirs();
        tmp = new File( tmp, "src.zip" );
        FileOutputStream fout = new FileOutputStream( tmp );
        
        InputStream input = getClass().getClassLoader().getResourceAsStream( zipFile );
        IOUtils.copy( input, fout );
        
        IOUtils.closeQuietly( input );
        IOUtils.closeQuietly( fout );
        
        logger.info( "    Unpacking source files ..." );
        ZipUtils.unzip( tmp, rootDir );
        
        logger.info( "    Cleaning up ..." );
        FileUtils.deleteDirectory( tmp.getParentFile() );
        logger.info( "Complete." );
    }
    
}
