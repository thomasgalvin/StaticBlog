package blog;

import java.util.Objects;

public class Link
{
    private String name;
    private String url;

    // <editor-fold  defaultstate="collapsed"  desc="generated code">
    
    public Link() {
    }

    public Link( String name, String url ) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode( this.name );
        hash = 73 * hash + Objects.hashCode( this.url );
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
        final Link other = (Link)obj;
        if( !Objects.equals( this.name, other.name ) ) {
            return false;
        }
        if( !Objects.equals( this.url, other.url ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Link{" + "name=" + name + ", url=" + url + '}';
    }
    
    // </editor-fold>
}
