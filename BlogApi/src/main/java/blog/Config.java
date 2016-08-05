package blog;

import java.util.Objects;

public class Config
{
    private String published = "src/posts/published";
    private String drafts = "src/posts/drafts";
    private String privatePosts = "src/posts/private";
    private String layouts = "src/layouts";
    private String includes = "src/includes";
    private String data = "src/data";
    private String templates = "src/templates";
    private String site = "site";
    
    private String siteMetadataFile;
    private String pandoc = "/usr/local/bin/pandoc";
    
    private String[] staticContent = new String[]{ "src/static/chrome", "src/static/uploads" };
    
    private boolean renderDrafts = false;
    private boolean renderPrivate = false;
    private boolean renderRss = true;
    private int rssFeedPostCount = 50;
    
    private String ftpHost;
    private int ftpPort = -1;
    private String ftpUser;
    private String ftpPassword;
    private String ftpDirectory;
    
    private String ftpProxyHost;
    private int ftpProxyPort;
    private String ftpProxyUser;
    private String ftpProxyPassword;
    
    public boolean renderDrafts(){
        return renderDrafts;
    }
    
    public boolean renderPrivate(){
        return renderPrivate;
    }
    
    public boolean renderRss(){
        return renderRss;
    }
    
    // <editor-fold  defaultstate="collapsed"  desc="generated code">

    public Config() {
    }

    public Config( String siteMetadataFile, String ftpHost, String ftpUser, String ftpPassword, String ftpDirectory, String ftpProxyHost, int ftpProxyPort, String ftpProxyUser, String ftpProxyPassword ) {
        this.siteMetadataFile = siteMetadataFile;
        this.ftpHost = ftpHost;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
        this.ftpDirectory = ftpDirectory;
        this.ftpProxyHost = ftpProxyHost;
        this.ftpProxyPort = ftpProxyPort;
        this.ftpProxyUser = ftpProxyUser;
        this.ftpProxyPassword = ftpProxyPassword;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished( String published ) {
        this.published = published;
    }

    public String getDrafts() {
        return drafts;
    }

    public void setDrafts( String drafts ) {
        this.drafts = drafts;
    }

    public String getPrivatePosts() {
        return privatePosts;
    }

    public void setPrivatePosts( String privatePosts ) {
        this.privatePosts = privatePosts;
    }

    public String getLayouts() {
        return layouts;
    }

    public void setLayouts( String layouts ) {
        this.layouts = layouts;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes( String includes ) {
        this.includes = includes;
    }

    public String getData() {
        return data;
    }

    public void setData( String data ) {
        this.data = data;
    }

    public String[] getStaticContent() {
        return staticContent;
    }

    public void setStaticContent( String[] staticContent ) {
        this.staticContent = staticContent;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates( String templates ) {
        this.templates = templates;
    }

    public String getSite() {
        return site;
    }

    public void setSite( String site ) {
        this.site = site;
    }

    public String getSiteMetadataFile() {
        return siteMetadataFile;
    }

    public void setSiteMetadataFile( String siteMetadataFile ) {
        this.siteMetadataFile = siteMetadataFile;
    }

    public String getPandoc() {
        return pandoc;
    }

    public void setPandoc( String pandoc ) {
        this.pandoc = pandoc;
    }

    public boolean isRenderDrafts() {
        return renderDrafts;
    }

    public void setRenderDrafts( boolean renderDrafts ) {
        this.renderDrafts = renderDrafts;
    }

    public boolean isRenderPrivate() {
        return renderPrivate;
    }

    public void setRenderPrivate( boolean renderPrivate ) {
        this.renderPrivate = renderPrivate;
    }

    public boolean isRenderRss() {
        return renderRss;
    }

    public void setRenderRss( boolean renderRss ) {
        this.renderRss = renderRss;
    }

    public int getRssFeedPostCount() {
        return rssFeedPostCount;
    }

    public void setRssFeedPostCount( int rssFeedPostCount ) {
        this.rssFeedPostCount = rssFeedPostCount;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost( String ftpHost ) {
        this.ftpHost = ftpHost;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort( int ftpPort ) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser( String ftpUser ) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword( String ftpPassword ) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpDirectory() {
        return ftpDirectory;
    }

    public void setFtpDirectory( String ftpDirectory ) {
        this.ftpDirectory = ftpDirectory;
    }

    public String getFtpProxyHost() {
        return ftpProxyHost;
    }

    public void setFtpProxyHost( String ftpProxyHost ) {
        this.ftpProxyHost = ftpProxyHost;
    }

    public int getFtpProxyPort() {
        return ftpProxyPort;
    }

    public void setFtpProxyPort( int ftpProxyPort ) {
        this.ftpProxyPort = ftpProxyPort;
    }

    public String getFtpProxyUser() {
        return ftpProxyUser;
    }

    public void setFtpProxyUser( String ftpProxyUser ) {
        this.ftpProxyUser = ftpProxyUser;
    }

    public String getFtpProxyPassword() {
        return ftpProxyPassword;
    }

    public void setFtpProxyPassword( String ftpProxyPassword ) {
        this.ftpProxyPassword = ftpProxyPassword;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode( this.published );
        hash = 23 * hash + Objects.hashCode( this.drafts );
        hash = 23 * hash + Objects.hashCode( this.privatePosts );
        hash = 23 * hash + Objects.hashCode( this.layouts );
        hash = 23 * hash + Objects.hashCode( this.includes );
        hash = 23 * hash + Objects.hashCode( this.data );
        hash = 23 * hash + Objects.hashCode( this.staticContent );
        hash = 23 * hash + Objects.hashCode( this.templates );
        hash = 23 * hash + Objects.hashCode( this.site );
        hash = 23 * hash + Objects.hashCode( this.siteMetadataFile );
        hash = 23 * hash + Objects.hashCode( this.pandoc );
        hash = 23 * hash + ( this.renderDrafts ? 1 : 0 );
        hash = 23 * hash + ( this.renderPrivate ? 1 : 0 );
        hash = 23 * hash + ( this.renderRss ? 1 : 0 );
        hash = 23 * hash + this.rssFeedPostCount;
        hash = 23 * hash + Objects.hashCode( this.ftpHost );
        hash = 23 * hash + this.ftpPort;
        hash = 23 * hash + Objects.hashCode( this.ftpUser );
        hash = 23 * hash + Objects.hashCode( this.ftpPassword );
        hash = 23 * hash + Objects.hashCode( this.ftpDirectory );
        hash = 23 * hash + Objects.hashCode( this.ftpProxyHost );
        hash = 23 * hash + this.ftpProxyPort;
        hash = 23 * hash + Objects.hashCode( this.ftpProxyUser );
        hash = 23 * hash + Objects.hashCode( this.ftpProxyPassword );
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
        final Config other = (Config)obj;
        if( this.renderDrafts != other.renderDrafts ) {
            return false;
        }
        if( this.renderPrivate != other.renderPrivate ) {
            return false;
        }
        if( this.renderRss != other.renderRss ) {
            return false;
        }
        if( this.rssFeedPostCount != other.rssFeedPostCount ) {
            return false;
        }
        if( this.ftpPort != other.ftpPort ) {
            return false;
        }
        if( this.ftpProxyPort != other.ftpProxyPort ) {
            return false;
        }
        if( !Objects.equals( this.published, other.published ) ) {
            return false;
        }
        if( !Objects.equals( this.drafts, other.drafts ) ) {
            return false;
        }
        if( !Objects.equals( this.privatePosts, other.privatePosts ) ) {
            return false;
        }
        if( !Objects.equals( this.layouts, other.layouts ) ) {
            return false;
        }
        if( !Objects.equals( this.includes, other.includes ) ) {
            return false;
        }
        if( !Objects.equals( this.data, other.data ) ) {
            return false;
        }
        if( !Objects.equals( this.staticContent, other.staticContent ) ) {
            return false;
        }
        if( !Objects.equals( this.templates, other.templates ) ) {
            return false;
        }
        if( !Objects.equals( this.site, other.site ) ) {
            return false;
        }
        if( !Objects.equals( this.siteMetadataFile, other.siteMetadataFile ) ) {
            return false;
        }
        if( !Objects.equals( this.pandoc, other.pandoc ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpHost, other.ftpHost ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpUser, other.ftpUser ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpPassword, other.ftpPassword ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpDirectory, other.ftpDirectory ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpProxyHost, other.ftpProxyHost ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpProxyUser, other.ftpProxyUser ) ) {
            return false;
        }
        if( !Objects.equals( this.ftpProxyPassword, other.ftpProxyPassword ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Config{" + "published=" + published + ", drafts=" + drafts + ", privatePosts=" + privatePosts + ", layouts=" + layouts + ", includes=" + includes + ", data=" + data + ", staticContent=" + staticContent + ", templates=" + templates + ", site=" + site + ", siteMetadataFile=" + siteMetadataFile + ", pandoc=" + pandoc + ", renderDrafts=" + renderDrafts + ", renderPrivate=" + renderPrivate + ", renderRss=" + renderRss + ", rssFeedPostCount=" + rssFeedPostCount + ", ftpHost=" + ftpHost + ", ftpPort=" + ftpPort + ", ftpUser=" + ftpUser + ", ftpPassword=" + ftpPassword + ", ftpDirectory=" + ftpDirectory + ", ftpProxyHost=" + ftpProxyHost + ", ftpProxyPort=" + ftpProxyPort + ", ftpProxyUser=" + ftpProxyUser + ", ftpProxyPassword=" + ftpProxyPassword + '}';
    }
    
    // </editor-fold>
}
