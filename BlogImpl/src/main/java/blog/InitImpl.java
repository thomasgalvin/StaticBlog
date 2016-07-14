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
    private static final String TEMPLATE_ZIP = "blog/src.zip";
    public void init( File rootDir )throws IOException {
        logger.info( "Creating a skeleton blog project ..." );
        logger.info( "    Creating root directory: " + rootDir.getAbsolutePath() + " ..." );
        rootDir.mkdirs();
        
        logger.info( "    Copying data ..." );
        File tmp = SystemUtils.getRandomTempDir();
        tmp.mkdirs();
        tmp = new File( tmp, "src.zip" );
        FileOutputStream fout = new FileOutputStream( tmp );
        
        InputStream input = getClass().getClassLoader().getResourceAsStream( TEMPLATE_ZIP );
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
