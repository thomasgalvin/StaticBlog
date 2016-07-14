package blog;

import blog.publish.FileSystemPublisher;
import blog.publish.Publisher;
import galvin.Lorem;
import java.io.File;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenderTest
{
    private static final Logger logger = LoggerFactory.getLogger( RenderTest.class );
    private static String INDEX_TEMPLATE = "<h1>Hello, world!</h1>";
    
    private static String POST_TEMPLATE = 
          "---\n"
        + "title: Post One\n"
        + "subtitle: The first post\n"
        + "author: thomas\n"
        + "tags:\n"
        + "  - Tag One\n"
        + "  - Tag Two\n"
        + "  - Tag Three\n"
        + "date: 2016-04-07\n"
        + "---\n"
        + Lorem.loremIpsum() + "\n\n"
        + Lorem.loremIpsum() + "\n\n"
        + "<!--more-->\n\n"
        + Lorem.loremIpsum() + "\n\n"
        + Lorem.loremIpsum() + "\n\n"
        + "<!--more-->\n\n"
        + Lorem.loremIpsum() + "\n\n"
        + Lorem.loremIpsum() + "\n\n";
    
    private static String AUTHORS = 
        "id: thomas\n"
      + "displayName: thomas\n"
      + "fullName: Thomas Galvin\n"
      + "sortByName: Galvin, Thomas\n"
      + "bio: Thomas is awesome\n"
      + "---\n"
      + "displayName: bill\n"
      + "fullName: William Shakespeare\n"
      + "sortByName: Shakespeare, William\n"
      + "bio: Some old English dudes";
    
    private static String SITE = 
        "title: Thomas Galvin\n"
      + "subtitle: Purveyor of File Pulp Fiction\n"
      + "about: My blog\n"
      + "postsPerPage: 5\n"
      + "landingPage: Post1.md\n"
      + "rssTitle: My Website\n"
      + "rssLink: http://localhost/feed.rss.xml\n" 
      + "rssDescription: What's this all about, then?\n"
      + "rssCopyright: Copyright (C) 2016All Right Reserved\n"
      + "rssAuthor: Author McAuthorface";
    
    @Test
    public void testRender() throws Exception{
        boolean verbose = false;
        File root = new File( "target/RenderTest" );
        File src = new File( root, "src" );
        File posts = new File( src, "posts/published" );
        File drafts = new File( src, "posts/drafts" );
        File data = new File( src, "data" );
        File raw = new File( src, "static" );

        posts.mkdirs();
        drafts.mkdirs();
        data.mkdirs();
        raw.mkdirs();
        
        File authors = new File( data, "authors.yml" );
        FileUtils.write( authors, AUTHORS );
        
        File site = new File( data, "site.yml" );
        FileUtils.write( site, SITE );
        
//        File index = new File( raw, "index.html");
//        FileUtils.write( index, INDEX_TEMPLATE );
        
        DateTime date = new DateTime( 1980, 4, 7, 0, 0 );
        for( int i = 1; i <= 5; i++ ){
            String datestamp = date.getYear() + "-" + date.getMonthOfYear() + "-" + date.getDayOfMonth();
            date = date.plusDays( 1 );
            String post = generatePost( "Post " + i, "This is post " + i, datestamp , "Cat1", "Cat3", "Cat5" );
            File output = new File( posts, "Post" + i + ".md" );
            FileUtils.write( output, post );
        }
        
        for( int i = 6; i <= 10; i++ ){
            String datestamp = date.getYear() + "-" + date.getMonthOfYear() + "-" + date.getDayOfMonth();
            date = date.plusDays( 1 );
            String post = generatePost( "Post " + i, "This is post " + i, datestamp , "Cat2", "Cat4", "Cat6" );
            File output = new File( posts, "Post" + i + ".md" );
            FileUtils.write( output, post );
        }
        
        for( int i = 11; i <= 15; i++ ){
            String datestamp = date.getYear() + "-" + date.getMonthOfYear() + "-" + date.getDayOfMonth();
            date = date.plusDays( 1 );
            String post = generatePost( "Post " + i, "This is post " + i, datestamp , "Cat1", "Cat2", "Cat3" );
            File output = new File( posts, "Post" + i + ".md" );
            FileUtils.write( output, post );
        }
        
        for( int i = 16; i <= 20; i++ ){
            String datestamp = date.getYear() + "-" + date.getMonthOfYear() + "-" + date.getDayOfMonth();
            date = date.plusDays( 1 );
            String post = generatePost( "Post " + i, "This is post " + i, datestamp , "Cat4", "Cat5", "Cat6" );
            File output = new File( posts, "Post" + i + ".md" );
            FileUtils.write( output, post );
        }
        
        RendererImpl renderer = new RendererImpl( root, verbose );
        renderer.render();
        
        logger.info( "### Test: Publishing to copy" );
        
        File copy = new File( "target/RenderTest/copy" );
        Publisher publisher = new FileSystemPublisher( copy );
        publisher.doFullPush( new File( root, "site" ) );
        
        logger.info( "### Test: Publishing with checksums; should be empty directories" );
        
        File noopDir = new File( "target/RenderTest/noop" );
        publisher = new FileSystemPublisher( noopDir );
        
        Checksum checksums = RendererImpl.processChecksums( copy, "/", null, verbose );
        publisher.doPartialPush( new File( root, "site" ), checksums );
        ensureEmpty( noopDir );
    }
    
    private void ensureEmpty( File file ) throws Exception{
        if( !file.isDirectory() ){
            throw new Exception( "Not a directory: " + file.getAbsolutePath() );
        }
        for( File child : file.listFiles() ){
            ensureEmpty( child );
        }
    }
    
    public String generatePost( String title, String subtitle, String date, String ... tags ){
        StringBuilder result = new StringBuilder();
        
        result.append( "---\n" );
        
        result.append( "title: " );
        result.append( title );
        result.append( "\n" );
        
        result.append( "subtitle: " );
        result.append( subtitle );
        result.append( "\n" );
        
        result.append( "author: thomas\n" );
        
        result.append( "tags:\n" );
        for( String tag : tags ){
            result.append( "  - " );
            result.append( tag );
            result.append( "\n" );
        }
        
        result.append( "date: " );
        result.append( date );
        result.append( "\n" );
        
        result.append( "symlinks:\n" );
        result.append( "    - " );
        result.append( UUID.randomUUID().toString() );
        result.append( "/\n\n" );
        
        result.append( "---\n" );
        
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( "<!--more-->" );
        
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( "<!--more-->" );
        
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( "<!--more-->" );
        
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( "<!--more-->" );
        
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( Lorem.loremIpsum() );
        result.append( "\n\n" );
        result.append( "<!--more-->" );
        
        return result.toString();
    }
}
