package blog.sort;

import java.util.Comparator;

public class ReversePublicationDateComparator implements Comparator
{
    private PublicationDateComparator comparator = new PublicationDateComparator();
    
    @Override
    public int compare( Object o1, Object o2 ) {
        return comparator.compare( o1, o2 ) * -1;
    }
}