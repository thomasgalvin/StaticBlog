package blog.publish;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemPublisher extends Publisher
{
    private static final Logger logger = LoggerFactory.getLogger( FileSystemPublisher.class );
    
    private final File targetDir;
    private File currentDir;

    public FileSystemPublisher( File targetDir ) {
        super( targetDir.getAbsolutePath() );
        this.targetDir = targetDir;
        currentDir = targetDir.getAbsoluteFile();
        currentDir.mkdirs();
    }
    
    @Override
    public boolean makeRemoteDir( String dir ) throws IOException{
        File tmp = new File( dir );
        
        if( isVerbose() ){
            logger.info( "Making directory: " + tmp.getAbsolutePath() );
        }
        
        return tmp.mkdirs();
    }
    
    @Override
    public void writeFile( File source, String dest ) throws IOException {
        if( isVerbose() ){
            logger.info( "Writing " + source.getName() + " to " + dest );
        }
        
        File destination = new File(dest);
        FileUtils.copyFile( source, destination );
    }
    
    @Override
    public void sleep(){}
}
