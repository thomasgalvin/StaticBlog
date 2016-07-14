package blog;

import lombok.Data;

@Data
public class Config
{
    private String published = "src/posts/published";
    private String drafts = "src/posts/drafts";
    private String privatePosts = "src/posts/private";
    private String layouts = "src/layouts";
    private String includes = "src/includes";
    private String data = "src/data";
    private String staticDir = "src/static";
    private String uploads = "src/uploads";
    private String templates = "src/templates";
    private String site = "site";
    
    private String siteMetadataFile;
    private String pandoc = "/usr/local/bin/pandoc";
    
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
}
