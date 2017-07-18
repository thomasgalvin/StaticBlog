package blog;

import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Help {
    public static void master(){
        printHelpFile( "help_master.txt" );
    }
    
    public static void newsite(){
        printHelpFile( "help_skeleton.txt" );
    }
    
    public static void layout(){
        printHelpFile( "help_layout.txt" );
    }
    
    public static void config(){
        printHelpFile( "help_config.txt" );
    }
    
    public static void metadata(){
        printHelpFile( "help_site.txt" );
    }
    
    public static void authors(){
        printHelpFile( "help_authors.txt" );
    }
    
    public static void themes(){
        printHelpFile( "help_layouts.txt" );
    }
    
    public static void posts(){
        printHelpFile( "help_posts.txt" );
    }
    
    public static void newpost(){
        printHelpFile( "help_new_post.txt" );
    }
    
    public static void render(){
        printHelpFile( "help_render.txt" );
    }
    
    public static void server(){
        printHelpFile( "help_server.txt" );
    }
    
    public static void publish(){
        printHelpFile( "help_publish.txt" );
    }
    
    public static void all(){
        master();
        newsite();
        layout();
        config();
        metadata();
        authors();
        themes();
        posts();
        newpost();
        render();
        server();
        publish();
    }
    
    private static void printHelpFile( String name ){
        try {
            URL url = Help.class.getClassLoader().getResource( "blog/help/" + name );
            if( url != null ){
                String contents = IOUtils.toString( url, Charset.defaultCharset() );
                if( !isBlank( contents ) ){
                    System.out.println(contents);
                    System.out.println("\n\n");
                }
            }
        }
        catch(Throwable t ){
            System.out.println("Error reading help file");
        }
    }
}
