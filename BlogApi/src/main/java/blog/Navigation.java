package blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    
    // <editor-fold  defaultstate="collapsed"  desc="generated code">

    public Navigation() {
    }

    public Navigation( String first, String last, String previous, String next, String edit, String unpaginated, String permalink, int currentPage ) {
        this.first = first;
        this.last = last;
        this.previous = previous;
        this.next = next;
        this.edit = edit;
        this.unpaginated = unpaginated;
        this.permalink = permalink;
        this.currentPage = currentPage;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst( String first ) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast( String last ) {
        this.last = last;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious( String previous ) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext( String next ) {
        this.next = next;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit( String edit ) {
        this.edit = edit;
    }

    public String getUnpaginated() {
        return unpaginated;
    }

    public void setUnpaginated( String unpaginated ) {
        this.unpaginated = unpaginated;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink( String permalink ) {
        this.permalink = permalink;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage( int currentPage ) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount( int pageCount ) {
        this.pageCount = pageCount;
    }

    public List<String> getAllLinks() {
        return allLinks;
    }

    public void setAllLinks( List<String> allLinks ) {
        this.allLinks = allLinks;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode( this.first );
        hash = 59 * hash + Objects.hashCode( this.last );
        hash = 59 * hash + Objects.hashCode( this.previous );
        hash = 59 * hash + Objects.hashCode( this.next );
        hash = 59 * hash + Objects.hashCode( this.edit );
        hash = 59 * hash + Objects.hashCode( this.unpaginated );
        hash = 59 * hash + Objects.hashCode( this.permalink );
        hash = 59 * hash + this.currentPage;
        hash = 59 * hash + this.pageCount;
        hash = 59 * hash + Objects.hashCode( this.allLinks );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final Navigation other = (Navigation)obj;
        if( this.currentPage != other.currentPage ) {
            return false;
        }
        if( this.pageCount != other.pageCount ) {
            return false;
        }
        if( !Objects.equals( this.first, other.first ) ) {
            return false;
        }
        if( !Objects.equals( this.last, other.last ) ) {
            return false;
        }
        if( !Objects.equals( this.previous, other.previous ) ) {
            return false;
        }
        if( !Objects.equals( this.next, other.next ) ) {
            return false;
        }
        if( !Objects.equals( this.edit, other.edit ) ) {
            return false;
        }
        if( !Objects.equals( this.unpaginated, other.unpaginated ) ) {
            return false;
        }
        if( !Objects.equals( this.permalink, other.permalink ) ) {
            return false;
        }
        if( !Objects.equals( this.allLinks, other.allLinks ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Navigation{" + "first=" + first + ", last=" + last + ", previous=" + previous + ", next=" + next + ", edit=" + edit + ", unpaginated=" + unpaginated + ", permalink=" + permalink + ", currentPage=" + currentPage + ", pageCount=" + pageCount + ", allLinks=" + allLinks + '}';
    }
    
    // </editor-fold>
}
