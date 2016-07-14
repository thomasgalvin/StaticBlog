package blog.watch;

import blog.Renderer;
import blog.RendererImpl;
import galvin.FileUpdateMonitor;
import galvin.LiveDirectoryCopy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Watcher
{
    private static final Logger logger = LoggerFactory.getLogger( Watcher.class );
    private long sleep = 1_000;
    private boolean running = false;
    private File root;
    private String configFileName;
    private FullRenderThread fullRenderer;
    private PartialRenderThread partialRenderer;
    private LiveDirectoryCopy staticWatcher;
    private LiveDirectoryCopy uploadsWatcher;
    private FileUpdateMonitor[] fullRenderMonitors;
    private FileUpdateMonitor[] partialRenderMonitors;
    private boolean verbose;
    
    public Watcher( File root, String configFileName, boolean verbose ) throws IOException {
        this.root = root;
        this.configFileName = configFileName;
        this.verbose = verbose;
        
        Renderer renderer = new RendererImpl( root, configFileName, verbose );
        
        staticWatcher = new LiveDirectoryCopy( renderer.getStaticDir(), renderer.getSiteDir(), sleep );
        uploadsWatcher = new LiveDirectoryCopy( renderer.getUploadsDir(), renderer.getSiteDir(), sleep );
        fullRenderMonitors = new FileUpdateMonitor[]{
            new FileUpdateMonitor( renderer.getDataDir() ),
            new FileUpdateMonitor( renderer.getIncludesDir() ),
            new FileUpdateMonitor( renderer.getLayoutsDir() ),
        };
        
        partialRenderMonitors = new FileUpdateMonitor[]{
            new FileUpdateMonitor( renderer.getDraftsDir() ),
            new FileUpdateMonitor( renderer.getPrivateDir() ),
            new FileUpdateMonitor( renderer.getPostsDir() ),
        };
    }
    
    public synchronized void start(){
        running = true;
        staticWatcher.start();
        uploadsWatcher.start();
        
        for( FileUpdateMonitor monitor : fullRenderMonitors ){
            monitor.updateTimestamps();
        }
        
        for( FileUpdateMonitor monitor : partialRenderMonitors ){
            monitor.updateTimestamps();
        }
        
        if( fullRenderer == null ){
            fullRenderer = new FullRenderThread();
            fullRenderer.start();
        }
        
        if( partialRenderer == null ){
            partialRenderer = new PartialRenderThread();
            partialRenderer.start();
        }
    }
    
    public synchronized void stop(){
        running = false;
        staticWatcher.stop();
        uploadsWatcher.stop();
        
        if( fullRenderer != null ){
            fullRenderer = null;
        }
        
        if( partialRenderer != null ){
            partialRenderer = null;
        }
    }
    
    private class PartialRenderThread extends Thread{
        public void run(){
            try{
                while( running ){
                    if( running ){
                        List<File> modifiedFiles = new ArrayList();
                        
                        for( FileUpdateMonitor monitor : partialRenderMonitors ){
                            modifiedFiles.addAll( monitor.modified() );
                        }
                        
                        if( running && !modifiedFiles.isEmpty() ){
                            logger.info( "Partial render triggered" );
                            Renderer renderer = new RendererImpl( root, configFileName, verbose );
                            renderer.loadTemplates();
                            
                            renderer.renderSourceFiles( modifiedFiles );
                            
                            renderer.renderHistory();
                            renderer.renderTags();
                            renderer.renderIndex();
                            renderer.renderRss();
                            renderer.renderChecksums();
                            logger.info( "Partial render complete" );
                        }
                    }
                }
            }
            catch( Throwable t ){
                logger.error( "Error in partial renderer thread", t );
            }
        }
    }
    
    private class FullRenderThread extends Thread{
        public void run(){
            try{
                while( running ){
                    if( running ){
                        boolean fullRenderTriggered = false;
                        for( FileUpdateMonitor monitor : fullRenderMonitors ){
                            fullRenderTriggered |= !monitor.modified().isEmpty();
                        }
                        
                        if( running && fullRenderTriggered ){
                            logger.info( "Full render triggered" );
                            Renderer renderer = new RendererImpl( root, configFileName, verbose );
                            renderer.loadTemplates();
                            
                            renderer.renderPosts();
                            renderer.renderHistory();
                            renderer.renderTags();
                            renderer.renderIndex();
                            renderer.renderRss();
                            renderer.renderChecksums();
                            logger.info( "Full render complete" );
                        }
                    }
                    
                    if( running ){
                        sleep( sleep );
                    }
                }
            }
            catch(Throwable t ){
                logger.error( "Error in full renderer thread", t );
            }
        }
    }
}
