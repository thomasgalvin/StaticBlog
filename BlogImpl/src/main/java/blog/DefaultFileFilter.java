package blog;

import java.io.File;
import java.io.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultFileFilter implements FileFilter
{
    private static final Logger logger = LoggerFactory.getLogger( DefaultFileFilter.class );
    private String[] badNames = {
        ".DS_Store"
    };
    
    private String[] badExtensions = {
        "#",
        "~",
    };
    
    @Override
    public boolean accept( File pathname ) {
        if( pathname != null ){
            if( pathname.isDirectory() ){
                return true;
            }
            
            String name = pathname.getName();
            for( String bad : badNames ){
                if( bad.equals( name ) ){
                    logger.info( "Rejected name: " + name );
                    return false;
                }
            }
            
            for( String bad : badExtensions ){
                if( name.endsWith( bad ) ){
                    logger.info( "Rejected extension: " + name );
                    return false;
                }
            }
        }
        
        return true;
    }
}
