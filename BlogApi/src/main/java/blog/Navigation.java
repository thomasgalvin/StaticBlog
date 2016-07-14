package blog;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Navigation implements Cloneable
{
    private String first;
    private String last;
    private String previous;
    private String next;
    private String edit;
    private String unpaginated;
    private String permalink;
    private int currentPage;
    private int pageCount = 1;
    
    private List<String> allLinks = new ArrayList();
    
    @Override
    public Navigation clone() {
        try {
        Navigation result = (Navigation)super.clone();
            result.setFirst( first );
            result.setPrevious( previous );
            result.setNext( next );
            result.setEdit( edit );
            result.setUnpaginated( unpaginated );
            result.setPermalink( permalink );
            result.setCurrentPage( currentPage );
            result.setPageCount( pageCount );

            result.setAllLinks( new ArrayList() );
            result.getAllLinks().addAll( allLinks );
            return result;
        } catch( Throwable t ) {
            return null;
        }
    }
}
