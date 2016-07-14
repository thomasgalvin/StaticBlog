package blog.sort;

import blog.Post;
import java.util.Comparator;
import java.util.Date;

public class PublicationDateComparator implements Comparator
{
    @Override
    public int compare( Object o1, Object o2 ) {
        Post post1 = (Post)o1;
        Post post2 = (Post)o2;
        
        Date one = post1.getDate();
        Date two = post2.getDate();
        
        if( one == null && two != null ){
            return -1;
        }
        else if( one != null && two == null ){
            return 1;
        }
        else if( one == null && two == null ){
            return 0;
        }
        
        return one.compareTo( two );
    }
}
