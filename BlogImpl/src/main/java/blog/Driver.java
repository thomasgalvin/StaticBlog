package blog;

import blog.publish.FtpPublisher;
import blog.watch.Watcher;
import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
    private static final Logger logger = LoggerFactory.getLogger( Driver.class );

    public static void main( String[] args ) {
        try {
            Options options = getOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse( options, args );
            
            if( cmd.hasOption( 'h' ) ){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "blog", options );
            }
            
            boolean verbose = cmd.hasOption( "v" );
            
            File root = new File( System.getProperty( "user.dir" ) );
            if( cmd.hasOption( 'd' ) ){
                String dirOpt = cmd.getOptionValue( 'd' );
                if( !StringUtils.isBlank( dirOpt ) )
                {
                    root = new File( dirOpt );
                }
            }
            
            String configFile = null;
            if( cmd.hasOption( 'c' ) ){
                configFile = cmd.getOptionValue( 'c' );
            }
            else {
                configFile = new File( root, "/src/" + RendererImpl.CONFIG_YML ).getAbsolutePath();
            }
            Config config = RendererImpl.readConfig( new File( configFile ) );
            
            if( cmd.hasOption( "d" ) ){
                config.setRenderDrafts( true );
            }
            
            if( cmd.hasOption( "p" ) ){
                config.setRenderPrivate( true );
            }
            
            if( cmd.hasOption( 'i' ) ){
                boolean proceed = true;
                if( root.exists() ){
                    File[] files = root.listFiles();
                    if( files != null && files.length > 0 ){
                        proceed = cmd.hasOption( 'f' );
                    }
                }
                
                if( proceed ){
                    InitImpl init = new InitImpl();
                    init.init( root );
                }
                else {
                    System.out.println( "Skeleton directory already exists: " + root.getAbsolutePath() );
                    System.out.println( "Use --force to overwrite" );
                }
            }
            
            if( cmd.hasOption( "clean" ) ){
                if( root.exists() ){
                    Cleaner.clean( root, config );
                }
            }
            
            RendererImpl renderer = null;
            Watcher watcher = null;
            if( cmd.hasOption( 'g' ) || cmd.hasOption( "publish" ) ){
                renderer = new RendererImpl( root, config, verbose );
            }
            
            if( cmd.hasOption( "n" ) ){
                boolean empty = cmd.hasOption( "empty" );
                boolean simple = cmd.hasOption( "simple" );
                
                if( empty ){
                    new PostGenerator().runEmpty( config );
                }
                else {
                    String templateFile = null;
                    if( cmd.hasOption( "template" ) ){
                        templateFile = cmd.getOptionValue( "template" );
                    }

                    boolean asis = cmd.hasOption( "asis" );
                    new PostGenerator().run( config, templateFile, asis, simple );
                }
            }
            
            if( cmd.hasOption( 'g' ) ){
                renderer.render();
            }
            
            if( cmd.hasOption( "publish" ) ){
                if( cmd.hasOption( "ftpHost" ) ){
                    config.setFtpHost( cmd.getOptionValue( "ftpHost" ) );
                    logger.info( "Set ftp host: " + config.getFtpHost() );
                }

                if( cmd.hasOption( "ftpPort" ) ){
                    String portString = cmd.getOptionValue( "ftpPort" );
                    try{
                        int port = Integer.parseInt( portString );
                        config.setFtpPort( port );
                        logger.info( "Set ftp port: " + config.getFtpPort() );
                    }
                    catch( Throwable t ){
                        logger.error( "Bad port value: " + portString, t );
                    }
                }

                if( cmd.hasOption( "ftpUser" ) ){
                    config.setFtpUser( cmd.getOptionValue( "ftpUser" ) );
                    logger.info( "Set ftp user: " + config.getFtpUser() );
                }

                if( cmd.hasOption( "ftpPass" ) ){
                    config.setFtpPassword( cmd.getOptionValue( "ftpPass" ) );
                    logger.info( "Set ftp pass: " + config.getFtpPassword() );
                }
                
                logger.info( "Publishing..." );
                
                File site = new File( root, config.getSite() );
                FtpPublisher publisher = new FtpPublisher( site, config, renderer.getSite() );
                publisher.setVerbose( verbose );
                publisher.publish();
            }
            
            if( cmd.hasOption( 'w' ) ){
                watcher = new Watcher( root, configFile, verbose );
                watcher.start();
            }
            
            if( cmd.hasOption( 's' ) ){
                int port = StaticServer.DEFAULT_PORT;
                if( cmd.hasOption( "port" ) ){
                    String portOpt = cmd.getOptionValue( "port" );
                    port = Integer.parseInt( portOpt );
                }
                
                
                File site = new File( root, config.getSite() );
                StaticServer server = new StaticServer( site, port );
                server.startThread();
            }
        }
        catch( Throwable t ) {
            logger.info( "Error in main", t );
        }
    }

    private static Options getOptions() {
        Option optGenerate = Option.builder( "g" )
            .longOpt( "generate" )
            .desc( "Generate the final static web site" )
            .required( false )
            .build();
        
        Option optClean = Option.builder( "clean" )
            .desc( "Deletes the contents of site" )
            .required( false )
            .build();

        Option optDir = Option.builder( "dir" )
            .longOpt( "directory" )
            .hasArg()
            .argName( "dir" )
            .desc( "Look for source files in <dir> rather than in the user's current working directory" )
            .required( false )
            .build();

        Option optConfig = Option.builder( "c" )
            .longOpt( "config" )
            .hasArg()
            .argName( "config.yml" )
            .desc( "Use the specified config file" )
            .required( false )
            .build();

        Option optServer = Option.builder( "s" )
            .longOpt( "server" )
            .desc( "Start a web server and host the blog" )
            .required( false )
            .build();
        
        Option optPort = Option.builder( "port" )
            .hasArg()
            .desc( "Specifies the port for the web server" )
            .required( false )
            .build();

        Option optWatch = Option.builder( "w" )
            .longOpt( "watch" )
            .desc( "Watch the source dirs for changes and automatically rebuild" )
            .required( false )
            .build();
        
        Option optVerbose = Option.builder( "v" )
            .longOpt( "verbose" )
            .desc( "Verbose output" )
            .required( false )
            .build();

        Option optInit = Option.builder( "i" )
            .longOpt( "init" )
            .hasArg( false )
            .desc( "Create a sekeleton blog project" )
            .required( false )
            .build();
        
        Option optForce = Option.builder( "f" )
            .longOpt( "force" )
            .hasArg( false )
            .desc( "Force the creation of a sekeleton blog project, overwriting any existing files" )
            .required( false )
            .build();
        
        Option optHelp = Option.builder( "h" )
            .longOpt( "help" )
            .hasArg( false )
            .desc( "Print this help message" )
            .required( false )
            .build();
        
        Option optDrafts = Option.builder( "d" )
            .longOpt( "drafts" )
            .desc( "Include draft posts when rendering" )
            .required( false )
            .build();
        
        Option optPrivate = Option.builder( "p" )
            .longOpt( "private" )
            .desc( "Include private posts when rendering" )
            .required( false )
            .build();
        
        Option optPublish = Option.builder( "publish" )
            .desc( "Published the web site via FTP" )
            .required( false )
            .build();
        
        Option optPublishHost = Option.builder( "ftpHost" )
            .hasArg()
            .desc( "Specifies the host for FTP publishing" )
            .required( false )
            .build();
        
        Option optPublishPort = Option.builder( "ftpPort" )
            .hasArg()
            .desc( "Specifies the port for FTP publishing" )
            .required( false )
            .build();
        
        Option optPublishUser = Option.builder( "ftpUser" )
            .hasArg()
            .desc( "Specifies the user for FTP publishing" )
            .required( false )
            .build();
        
        Option optPublishPassword = Option.builder( "ftpPass" )
            .hasArg()
            .desc( "Specifies the password for FTP publishing" )
            .required( false )
            .build();
        
        Option optNewPost = Option.builder( "n" )
            .longOpt( "new" )
            .desc( "Create a new post" )
            .required( false )
            .build();
        
        Option optNewPostTemplate = Option.builder( "template" )
            .desc( "Load template file for new post" )
            .hasArg( true )
            .required( false )
            .build();
        
        Option optNewPostAsIs = Option.builder( "asis" )
            .desc( "Create post as-is; do not prompt for metadata when creating a new post" )
            .required( false )
            .build();
        
        Option optNewPostEmpty = Option.builder( "empty" )
            .desc( "Create a new, empty post" )
            .required( false )
            .build();
        
        Option optNewPostSimple = Option.builder( "simple" )
            .desc( "Only prompt for the most common post metadata" )
            .required( false )
            .build();
        
        Options options = new Options();
        options.addOption( optHelp );
        
        options.addOption( optDir );
        options.addOption( optConfig );
        
        options.addOption( optInit );
        options.addOption( optForce );
        
        options.addOption( optGenerate );
        options.addOption( optClean );
        options.addOption( optWatch );
        options.addOption( optVerbose );
        
        
        options.addOption( optServer );
        options.addOption( optPort );
        
        options.addOption( optDrafts );
        options.addOption( optPrivate );
        
        options.addOption( optPublish );
        options.addOption( optPublishHost );
        options.addOption( optPublishPort );
        options.addOption( optPublishUser );
        options.addOption( optPublishPassword );
        
        options.addOption( optNewPost );
        options.addOption( optNewPostTemplate );
        options.addOption( optNewPostAsIs );
        options.addOption( optNewPostEmpty );
        options.addOption( optNewPostSimple );
        
        return options;
    }
}
