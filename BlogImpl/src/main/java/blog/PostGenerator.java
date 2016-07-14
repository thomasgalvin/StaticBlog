package blog;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostGenerator
{
    private static final Logger logger = LoggerFactory.getLogger( PostGenerator.class );
    
    private static final String TITLE = "Title";
    private static final String SUBTITLE = "Subtitle";
    private static final String AUTHOR = "Author";
    private static final String DATE = "Date";
    private static final String LINK = "Link";
    private static final String LAYOUT = "Layout";
    private static final String FILE_NAME = "File name";
    
    private static final String BANNER_IMAGE = "Banner image";
    private static final String THUMBNIAL_IMAGE = "Thumbnail image";
    private static final String PULL_QUOTE_IMAGE = "Pull quote image";
    
    private static final String OG_URL = "OpenGraph URL";
    private static final String OG_TITLE = "OpenGraph title";
    private static final String OG_DESCRIPTION = "OpenGraph description";
    private static final String OG_IMAGE = "OpenGraph image";
    private static final String OG_SITE_NAME = "OpenGraph site name";
    private static final String OG_AUDIO = "OpenGraph audio";
    private static final String OG_VIDEO = "OpenGraph video";
    private static final String OG_TYPE = "OpenGraph type";
    private static final String OG_LOCALE = "OpenGraph locale";
    private static final String OG_FB_APP_ID = "OpenGraph Facebook App ID";
    private static final String OG_FB_PROFILE_ID = "OpenGraph Facebook profile ID";
    
    private static final String TWITTER_URL = "Twitter URL";
    private static final String TWITTER_TITLE = "Twitter title";
    private static final String TWITTER_DESCRIPTION = "Twitter description";
    private static final String TWITTER_IMAGE = "Twitter image";
    private static final String TWITTER_CARD = "Twitter card";
    private static final String TWITTER_SITE = "Twitter site";
    private static final String TWITTER_CREATOR = "Twitter creator";
    
    private static final String GOOGLE_NAME = "Google name";
    private static final String GOOGLE_DESCRIPTION = "Google description";
    private static final String GOOGLE_IMAGE = "Google image";
    
    
    private static final String VISIBILITY = "Visibility";
    private static final String VISIBILITY_PROMPT = 
         "Visibility: 1: Published; 2: Draft; 3: Private";
    private static final String PUBLISHED = "1";
    private static final String DRAFT = "2";
    private static final String PRIVATE = "3";
    
    private static final String BLOG_POST_TAG = "!blog.Post\n";
    private static final String BLOG_POST_TAGS = "tags: []\n";
    
    private static final String TEMPLATE_EMPTY = "blog/post-template.md";
    
    public void runEmpty( Config config ) throws Exception{
        String visibility = prompt( VISIBILITY_PROMPT, PUBLISHED );
        String fileName = prompt( FILE_NAME, "" );
        
        String targetDir;
        if( PRIVATE.equals( visibility ) ){
            targetDir = config.getPrivatePosts();
        }
        else if( DRAFT.equals( visibility ) ){
            targetDir = config.getDrafts();
        }
        else {
            targetDir = config.getPublished();
        }
        
        if( !StringUtils.isBlank( fileName ) ){
            File file = new File( targetDir );
            file.mkdirs();
            if( file.exists() && file.isDirectory() ){
                file = new File( file, fileName );
                if( !file.exists() ){
                    System.out.println( "Writing to: " + file.getAbsolutePath() );
                    FileOutputStream fout = new FileOutputStream( file );
        
                    InputStream input = getClass().getClassLoader().getResourceAsStream( TEMPLATE_EMPTY );
                    IOUtils.copy( input, fout );

                    IOUtils.closeQuietly( input );
                    IOUtils.closeQuietly( fout );
                }
                else {
                    System.out.println( "Error: file already exists: " + file.getAbsolutePath() );
                }
            }
            else{
                System.out.println( "Error: cannot write to: " + file.getAbsolutePath() );
            }
        }
    }
    
    public void run( Config config, String templateName, boolean asis ) throws Exception{
        Post post = null;
        
        if( !StringUtils.isBlank( templateName ) ){
            File file = new File( config.getTemplates(), templateName );
            if( file.exists() && file.canRead() ){
                post = PostLoader.readPost( file );
            }
            else {
                logger.error( "Cannot load template file: " + file.getAbsolutePath() );
            }
        }
        
        if( post == null ){
            post = new Post();
        }
        
        if( !asis ){
            getValues( post );
        }
        
        String visibility = prompt( VISIBILITY_PROMPT, PUBLISHED );
        boolean generate = confirm( post, visibility );
        
        if( generate ){
            generate( post, visibility, config );
        }
    }
    
    private void getValues( Post post ){
        System.out.println( "General metadata:" );
        post.setTitle( prompt( TITLE, post.getTitle() ) );
        post.setSubtitle( prompt( SUBTITLE, post.getSubtitle() ) );
        post.setAuthor( prompt( AUTHOR, post.getAuthor() ) );
        post.setLink( prompt( LINK, post.getLink() ) );
        post.setLayout( prompt( LAYOUT, post.getLayout() ) );
        
        post.setDate( new Date() );
        String dateValue = toString( post.getDate() );
        dateValue = prompt( DATE, dateValue );
        post.setDate( toDate( dateValue ) );
        
        System.out.println( "\nImages:" );
        post.setBannerImage( prompt( BANNER_IMAGE, post.getBannerImage() ) );
        post.setThumbnailImage( prompt( THUMBNIAL_IMAGE, post.getThumbnailImage() ) );
        post.setPullQuoteImage( prompt( PULL_QUOTE_IMAGE, post.getPullQuoteImage() ) );
        
        System.out.println( "\nOpenGraph metadata:" );
        post.setOgUrl( prompt( OG_URL, post.getOgUrl() ) );
        post.setOgTitle( prompt( OG_TITLE, post.getOgTitle() ) );
        post.setOgDescription( prompt( OG_DESCRIPTION, post.getOgDescription() ) );
        post.setOgImage( prompt( OG_IMAGE, post.getOgImage() ) );
        post.setOgSiteName( prompt( OG_SITE_NAME, post.getOgSiteName() ) );
        post.setOgAudio( prompt( OG_AUDIO, post.getOgAudio() ) );
        post.setOgVideo( prompt( OG_VIDEO, post.getOgVideo() ) );
        post.setOgType( prompt( OG_TYPE, post.getOgType() ) );
        post.setOgLocale( prompt( OG_LOCALE, post.getOgLocale() ) );
        post.setFbAppId( prompt( OG_FB_APP_ID, post.getFbAppId() ) );
        post.setFbProfileId( prompt( OG_FB_PROFILE_ID, post.getFbProfileId() ) );
        
        System.out.println( "\nTwitter metadata:" );
        post.setTwitterUrl( prompt( TWITTER_URL, post.getTwitterUrl() ) );
        post.setTwitterTitle( prompt( TWITTER_TITLE, post.getTwitterTitle() ) );
        post.setTwitterDescription( prompt( TWITTER_DESCRIPTION, post.getTwitterDescription() ) );
        post.setTwitterImage( prompt( TWITTER_IMAGE, post.getTwitterImage() ) );
        post.setTwitterCard( prompt( TWITTER_CARD, post.getTwitterCard() ) );
        post.setTwitterSite( prompt( TWITTER_SITE, post.getTwitterSite() ) );
        post.setTwitterCreator( prompt( TWITTER_CREATOR, post.getTwitterCreator() ) );
        
        System.out.println( "\nGoogle/G+ metadata:" );
        post.setGoogleName( prompt( GOOGLE_NAME, post.getGoogleName() ) );
        post.setGoogleDescription( prompt( GOOGLE_DESCRIPTION, post.getGoogleDescription() ) );
        post.setGoogleImage( prompt( GOOGLE_IMAGE, post.getGoogleImage() ) );
    }

    private String toString( Date date ){
        if( date == null ){
            System.out.println( "Null" );
            return "";
        }
        
        DateTime dateTime = new DateTime( date.getTime() );
        String result = dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth();
        
        int hour = dateTime.getHourOfDay();
        int minute = dateTime.getMinuteOfHour();
        int second = dateTime.getSecondOfMinute();
        if( hour != 0 || minute != 0 || second != 0 ){
            result += " " + hour + ":" + minute + ":" + second;
        }
        
        return result;
    }
    
    private Date toDate( String date ){
        try{
            YamlReader reader = new YamlReader( date );
            Date result = reader.read( Date.class );
            return result;
        }
        catch( Throwable t ){
            System.out.println( "Bad date: " + date );
            return null;
        }
    }
    
    private boolean confirm( Post post, String visibility ){
        System.out.println( "Post:" );
        confirm( TITLE, post.getTitle() );
        confirm( SUBTITLE, post.getSubtitle() );
        confirm( AUTHOR, post.getAuthor() );
        confirm( LINK, post.getLink() );
        confirm( LAYOUT, post.getLayout() );
        confirm( DATE, toString( post.getDate() ) );
        
        if( PRIVATE.equals( visibility ) ){
            confirm( VISIBILITY, "Private" );
        }
        else if( DRAFT.equals( visibility ) ){
            confirm( VISIBILITY, "Draft" );
        }
        else {
            confirm( VISIBILITY, "Published" );
        }
        
        System.out.println( "\nImages:" );
        confirm( BANNER_IMAGE, post.getBannerImage() );
        confirm( THUMBNIAL_IMAGE, post.getThumbnailImage() );
        confirm( PULL_QUOTE_IMAGE, post.getPullQuoteImage() );
        
        System.out.println( "\nOpenGraph metadata:" );
        confirm( OG_URL, post.getOgUrl() );
        confirm( OG_TITLE, post.getOgTitle() );
        confirm( OG_DESCRIPTION, post.getOgDescription() );
        confirm( OG_IMAGE, post.getOgImage() );
        confirm( OG_SITE_NAME, post.getOgSiteName() );
        confirm( OG_AUDIO, post.getOgAudio() );
        confirm( OG_VIDEO, post.getOgVideo() );
        confirm( OG_TYPE, post.getOgType() );
        confirm( OG_LOCALE, post.getOgLocale() );
        confirm( OG_FB_APP_ID, post.getFbAppId() );
        confirm( OG_FB_PROFILE_ID, post.getFbProfileId() );
        
        System.out.println( "\nTwitter metadata:" );
        confirm( TWITTER_URL, post.getTwitterUrl() );
        confirm( TWITTER_TITLE, post.getTwitterTitle() );
        confirm( TWITTER_DESCRIPTION, post.getTwitterDescription() );
        confirm( TWITTER_IMAGE, post.getTwitterImage() );
        confirm( TWITTER_CARD, post.getTwitterCard() );
        confirm( TWITTER_SITE, post.getTwitterSite() );
        confirm( TWITTER_CREATOR, post.getTwitterCreator() );
        
        System.out.println( "\nGoogle/G+ metadata:" );
        confirm( GOOGLE_NAME, post.getGoogleName() );
        confirm( GOOGLE_DESCRIPTION, post.getGoogleDescription() );
        confirm( GOOGLE_IMAGE, post.getGoogleImage() );
        
        
        String confirm = prompt( "Generate post? [Y/N]", null );
        if( !StringUtils.isBlank( confirm ) ){
            return confirm.toLowerCase().startsWith( "y" );
        }
        return false;
    }
    
    private String prompt( String name, String value ){
        System.out.print( name );
        if( !StringUtils.isBlank( value ) ){
            System.out.print( " [" );
            System.out.print( value );
            System.out.print( "]" );
        }
        System.out.print( ": " );
        
        String result = System.console().readLine();
        if( StringUtils.isBlank( result ) ){
            return value;
        }
        return result;
    }
    
    private void confirm( String name, String value ) {
        System.out.println( "    " + name + ": " + neverNull( value ) );
    }
    
    private void generate( Post post, String visibility, Config config ) throws Exception{
        System.out.println( "Generating ..." );
        
        String body = post.getSource();
        if( body == null ){
            body = "";
        }
        
        post.setSource( null );
        post.setSourceFile( null );
        
        StringWriter string = new StringWriter();
        YamlWriter writer = new YamlWriter( string );
        writer.write( post );
        writer.close();
        String yaml = string.toString();
        yaml = yaml.replace( BLOG_POST_TAG, "" );
        yaml = yaml.replace( BLOG_POST_TAGS, "" );
        
        StringBuilder result = new StringBuilder();
        result.append( "---\n" );
        result.append( yaml );
        result.append( "\n---\n\n" );
        result.append( body );
        result.append( "\n" );
        
        String targetDir;
        if( PRIVATE.equals( visibility ) ){
            targetDir = config.getPrivatePosts();
        }
        else if( DRAFT.equals( visibility ) ){
            targetDir = config.getDrafts();
        }
        else {
            targetDir = config.getPublished();
        }
        
        String fileName = prompt( FILE_NAME, "" );
        if( !StringUtils.isBlank( fileName ) ){
            File file = new File( targetDir );
            file.mkdirs();
            if( file.exists() && file.isDirectory() ){
                file = new File( file, fileName );
                if( !file.exists() ){
                    System.out.println( "Writing to: " + file.getAbsolutePath() );
                    FileUtils.write( file, result.toString() );
                }
                else {
                    System.out.println( "Error: file already exists: " + file.getAbsolutePath() );
                }
            }
            else{
                System.out.println( "Error: cannot write to: " + file.getAbsolutePath() );
            }
        }
    }
    
    private String neverNull( String value ){
        return value == null ? "" : value;
    }
}
