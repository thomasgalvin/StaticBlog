package blog;

import com.esotericsoftware.yamlbeans.YamlReader;
import blog.sort.BlogPostFilenameFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostLoader
{
    public static final String METADATA_DELIMITER = "---";
    private static final Logger logger = LoggerFactory.getLogger( PostLoader.class );
    private static BlogPostFilenameFilter filter = new BlogPostFilenameFilter();
    private LinkGenerator links = new LinkGenerator();
    private List<Post> posts = new ArrayList();
    private List<Post> drafts = new ArrayList();
    private List<Post> privatePosts = new ArrayList();
    private boolean verbose;
    
    public PostLoader( File postsDir, File draftsDir, File privateDir, Config config, boolean verbose ) {
        this.verbose = verbose;
        readPosts( postsDir, draftsDir, privateDir, config );
    }
    
    
    private void readPosts( File postsDir, File draftsDir, File privateDir, Config config ) {
        links.clear();
        logger.info( "Loading posts" );
        readPosts( postsDir, posts, false, false );
        
        boolean loadDrafts = false;
        boolean loadPrivate = false;
        
        if( config != null ){
            loadDrafts = config.isRenderDrafts();
            loadPrivate = config.isRenderPrivate();
        }
        
        if( loadDrafts ){
            logger.info( "Loading draft posts" );
            readPosts( draftsDir, drafts, true, false );
        }
        
        if( loadPrivate ){
            logger.info( "Loading private posts" );
            readPosts( privateDir, privatePosts, false, true );
        }
    }
    
    private void readPosts( File dir, List<Post> posts, boolean draftPost, boolean privatePost ) {
        File[] files = dir.listFiles();
        if( files == null ){
            return;
        }
        
        for( File file : files ){
            if( isPostFile( file ) ){
                if( isVerbose() ){
                    logger.info( "Adding post: " + file.getAbsolutePath() );
                }
                
                try {
                    Post post = readPost( file );
                    if( post != null ){
                        links.ensureLink( file, post );
                        String link = post.getLink();
                        
                        if( StringUtils.isEmpty( link ) ){
                            logger.info( "Could not generate link for file " + file.getAbsolutePath() );
                        }
                        else {
                            post.setDraftPost( draftPost );
                            post.setPrivatePost( privatePost );
                            posts.add( post );
                        }
                    }
                }
                catch( IOException ioe ){
                    logger.error( "Error reading post: " + file.getAbsolutePath(), ioe );
                }
            }
            else if( file.isDirectory() ){
                readPosts( file, posts, draftPost, privatePost );
            }
        }
    }
    
    public static boolean isPostFile( File file ){
        return filter.accept( file.getParentFile(), file.getName() );
    }
    
    public static Post readPost( File file ) throws IOException {
        Post result = null;
        
        String data = FileUtils.readFileToString( file );
        if( StringUtils.isBlank( data ) ){
            logger.info( "File " + file.getAbsolutePath() + " contained no data" );
            return null;
        }
        
        data = data.trim();
        
        if( data.startsWith( METADATA_DELIMITER ) ){
            int endIndex = data.indexOf( "\n", 0 );
            endIndex = data.indexOf( METADATA_DELIMITER, endIndex );
            
            String yaml = data.substring( METADATA_DELIMITER.length(), endIndex );
            String body = data.substring( endIndex + METADATA_DELIMITER.length() );
            
            yaml = yaml.trim();
            body = body.trim();
            
            if( StringUtils.isBlank( yaml ) ){
                result = new Post();
            }
            else{
                YamlReader reader = new YamlReader( yaml );
                result = reader.read(Post.class);
            }
            result.setSource( body );
            //String fileName = file.getName();
        }
        else{
            result = new Post();
            result.setSource( data );
        }
        
        result.setSourceFile( file );
        result.setSourceFormat( SourceFormat.get( file ) );
        
        if( result.getDate() == null ){
            long timestamp = file.lastModified();
            Date date = new Date( timestamp );
            result.setDate( date );
        }
        
        return result;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Post> getDrafts() {
        return drafts;
    }
    
    public List<Post> getPrivatePosts() {
        return privatePosts;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose( boolean verbose ) {
        this.verbose = verbose;
    }
}
