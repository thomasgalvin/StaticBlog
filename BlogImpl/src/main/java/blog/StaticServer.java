package blog;

import java.io.File;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticServer
{
    private static final Logger logger = LoggerFactory.getLogger(StaticServer.class );
    public static final int DEFAULT_PORT = 8080;
    private File root;
    private int port;
    
    public StaticServer( File root, int port ){
        this.root = root;
        this.port = port;
    }
    
    public void start(){
        try {
            logger.info( "Running Jetty from " + root.getAbsolutePath() + " on port " + port );

            ResourceHandler handler = new ResourceHandler();
            handler.setDirectoriesListed( true );
            handler.setWelcomeFiles( new String[] { "index.html" } );
            handler.setResourceBase( root.getAbsolutePath() );

            Server server = new Server();
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(port);
            server.addConnector(connector);

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { handler, new DefaultHandler() });
            server.setHandler(handlers);

            server.start();
            server.join();
        }
        catch( Throwable t ){
            logger.error( "Error starting Jetty server", t );
        }
    }
    
    public void startThread(){
        Thread thread = new Thread(){
            public void run(){
                StaticServer.this.start();
            }
        };
        thread.start();
    }
}
