package blog;

import java.util.Objects;

public class Author
{
    private String id;
    private String displayName;
    private String fullName;
    private String sortByName;
    private String bio;
    private String headshot;
    private String thumbnail;
    private String twitterHandle; //eg @my_handle

    // <editor-fold  defaultstate="collapsed"  desc="generated code">
    
    public Author() {
    }

    public Author( String id, String displayName, String fullName, String sortByName, String bio, String headshot, String thumbnail, String twitterHandle ) {
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;
        this.sortByName = sortByName;
        this.bio = bio;
        this.headshot = headshot;
        this.thumbnail = thumbnail;
        this.twitterHandle = twitterHandle;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName( String sortByName ) {
        this.sortByName = sortByName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio( String bio ) {
        this.bio = bio;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot( String headshot ) {
        this.headshot = headshot;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail( String thumbnail ) {
        this.thumbnail = thumbnail;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle( String twitterHandle ) {
        this.twitterHandle = twitterHandle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode( this.id );
        hash = 29 * hash + Objects.hashCode( this.displayName );
        hash = 29 * hash + Objects.hashCode( this.fullName );
        hash = 29 * hash + Objects.hashCode( this.sortByName );
        hash = 29 * hash + Objects.hashCode( this.bio );
        hash = 29 * hash + Objects.hashCode( this.headshot );
        hash = 29 * hash + Objects.hashCode( this.thumbnail );
        hash = 29 * hash + Objects.hashCode( this.twitterHandle );
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
        final Author other = (Author)obj;
        if( !Objects.equals( this.id, other.id ) ) {
            return false;
        }
        if( !Objects.equals( this.displayName, other.displayName ) ) {
            return false;
        }
        if( !Objects.equals( this.fullName, other.fullName ) ) {
            return false;
        }
        if( !Objects.equals( this.sortByName, other.sortByName ) ) {
            return false;
        }
        if( !Objects.equals( this.bio, other.bio ) ) {
            return false;
        }
        if( !Objects.equals( this.headshot, other.headshot ) ) {
            return false;
        }
        if( !Objects.equals( this.thumbnail, other.thumbnail ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterHandle, other.twitterHandle ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", displayName=" + displayName + ", fullName=" + fullName + ", sortByName=" + sortByName + ", bio=" + bio + ", headshot=" + headshot + ", thumbnail=" + thumbnail + ", twitterHandle=" + twitterHandle + '}';
    }
    
    // </editor-fold>
}
