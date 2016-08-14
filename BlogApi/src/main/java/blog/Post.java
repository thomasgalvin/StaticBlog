package blog;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Post implements Cloneable {
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
    
    @Override
    public Post clone(){
        Post result = new Post();
        clone( result );
        return result;
    }
    
    public void clone( Post result ){
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
    }
    
    public boolean getIncludeInHistory(){
        return includeInHistory;
    }
    
    public void setIncludeInHistory( boolean includeInHistory ){
        this.includeInHistory = includeInHistory;
    }

    // <editor-fold  defaultstate="collapsed"  desc="generated code">
    
    public Post() {
    }

    public Post( String title, String subtitle, String author, String link, String[] symlinks, Date date, String source, String html, String pullQuote, boolean paginated, String bannerImage, String thumbnailImage, String pullQuoteImage, File sourceFile, String ogUrl, String ogTitle, String ogDescription, String ogImage, String ogSiteName, String ogAudio, String ogVideo, String fbAppId, String fbProfileId, String twitterUrl, String twitterTitle, String twitterDescription, String twitterImage, String twitterSite, String twitterCreator, String googleName, String googleDescription, String googleImage ) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.link = link;
        this.symlinks = symlinks;
        this.date = date;
        this.source = source;
        this.html = html;
        this.pullQuote = pullQuote;
        this.paginated = paginated;
        this.bannerImage = bannerImage;
        this.thumbnailImage = thumbnailImage;
        this.pullQuoteImage = pullQuoteImage;
        this.sourceFile = sourceFile;
        this.ogUrl = ogUrl;
        this.ogTitle = ogTitle;
        this.ogDescription = ogDescription;
        this.ogImage = ogImage;
        this.ogSiteName = ogSiteName;
        this.ogAudio = ogAudio;
        this.ogVideo = ogVideo;
        this.fbAppId = fbAppId;
        this.fbProfileId = fbProfileId;
        this.twitterUrl = twitterUrl;
        this.twitterTitle = twitterTitle;
        this.twitterDescription = twitterDescription;
        this.twitterImage = twitterImage;
        this.twitterSite = twitterSite;
        this.twitterCreator = twitterCreator;
        this.googleName = googleName;
        this.googleDescription = googleDescription;
        this.googleImage = googleImage;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink( String link ) {
        this.link = link;
    }

    public String[] getSymlinks() {
        return symlinks;
    }

    public void setSymlinks( String[] symlinks ) {
        this.symlinks = symlinks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout( String layout ) {
        this.layout = layout;
    }

    public boolean isDraftPost() {
        return draftPost;
    }

    public void setDraftPost( boolean draftPost ) {
        this.draftPost = draftPost;
    }

    public boolean isPrivatePost() {
        return privatePost;
    }

    public void setPrivatePost( boolean privatePost ) {
        this.privatePost = privatePost;
    }

    public String getSource() {
        return source;
    }

    public void setSource( String source ) {
        this.source = source;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml( String html ) {
        this.html = html;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags( String[] tags ) {
        this.tags = tags;
    }

    public String getPullQuote() {
        return pullQuote;
    }

    public void setPullQuote( String pullQuote ) {
        this.pullQuote = pullQuote;
    }

    public boolean isPaginated() {
        return paginated;
    }

    public void setPaginated( boolean paginated ) {
        this.paginated = paginated;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage( String bannerImage ) {
        this.bannerImage = bannerImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage( String thumbnailImage ) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getPullQuoteImage() {
        return pullQuoteImage;
    }

    public void setPullQuoteImage( String pullQuoteImage ) {
        this.pullQuoteImage = pullQuoteImage;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile( File sourceFile ) {
        this.sourceFile = sourceFile;
    }

    public SourceFormat getSourceFormat() {
        return sourceFormat;
    }

    public void setSourceFormat( SourceFormat sourceFormat ) {
        this.sourceFormat = sourceFormat;
    }

    public String getOgUrl() {
        return ogUrl;
    }

    public void setOgUrl( String ogUrl ) {
        this.ogUrl = ogUrl;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle( String ogTitle ) {
        this.ogTitle = ogTitle;
    }

    public String getOgDescription() {
        return ogDescription;
    }

    public void setOgDescription( String ogDescription ) {
        this.ogDescription = ogDescription;
    }

    public String getOgImage() {
        return ogImage;
    }

    public void setOgImage( String ogImage ) {
        this.ogImage = ogImage;
    }

    public String getOgSiteName() {
        return ogSiteName;
    }

    public void setOgSiteName( String ogSiteName ) {
        this.ogSiteName = ogSiteName;
    }

    public String getOgAudio() {
        return ogAudio;
    }

    public void setOgAudio( String ogAudio ) {
        this.ogAudio = ogAudio;
    }

    public String getOgVideo() {
        return ogVideo;
    }

    public void setOgVideo( String ogVideo ) {
        this.ogVideo = ogVideo;
    }

    public String getOgType() {
        return ogType;
    }

    public void setOgType( String ogType ) {
        this.ogType = ogType;
    }

    public String getOgLocale() {
        return ogLocale;
    }

    public void setOgLocale( String ogLocale ) {
        this.ogLocale = ogLocale;
    }

    public String getFbAppId() {
        return fbAppId;
    }

    public void setFbAppId( String fbAppId ) {
        this.fbAppId = fbAppId;
    }

    public String getFbProfileId() {
        return fbProfileId;
    }

    public void setFbProfileId( String fbProfileId ) {
        this.fbProfileId = fbProfileId;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl( String twitterUrl ) {
        this.twitterUrl = twitterUrl;
    }

    public String getTwitterTitle() {
        return twitterTitle;
    }

    public void setTwitterTitle( String twitterTitle ) {
        this.twitterTitle = twitterTitle;
    }

    public String getTwitterDescription() {
        return twitterDescription;
    }

    public void setTwitterDescription( String twitterDescription ) {
        this.twitterDescription = twitterDescription;
    }

    public String getTwitterImage() {
        return twitterImage;
    }

    public void setTwitterImage( String twitterImage ) {
        this.twitterImage = twitterImage;
    }

    public String getTwitterCard() {
        return twitterCard;
    }

    public void setTwitterCard( String twitterCard ) {
        this.twitterCard = twitterCard;
    }

    public String getTwitterSite() {
        return twitterSite;
    }

    public void setTwitterSite( String twitterSite ) {
        this.twitterSite = twitterSite;
    }

    public String getTwitterCreator() {
        return twitterCreator;
    }

    public void setTwitterCreator( String twitterCreator ) {
        this.twitterCreator = twitterCreator;
    }

    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName( String googleName ) {
        this.googleName = googleName;
    }

    public String getGoogleDescription() {
        return googleDescription;
    }

    public void setGoogleDescription( String googleDescription ) {
        this.googleDescription = googleDescription;
    }

    public String getGoogleImage() {
        return googleImage;
    }

    public void setGoogleImage( String googleImage ) {
        this.googleImage = googleImage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode( this.title );
        hash = 17 * hash + Objects.hashCode( this.subtitle );
        hash = 17 * hash + Objects.hashCode( this.author );
        hash = 17 * hash + Objects.hashCode( this.link );
        hash = 17 * hash + Arrays.deepHashCode( this.symlinks );
        hash = 17 * hash + Objects.hashCode( this.date );
        hash = 17 * hash + Objects.hashCode( this.layout );
        hash = 17 * hash + ( this.includeInHistory ? 1 : 0 );
        hash = 17 * hash + ( this.draftPost ? 1 : 0 );
        hash = 17 * hash + ( this.privatePost ? 1 : 0 );
        hash = 17 * hash + Objects.hashCode( this.source );
        hash = 17 * hash + Objects.hashCode( this.html );
        hash = 17 * hash + Arrays.deepHashCode( this.tags );
        hash = 17 * hash + Objects.hashCode( this.pullQuote );
        hash = 17 * hash + ( this.paginated ? 1 : 0 );
        hash = 17 * hash + Objects.hashCode( this.bannerImage );
        hash = 17 * hash + Objects.hashCode( this.thumbnailImage );
        hash = 17 * hash + Objects.hashCode( this.pullQuoteImage );
        hash = 17 * hash + Objects.hashCode( this.sourceFile );
        hash = 17 * hash + Objects.hashCode( this.sourceFormat );
        hash = 17 * hash + Objects.hashCode( this.ogUrl );
        hash = 17 * hash + Objects.hashCode( this.ogTitle );
        hash = 17 * hash + Objects.hashCode( this.ogDescription );
        hash = 17 * hash + Objects.hashCode( this.ogImage );
        hash = 17 * hash + Objects.hashCode( this.ogSiteName );
        hash = 17 * hash + Objects.hashCode( this.ogAudio );
        hash = 17 * hash + Objects.hashCode( this.ogVideo );
        hash = 17 * hash + Objects.hashCode( this.ogType );
        hash = 17 * hash + Objects.hashCode( this.ogLocale );
        hash = 17 * hash + Objects.hashCode( this.fbAppId );
        hash = 17 * hash + Objects.hashCode( this.fbProfileId );
        hash = 17 * hash + Objects.hashCode( this.twitterUrl );
        hash = 17 * hash + Objects.hashCode( this.twitterTitle );
        hash = 17 * hash + Objects.hashCode( this.twitterDescription );
        hash = 17 * hash + Objects.hashCode( this.twitterImage );
        hash = 17 * hash + Objects.hashCode( this.twitterCard );
        hash = 17 * hash + Objects.hashCode( this.twitterSite );
        hash = 17 * hash + Objects.hashCode( this.twitterCreator );
        hash = 17 * hash + Objects.hashCode( this.googleName );
        hash = 17 * hash + Objects.hashCode( this.googleDescription );
        hash = 17 * hash + Objects.hashCode( this.googleImage );
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
        final Post other = (Post)obj;
        if( this.includeInHistory != other.includeInHistory ) {
            return false;
        }
        if( this.draftPost != other.draftPost ) {
            return false;
        }
        if( this.privatePost != other.privatePost ) {
            return false;
        }
        if( this.paginated != other.paginated ) {
            return false;
        }
        if( !Objects.equals( this.title, other.title ) ) {
            return false;
        }
        if( !Objects.equals( this.subtitle, other.subtitle ) ) {
            return false;
        }
        if( !Objects.equals( this.author, other.author ) ) {
            return false;
        }
        if( !Objects.equals( this.link, other.link ) ) {
            return false;
        }
        if( !Objects.equals( this.layout, other.layout ) ) {
            return false;
        }
        if( !Objects.equals( this.source, other.source ) ) {
            return false;
        }
        if( !Objects.equals( this.html, other.html ) ) {
            return false;
        }
        if( !Objects.equals( this.pullQuote, other.pullQuote ) ) {
            return false;
        }
        if( !Objects.equals( this.bannerImage, other.bannerImage ) ) {
            return false;
        }
        if( !Objects.equals( this.thumbnailImage, other.thumbnailImage ) ) {
            return false;
        }
        if( !Objects.equals( this.pullQuoteImage, other.pullQuoteImage ) ) {
            return false;
        }
        if( !Objects.equals( this.ogUrl, other.ogUrl ) ) {
            return false;
        }
        if( !Objects.equals( this.ogTitle, other.ogTitle ) ) {
            return false;
        }
        if( !Objects.equals( this.ogDescription, other.ogDescription ) ) {
            return false;
        }
        if( !Objects.equals( this.ogImage, other.ogImage ) ) {
            return false;
        }
        if( !Objects.equals( this.ogSiteName, other.ogSiteName ) ) {
            return false;
        }
        if( !Objects.equals( this.ogAudio, other.ogAudio ) ) {
            return false;
        }
        if( !Objects.equals( this.ogVideo, other.ogVideo ) ) {
            return false;
        }
        if( !Objects.equals( this.ogType, other.ogType ) ) {
            return false;
        }
        if( !Objects.equals( this.ogLocale, other.ogLocale ) ) {
            return false;
        }
        if( !Objects.equals( this.fbAppId, other.fbAppId ) ) {
            return false;
        }
        if( !Objects.equals( this.fbProfileId, other.fbProfileId ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterUrl, other.twitterUrl ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterTitle, other.twitterTitle ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterDescription, other.twitterDescription ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterImage, other.twitterImage ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterCard, other.twitterCard ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterSite, other.twitterSite ) ) {
            return false;
        }
        if( !Objects.equals( this.twitterCreator, other.twitterCreator ) ) {
            return false;
        }
        if( !Objects.equals( this.googleName, other.googleName ) ) {
            return false;
        }
        if( !Objects.equals( this.googleDescription, other.googleDescription ) ) {
            return false;
        }
        if( !Objects.equals( this.googleImage, other.googleImage ) ) {
            return false;
        }
        if( !Arrays.deepEquals( this.symlinks, other.symlinks ) ) {
            return false;
        }
        if( !Objects.equals( this.date, other.date ) ) {
            return false;
        }
        if( !Arrays.deepEquals( this.tags, other.tags ) ) {
            return false;
        }
        if( !Objects.equals( this.sourceFile, other.sourceFile ) ) {
            return false;
        }
        if( this.sourceFormat != other.sourceFormat ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", subtitle=" + subtitle + ", author=" + author + ", link=" + link + ", symlinks=" + symlinks + ", date=" + date + ", layout=" + layout + ", includeInHistory=" + includeInHistory + ", draftPost=" + draftPost + ", privatePost=" + privatePost + ", source=" + source + ", html=" + html + ", tags=" + tags + ", pullQuote=" + pullQuote + ", paginated=" + paginated + ", bannerImage=" + bannerImage + ", thumbnailImage=" + thumbnailImage + ", pullQuoteImage=" + pullQuoteImage + ", sourceFile=" + sourceFile + ", sourceFormat=" + sourceFormat + ", ogUrl=" + ogUrl + ", ogTitle=" + ogTitle + ", ogDescription=" + ogDescription + ", ogImage=" + ogImage + ", ogSiteName=" + ogSiteName + ", ogAudio=" + ogAudio + ", ogVideo=" + ogVideo + ", ogType=" + ogType + ", ogLocale=" + ogLocale + ", fbAppId=" + fbAppId + ", fbProfileId=" + fbProfileId + ", twitterUrl=" + twitterUrl + ", twitterTitle=" + twitterTitle + ", twitterDescription=" + twitterDescription + ", twitterImage=" + twitterImage + ", twitterCard=" + twitterCard + ", twitterSite=" + twitterSite + ", twitterCreator=" + twitterCreator + ", googleName=" + googleName + ", googleDescription=" + googleDescription + ", googleImage=" + googleImage + '}';
    }
    
    // </editor-fold>
}
