package blog.sort;

import blog.Post;
import java.util.ArrayList;
import java.util.List;

public class PagedPosts
{
    private static final int DEFAULT_POSTS_PER_PAGE = 10;
    private int postsPerPage;
    private List<Page> pages = new ArrayList();
    
    public PagedPosts( List<Post> posts ) {
        this( posts, DEFAULT_POSTS_PER_PAGE );
    }
    
    public PagedPosts( List<Post> posts, int postsPerPage ) {
        this.postsPerPage = postsPerPage;
        
        Page page = null;
        for( Post post : posts ){
            if( page == null || page.size() >= postsPerPage ){
                page = new Page();
                pages.add( page );
            }
            
            page.add( post );
        }
    }
    
    public boolean hasNext() {
        return !pages.isEmpty();
    }
    
    public List<Post> next() {
        if( hasNext() ){
            Page page = pages.get( 0 );
            pages.remove( 0 );
            return page;
        }
        throw new IllegalStateException( "No more pages" );
    }
    
    public int getPageCount(){
        return pages.size();
    }
    
    private class Page extends ArrayList<Post>{}
}
