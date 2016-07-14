package blog;

import lombok.Data;

@Data
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
}
