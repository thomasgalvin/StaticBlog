package blog;

import java.io.File;
import java.util.Date;
import lombok.Data;

@Data
public class Post {
    private String title;
    private String subtitle;
    private String author;
    
    private String link; //e.g. hello-world; this must be unique
    private String[] symlinks;
    
    private Date date;
    private String layout = "default";
    
    private boolean includeInHistory = true;
    private boolean draftPost = false;
    private boolean privatePost = false;
    
    private String source;
    private String html;
    private String[] tags = new String[]{}; //TODO: render these in the default template
    
    private String pullQuote;
    private boolean paginated;
    
    private String bannerImage;
    private String thumbnailImage;
    private String pullQuoteImage;
    
    private File sourceFile;
    private SourceFormat sourceFormat = SourceFormat.Html;
    
    // Facebook Open Graph metadata
    
    private String ogUrl; //The canonical URL for your page. This should be the undecorated URL, without session variables, user identifying parameters, or counters.
    private String ogTitle; //The title of your article without any branding such as your site name.
    private String ogDescription; //A brief description of the content, usually between 2 and 4 sentences. This will displayed below the title of the post on Facebook.
    private String ogImage; //The URL of the image that appears when someone shares the content to Facebook.
    private String ogSiteName; //The name of your website
    
    private String ogAudio; //Attach audio to the article
    private String ogVideo; //Attach videoto the article
    
    private String ogType = OGType.article;
    private String ogLocale = "en_US";
    
    private String fbAppId; //The Facebook Application ID
    private String fbProfileId; //The Facebook User Profile ID
    
    // Twitter Cards metadata; by default, we'll copy the values from
    // the Facebook equivalents if these are not set
    private String twitterUrl; 
    private String twitterTitle; 
    private String twitterDescription; //limited to 200 chars
    private String twitterImage;

    // these fields doesn't overlap with og:type's values, so you 
    // should set them independently
    private String twitterCard = TwitterSummary.summary;
    private String twitterSite; // publisher's @tag
    private String twitterCreator; // author's @tag
    
    // Google+ metadata; by default, we'll copy the values from
    // the Facebook equivalents if these are not set
    private String googleName;
    private String googleDescription;
    private String googleImage;
    
    public Post clone(){
        Post result = new Post();
        result.title = title;
        result.subtitle = subtitle;
        result.author = author;
        result.link = link;
        result.date = date;
        result.layout = layout;
        result.source = source;
        result.html = html;
        result.tags = new String[ tags.length ];
        
        result.pullQuote = pullQuote;
        result.paginated = paginated;
        result.bannerImage = bannerImage;
        result.thumbnailImage = thumbnailImage;
        result.pullQuoteImage = pullQuoteImage;
        result.sourceFile = sourceFile;
        
        result.includeInHistory = includeInHistory;
        result.draftPost = draftPost;
        result.privatePost = privatePost;
        result.sourceFormat = sourceFormat;
        
        result.ogUrl = ogUrl;
        result.ogTitle = ogTitle;
        result.ogDescription = ogDescription;
        result.ogImage = ogImage;
        result.ogSiteName = ogSiteName;
        
        result.ogAudio = ogAudio;
        result.ogVideo = ogVideo;
        
        result.ogType = ogType;
        result.ogLocale = ogLocale;
        
        result.fbAppId = fbAppId;
        result.fbProfileId = fbProfileId;
        
        result.twitterUrl = twitterUrl;
        result.twitterTitle = twitterTitle;
        result.twitterDescription = twitterDescription;
        result.twitterImage = twitterImage;
        
        result.twitterCard = twitterCard;
        result.twitterSite = twitterSite;
        result.twitterCreator = twitterCreator;
        
        result.googleName = googleName;
        result.googleDescription = googleDescription;
        result.googleImage = googleImage;
        
        System.arraycopy( tags, 0, result.tags, 0, tags.length );
        
        return result;
    }
    
    public boolean getIncludeInHistory(){
        return includeInHistory;
    }
    
    public void setIncludeInHistory( boolean includeInHistory ){
        this.includeInHistory = includeInHistory;
    }
}
