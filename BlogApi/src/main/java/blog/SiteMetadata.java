package blog;

import java.util.Objects;

public class SiteMetadata implements Cloneable
{
    public static final String DEFAULT_HISTORY = "page";
    public static final String DEFAULT_TAGS = "tags";
    public static final String DEFAULT_AUTHORS = "authors";
    public static final String DEFAULT_FEED = "rss";
    
    private String title;
    private String subtitle;
    private String about;
    private int postsPerPage = 10;
    private String webAddress = "";
    private String blogRoot = "";
    private String landingPage;
    
    private String posts = "";
    
    private String authors = DEFAULT_AUTHORS;
    private String history = DEFAULT_HISTORY;
    private String tags = DEFAULT_TAGS;
    private String feed = DEFAULT_FEED;
    
    private String rssTitle;
    private String rssLink;
    private String rssDescription;
    private String rssCopyright;
    private String rssAuthor;
    
    private String twitterHandle; //e.g. @MyPublisher

    // <editor-fold  defaultstate="collapsed"  desc="generated code">
    
    public SiteMetadata() {
    }

    public SiteMetadata( String title, String subtitle, String about, String landingPage, String rssTitle, String rssLink, String rssDescription, String rssCopyright, String rssAuthor, String twitterHandle ) {
        this.title = title;
        this.subtitle = subtitle;
        this.about = about;
        this.landingPage = landingPage;
        this.rssTitle = rssTitle;
        this.rssLink = rssLink;
        this.rssDescription = rssDescription;
        this.rssCopyright = rssCopyright;
        this.rssAuthor = rssAuthor;
        this.twitterHandle = twitterHandle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle( String subtitle ) {
        this.subtitle = subtitle;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout( String about ) {
        this.about = about;
    }

    public int getPostsPerPage() {
        return postsPerPage;
    }

    public void setPostsPerPage( int postsPerPage ) {
        this.postsPerPage = postsPerPage;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress( String webAddress ) {
        this.webAddress = webAddress;
    }

    public String getBlogRoot() {
        return blogRoot;
    }

    public void setBlogRoot( String blogRoot ) {
        this.blogRoot = blogRoot;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage( String landingPage ) {
        this.landingPage = landingPage;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts( String posts ) {
        this.posts = posts;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors( String authors ) {
        this.authors = authors;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory( String history ) {
        this.history = history;
    }

    public String getTags() {
        return tags;
    }

    public void setTags( String tags ) {
        this.tags = tags;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed( String feed ) {
        this.feed = feed;
    }

    public String getRssTitle() {
        return rssTitle;
    }

    public void setRssTitle( String rssTitle ) {
        this.rssTitle = rssTitle;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink( String rssLink ) {
        this.rssLink = rssLink;
    }

    public String getRssDescription() {
        return rssDescription;
    }

    public void setRssDescription( String rssDescription ) {
        this.rssDescription = rssDescription;
    }

    public String getRssCopyright() {
        return rssCopyright;
    }

    public void setRssCopyright( String rssCopyright ) {
        this.rssCopyright = rssCopyright;
    }

    public String getRssAuthor() {
        return rssAuthor;
    }

    public void setRssAuthor( String rssAuthor ) {
        this.rssAuthor = rssAuthor;
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
        hash = 29 * hash + Objects.hashCode( this.title );
        hash = 29 * hash + Objects.hashCode( this.subtitle );
        hash = 29 * hash + Objects.hashCode( this.about );
        hash = 29 * hash + this.postsPerPage;
        hash = 29 * hash + Objects.hashCode( this.webAddress );
        hash = 29 * hash + Objects.hashCode( this.blogRoot );
        hash = 29 * hash + Objects.hashCode( this.landingPage );
        hash = 29 * hash + Objects.hashCode( this.posts );
        hash = 29 * hash + Objects.hashCode( this.authors );
        hash = 29 * hash + Objects.hashCode( this.history );
        hash = 29 * hash + Objects.hashCode( this.tags );
        hash = 29 * hash + Objects.hashCode( this.feed );
        hash = 29 * hash + Objects.hashCode( this.rssTitle );
        hash = 29 * hash + Objects.hashCode( this.rssLink );
        hash = 29 * hash + Objects.hashCode( this.rssDescription );
        hash = 29 * hash + Objects.hashCode( this.rssCopyright );
        hash = 29 * hash + Objects.hashCode( this.rssAuthor );
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
        final SiteMetadata other = (SiteMetadata)obj;
        if( this.postsPerPage != other.postsPerPage ) {
            return false;
        }
        if( !Objects.equals( this.title, other.title ) ) {
            return false;
        }
        if( !Objects.equals( this.subtitle, other.subtitle ) ) {
            return false;
        }
        if( !Objects.equals( this.about, other.about ) ) {
            return false;
        }
        if( !Objects.equals( this.webAddress, other.webAddress ) ) {
            return false;
        }
        if( !Objects.equals( this.blogRoot, other.blogRoot ) ) {
            return false;
        }
        if( !Objects.equals( this.landingPage, other.landingPage ) ) {
            return false;
        }
        if( !Objects.equals( this.posts, other.posts ) ) {
            return false;
        }
        if( !Objects.equals( this.authors, other.authors ) ) {
            return false;
        }
        if( !Objects.equals( this.history, other.history ) ) {
            return false;
        }
        if( !Objects.equals( this.tags, other.tags ) ) {
            return false;
        }
        if( !Objects.equals( this.feed, other.feed ) ) {
            return false;
        }
        if( !Objects.equals( this.rssTitle, other.rssTitle ) ) {
            return false;
        }
        if( !Objects.equals( this.rssLink, other.rssLink ) ) {
            return false;
        }
        if( !Objects.equals( this.rssDescription, other.rssDescription ) ) {
            return false;
        }
        if( !Objects.equals( this.rssCopyright, other.rssCopyright ) ) {
            return false;
        }
        if( !Objects.equals( this.rssAuthor, other.rssAuthor ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterHandle, other.twitterHandle ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SiteMetadata{" + "title=" + title + ", subtitle=" + subtitle + ", about=" + about + ", postsPerPage=" + postsPerPage + ", webAddress=" + webAddress + ", blogRoot=" + blogRoot + ", landingPage=" + landingPage + ", posts=" + posts + ", authors=" + authors + ", history=" + history + ", tags=" + tags + ", feed=" + feed + ", rssTitle=" + rssTitle + ", rssLink=" + rssLink + ", rssDescription=" + rssDescription + ", rssCopyright=" + rssCopyright + ", rssAuthor=" + rssAuthor + ", twitterHandle=" + twitterHandle + '}';
    }
    
    // </editor-fold>
}
