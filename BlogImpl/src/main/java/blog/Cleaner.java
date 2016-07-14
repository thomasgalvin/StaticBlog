package blog;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cleaner
{
    private static final Logger logger = LoggerFactory.getLogger( Cleaner.class );
    
    public static void clean( File root, Config config ){
        File siteDir = new File( root, config.getSite() );
        try {
            logger.info( "Cleaning site directory: " + siteDir.getAbsolutePath() );
            FileUtils.deleteDirectory( siteDir );
        }
        catch( Throwable t ){
            logger.error( "Error cleaning site directory", t );
        }
    }
}
