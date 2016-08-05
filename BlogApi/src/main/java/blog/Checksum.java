package blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Checksum
{
    private String name;
    private long checksum;
    private List<Checksum> children = new ArrayList();
    
    // <editor-fold  defaultstate="collapsed"  desc="generated code">
    
    public Checksum(){}

    public Checksum( String name, long checksum ) {
        this.name = name;
        this.checksum = checksum;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum( long checksum ) {
        this.checksum = checksum;
    }

    public List<Checksum> getChildren() {
        return children;
    }

    public void setChildren( List<Checksum> children ) {
        this.children = children;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode( this.name );
        hash = 83 * hash + (int)( this.checksum ^ ( this.checksum >>> 32 ) );
        hash = 83 * hash + Objects.hashCode( this.children );
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
        final Checksum other = (Checksum)obj;
        if( this.checksum != other.checksum ) {
            return false;
        }
        if( !Objects.equals( this.name, other.name ) ) {
            return false;
        }
        if( !Objects.equals( this.children, other.children ) ) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return name + ": " + checksum;
    }
    
    // </editor-fold>
}
