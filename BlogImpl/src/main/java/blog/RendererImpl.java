package blog;

import com.esotericsoftware.yamlbeans.YamlReader;
import freemarker.template.Template;
import blog.sort.PagedPosts;
import blog.sort.ReversePublicationDateComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedOutput;
import galvin.pandoc.Format;
import galvin.pandoc.Options;
import galvin.pandoc.Pandoc;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import galvin.FileUtils;
import java.io.FileFilter;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import static org.apache.commons.io.FileUtils.checksumCRC32;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RendererImpl implements Renderer {
    public static final String CONFIG_YML = "data/config.yml";
    public static final String FULL_ARTICLE_HTML = "full.html";
    public static final String INDEX_HTML = "index.html";
    public static final String SITE = "site.yml";
    public static final String PAGINATE = "<!--more-->";
    public static final String PULL_QUOTE = "<!--pull-->";
    public static final String LAYOUT_LINKS = "links";
    
    public static final String SOCIAL_MEDIA_METADATA_PLACEHOLDER = "<!--social_media_metadata-->";
    public static final String FACEBOOK_PROPERTY_NAME="property";
    public static final String TWITTER_PROPERTY_NAME="name";
    public static final String GOOGLE_PROPERTY_NAME="itemprop";
    

    private static final Logger logger = LoggerFactory.getLogger( RendererImpl.class );
    private FileFilter filter = new DefaultFileFilter();
    private boolean verbose;

    private File root;
    private File publishedDir;
    private File draftsDir;
    private File privateDir;
    private File layoutsDir;
    private File includesDir;
    private File dataDir;
    private File staticDir;
    private File uploadsDir;
    private File siteDir;

    private List<Post> posts = new ArrayList();
    private List<Post> drafts = new ArrayList();
    private List<Post> privatePosts = new ArrayList();
    private Includes includes;

    private Config config;
    private SiteMetadata site;
    private TemplateLoader templateLoader;
    private AuthorLoader authors;

    private Post indexPost;
    private boolean writeToDisk = true;

    public RendererImpl( File root, boolean verbose )
        throws IOException {
        this( root, CONFIG_YML, verbose );
    }

    public RendererImpl( File root, String configFileName, boolean verbose )
        throws IOException {
        if( StringUtils.isBlank( configFileName ) ) {
            configFileName = CONFIG_YML;
        }
        File configFile = new File( root, configFileName );
        config = readConfig( configFile );

        init( root, config, verbose );
    }

    public RendererImpl( File root, Config config, boolean verbose )
        throws IOException {
        init( root, config, verbose );
    }

    private void init( File root, Config config, boolean verbose )
        throws IOException {
        this.root = root;
        this.config = config;
        this.verbose = verbose;

        loadConfig();
        loadData();
        loadTemplates();
        loadPosts();

        mkdirs();
    }

    private void loadConfig() throws IOException {
        publishedDir = new File( root, config.getPublished() );
        draftsDir = new File( root, config.getDrafts() );
        privateDir = new File( root, config.getPrivatePosts() );
        layoutsDir = new File( root, config.getLayouts() );
        includesDir = new File( root, config.getIncludes() );
        dataDir = new File( root, config.getData() );
        staticDir = new File( root, config.getStaticDir() );
        uploadsDir = new File( root, config.getUploads() );
        siteDir = new File( root, config.getSite() );
    }

    public static Config readConfig( File file ) throws IOException {
        Config result = null;
        if( file != null && file.exists() ) {
            YamlReader reader = new YamlReader( new FileReader( file ) );
            result = reader.read( Config.class );
        }

        if( result == null ){
            result = new Config();
        }
        return result;
    }

    private void loadData()
        throws IOException {
        if( dataDir.exists() ) {
            authors = new AuthorLoader( dataDir );

            File siteYaml = null;

            String overrideSiteYaml = config.getSiteMetadataFile();
            if( !StringUtils.isBlank( overrideSiteYaml ) ) {
                siteYaml = new File( overrideSiteYaml );
            }
            else {
                siteYaml = new File( dataDir, SITE );
            }

            if( siteYaml.exists() ) {
                YamlReader reader = new YamlReader( new FileReader( siteYaml ) );
                site = reader.read( SiteMetadata.class );
            }

            if( site == null ) {
                site = new SiteMetadata();
            }
        }
        
        if( includesDir.exists() ){
            includes = Includes.load( includesDir );
        }
        else{
            includes = new Includes();
        }
    }

    @Override
    public void loadTemplates() {
        templateLoader = new TemplateLoader( layoutsDir );
    }

    @Override
    public void loadPosts() {
        PostLoader loader = new PostLoader( publishedDir, draftsDir, privateDir, config, verbose );
        posts = loader.getPosts();
        drafts = loader.getDrafts();
        privatePosts = loader.getPrivatePosts();
    }

    private void mkdirs() {
        publishedDir.mkdirs();
        draftsDir.mkdirs();
        layoutsDir.mkdirs();
        includesDir.mkdirs();
        dataDir.mkdirs();
        staticDir.mkdirs();
        uploadsDir.mkdirs();
        siteDir.mkdirs();
    }

    @Override
    public synchronized void render() {
        logger.info( "Rendering..." );
        
        renderPosts();
        renderHistory();
        renderTags();
        renderIndex();
        
        if( config != null && config.renderRss() ){
            renderRss();
        }

        copyStatic();
        renderChecksums();
        
        logger.info( "Render complete." );
    }

    /// render posts ///
    @Override
    public void renderPosts() {
        logger.info( "Rendering posts" );
        render( posts );

        if( config.renderDrafts() ) {
            logger.info( "Rendering draft posts" );
            render( drafts );
        }

        if( config.renderPrivate() ) {
            logger.info( "Rendering private posts" );
            render( privatePosts );
        }
    }

    @Override
    public void renderRss() {
        logger.info( "Rendering RSS feed" );
        
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType( "rss_2.0" );

        feed.setTitle( site.getRssTitle() );
        feed.setLink( site.getRssLink() );
        feed.setDescription( site.getRssDescription() );
        feed.setCopyright( site.getRssCopyright() );
        feed.setAuthor( site.getRssAuthor() );
        feed.setPublishedDate( new Date() );

        List<SyndEntry> entries = new ArrayList();
        List<Post> rssFeedPosts = new ArrayList();
        
        
        for( Post post : posts ){
            if( post.getIncludeInHistory() ){
                rssFeedPosts.add( post );
            }
        }
        
        if( config.renderDrafts() ) {
            for( Post post : drafts ) {
                if( post.getIncludeInHistory() ) {
                    rssFeedPosts.add( post );
                }
            }
        }

        if( config.renderPrivate() ) {
            for( Post post : privatePosts ) {
                if( post.getIncludeInHistory() ) {
                    rssFeedPosts.add( post );
                }
            }
        }
        
        Collections.sort( rssFeedPosts, new ReversePublicationDateComparator() );
        if( rssFeedPosts.size() > config.getRssFeedPostCount() ){
            rssFeedPosts = rssFeedPosts.subList( 0, config.getRssFeedPostCount() );
        }

        addEntries( entries, rssFeedPosts );
        feed.setEntries( entries );

        File rssDir = siteDir;
        String dir = site.getFeed();
        if( !StringUtils.isBlank( dir ) ) {
            rssDir = new File( rssDir, dir );
        }
        rssDir.mkdirs();
        
        try {
            StringWriter writer = new StringWriter();
            SyndFeedOutput output = new SyndFeedOutput();
            output.output( feed, writer );
            
            String rssData = writer.toString();
            
            String relativeUrl = "href=\"/";
            String replacementUrl = buildReplacementUrl( "href", "\"" );
            rssData = rssData.replace( relativeUrl, replacementUrl );
            
            relativeUrl = "href='/";
            replacementUrl = buildReplacementUrl( "href", "'" );
            rssData = rssData.replace( relativeUrl, replacementUrl );
            
            relativeUrl = "src=\"/";
            replacementUrl = buildReplacementUrl( "src", "\"" );
            rssData = rssData.replace( relativeUrl, replacementUrl );
            
            relativeUrl = "src='/";
            replacementUrl = buildReplacementUrl( "src", "'" );
            rssData = rssData.replace( relativeUrl, replacementUrl );
            
            File rssFile = new File( rssDir, "rss.xml" );
            if( writeToDisk ){
                FileUtils.write( rssFile, rssData );
            }
            
        }
        catch( Throwable t ) {
            logger.error( "Error writing RSS Feed", t );
        }
    }
    
    private String buildReplacementUrl( String attribute, String quote ){
        return attribute + "=" + quote + neverNull( site.getWebAddress() ) + "/";
    }
    
    private String neverNull( String string ){
        return string == null ? "" : string;
    }

    private void addEntries( List<SyndEntry> entries, List<Post> posts ) {
        for( Post post : posts ) {
            if( entries.size() >= config.getRssFeedPostCount() ) {
                return;
            }

            SyndEntryImpl entry = new SyndEntryImpl();
            entry.setTitle( post.getTitle() );
            
            if( !StringUtils.isBlank( post.getAuthor() ) ) {
                Author author = authors.get( post.getAuthor() );
                if( author != null ){
                    entry.setAuthor( author.getDisplayName() );
                }
                else{
                    entry.setAuthor( post.getAuthor() );
                }
            }
            
            String link = site.getWebAddress() + site.getBlogRoot() + "/" + post.getLink();
            entry.setLink( link );
            
            entry.setPublishedDate( post.getDate() );

            SyndContentImpl description = new SyndContentImpl();
            description.setType( "text/html" );
            String body = post.getHtml();
            description.setValue( body );
            entry.setDescription( description );

            entries.add( entry );
        }

    }

    private File getPostsTargetDir(){
        File postsDir = siteDir;
        String dir = site.getPosts();
        if( !StringUtils.isBlank( dir ) ) {
            postsDir = new File( postsDir, dir );
        }
        postsDir.mkdirs();
        return postsDir;
    }
    
    @Override
    public void renderSourceFiles( List<File> files ){
        writeToDisk = false;
        renderPosts();
        writeToDisk = true;
        
        List<Post> toRender = new ArrayList();
        for( File file : files ){
            for( Post post : posts ){
                if( file.equals( post.getSourceFile() ) ){
                    toRender.add( post );
                }
            }
            
            if( config.renderDrafts() ){
                for( Post post : drafts ){
                    if( file.equals( post.getSourceFile() ) ){
                        toRender.add( post );
                    }
                }
            }

            if( config.renderPrivate()){
                for( Post post : privatePosts ){
                    if( file.equals( post.getSourceFile() ) ){
                        toRender.add( post );
                    }
                }
            }
        }
        
        for( Post post : toRender ){
            logger.info( "Post modified: " + post.getTitle() );
        }
        
        render( toRender );
    }
    
    private void render( List<Post> posts ) {
        try {
            File postsDir = getPostsTargetDir();

            for( Post post : posts ) {
                render( postsDir, post );
            }
        }
        catch( Throwable t ) {
            logger.error( "Error rendering posts", t );
        }
    }

    private String getUrl( String link ) {
        String baseUrl = site.getBlogRoot() + "/" + link;
        if( !baseUrl.endsWith( "/" ) ) {
            baseUrl += "/";
        }
        return baseUrl;
    }

    private void render( File postsDir, Post post ) {
        String title = post.getTitle();
        if( post.isDraftPost() ) {
            title += " (draft)";
        }
        if( post.isPrivatePost() ) {
            title += " (private)";
        }
        
        if( isVerbose() ){
            logger.info( "Rendering post: " + title );
        }

        try {
            String landingPage = site.getLandingPage();
            if( !StringUtils.isBlank( landingPage ) ) {
                if( landingPage.equals( post.getSourceFile().getName() ) ) {
                    indexPost = post;
                    
                    if( isVerbose() ){
                        logger.info( "Landing page found: " + title );
                    }
                }
            }

            String link = post.getLink();
            String baseUrl = getUrl( link );
            String unpaginated = baseUrl + FULL_ARTICLE_HTML;
            
            String canonicalUrl = site.getWebAddress().trim();
            if( !canonicalUrl.endsWith( "/" ) && !unpaginated.startsWith( "/" ) ){
                canonicalUrl += "/";
            }
            canonicalUrl += unpaginated;
            
            String[] symlinks = post.getSymlinks();
            if( symlinks != null ){
                for( String symlink: symlinks ){
                    renderSymlink( postsDir, post, baseUrl, symlink );
                }
            }
            
            File dir = new File( postsDir, link );
            dir.mkdirs();

            Navigation navigation = new Navigation();
            navigation.setPermalink( unpaginated );

            Author author = null;
            if( !StringUtils.isBlank( post.getAuthor() ) ) {
                author = authors.get( post.getAuthor() );
            }

            Template template = templateLoader.loadTemplate( post.getLayout() );
            
            String source = post.getSource();
            String html = toHtml( source, post.getSourceFormat() );
            post.setHtml( html );
            
            post.setPaginated( false );
            if( StringUtils.isBlank( post.getPullQuote() ) ) {
                String pullQuote = html;
                
                int index = pullQuote.indexOf( PULL_QUOTE );
                if( index != -1 ) {
                    post.setPaginated( true );
                    pullQuote = pullQuote.substring( 0, index );
                }
                else {
                    index = pullQuote.indexOf( PAGINATE );
                    if( index != -1 ) {
                        post.setPaginated( true );
                        pullQuote = pullQuote.substring( 0, index );
                    }
                }

                post.setPullQuote( pullQuote );
            }
            else{
                post.setPaginated( true );
            }
            
            String[] tags = post.getTags();
            List<Link> tagLinks = generateTagLinks( tags );

            String body = render( template, site, post, null, author, null, tagLinks, navigation, canonicalUrl );
            File output = new File( dir, FULL_ARTICLE_HTML );
            if( writeToDisk ){
                FileUtils.write( output, body );
            }

            List<Post> pages = paginate( post );
            if( pages.size() == 1 ) {
                navigation.setPermalink( baseUrl );
                body = render( template, site, post, null, author, null, tagLinks, navigation, canonicalUrl );
                output = new File( dir, INDEX_HTML );
                if( writeToDisk ){
                    FileUtils.write( output, body );
                }
            }
            else {
                int pageNumber = 1;
                String previous = null;
                String next = null;
                navigation.setPageCount( pages.size() );

                for( Post page : pages ) {
                    navigation.setUnpaginated( unpaginated );
                    navigation.setPrevious( previous );
                    navigation.setCurrentPage( pageNumber );

                    output = dir;
                    String pageUrl = baseUrl;
                    if( pageNumber != 1 ) {
                        output = new File( dir, "" + pageNumber );
                        pageUrl += pageNumber + "/";
                    }

                    output.mkdirs();
                    output = new File( output, INDEX_HTML );
                    navigation.setPermalink( pageUrl );

                    if( pageNumber < pages.size() ) {
                        next = baseUrl + ( pageNumber + 1 ) + "/";
                    }
                    else {
                        next = null;
                    }
                    navigation.setNext( next );

                    navigation.setPrevious( previous );
                    navigation.setNext( next );
                    navigation.setUnpaginated( unpaginated );

                    body = render( template, site, page, posts, author, null, tagLinks, navigation, canonicalUrl );
                    if( writeToDisk ){
                        FileUtils.write( output, body );
                    }

                    previous = pageUrl;
                    pageNumber++;
                }
            }
        }
        catch( Exception ex ) {
            logger.error( "Error processing template", ex );
        }
    }
    
    private String render( Template template,
                           SiteMetadata site,
                           Post post,
                           List<Post> posts,
                           Author author,
                           String category,
                           List<Link> categories,
                           Navigation navigation,
                           String canonicalUrl )
        throws Exception {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put( "site", site );
        dataModel.put( "post", post );
        dataModel.put( "posts", posts );
        dataModel.put( "author", author );
        dataModel.put( "category", category );
        dataModel.put( "categories", categories );
        dataModel.put( "navigation", navigation );
        dataModel.put( "includes", includes );
        
        StringWriter writer = new StringWriter();
        template.process( dataModel, writer );
        String body = writer.toString();
        IOUtils.closeQuietly( writer );
        
        if( post != null ){
            body = populateMetadata( body, post, site, author, canonicalUrl );
        }
        
        return body;
    }
    
    private static final String SYMLINK_HTML = "blog/templates/symlink.html";
    
    private void renderSymlink( File postsDir, Post post, String realLink, String symlink ) {
        try{
            InputStream stream = RendererImpl.class.getClassLoader().getResourceAsStream( SYMLINK_HTML );
            if( stream != null ){
                String content = IOUtils.toString( stream );
                IOUtils.closeQuietly( stream );
                
                content = content.replaceAll( "##URL##", realLink );

                File dir = new File( postsDir, symlink );
                dir.mkdirs();
                
                File index = new File( dir, "index.html" );
                if( writeToDisk ){
                    FileUtils.write( index, content );
                }
                
                if( isVerbose() ){
                    logger.info( "Writing symlink to: " + index.getAbsolutePath() );
                }
            }
        }
        catch( Throwable t ){
            logger.error( "Error rendering symlink: " + symlink, t );
        }
    }

    /// history ///
    @Override
    public void renderHistory() {
        List<Post> allPosts = new ArrayList();
        allPosts.addAll( posts );

        if( config.renderDrafts() ) {
            allPosts.addAll( drafts );
        }

        if( config.renderPrivate() ) {
            allPosts.addAll( privatePosts );
        }

        renderLinks( allPosts, null );
    }

    //TODO: render an index.html page for tags
    @Override
    public void renderTags() {
        List<String> tags = getTags();
        for( String tag : tags ) {
            List<Post> posts = getPosts( tag );
            renderLinks( posts, tag );
        }
    }

    private List<String> getTags() {
        Set<String> working = new HashSet();
        getTags( working, posts );
        if( config.renderDrafts() ) {
            getTags( working, drafts );
        }

        List<String> result = new ArrayList();
        result.addAll( working );
        Collections.sort( result );
        return result;
    }

    private void getTags( Set<String> working, List<Post> posts ) {
        for( Post post : posts ) {
            String[] tags = post.getTags();
            if( tags != null ) {
                for( String tag : tags ) {
                    working.add( tag );
                }
            }
        }
    }

    private List<Post> getPosts( String tag ) {
        List<Post> result = new ArrayList();
        getPosts( tag, result, posts );
        if( config.renderDrafts() ) {
            getPosts( tag, result, drafts );
        }

        return result;
    }

    private void getPosts( String tag, List<Post> result, List<Post> posts ) {
        if( StringUtils.isEmpty( tag ) ) {
            return;
        }

        for( Post post : posts ) {
            String[] tags = post.getTags();
            if( tags != null ) {
                for( String test : tags ) {
                    if( tag.equals( test ) ) {
                        result.add( post );
                    }
                }
            }
        }
    }

    private String toTagDir( String category ) {
        String result = site.getTags();
        if( StringUtils.isBlank( result ) ) {
            result = SiteMetadata.DEFAULT_TAGS;
        }
        String categoryLink = LinkGenerator.toLink( category );
        result += "/" + categoryLink;
        return result;
    }
    
    public List<Link> generateTagLinks( String[] tags ){
        List<Link> tagLinks = new ArrayList();
        if( tags != null ) {
            for( String tag : tags ) {
                String tagLink = toTagDir( tag );
                tagLink = site.getBlogRoot() + "/" + tagLink + "/1/";
                Link link = new Link( tag, tagLink );
                tagLinks.add( link );
            }
        }
        
        return tagLinks;
    }
    
    private void renderLinks( List<Post> allPosts, String category ) {
        try {
            Iterator<Post> iter = allPosts.iterator();
            while( iter.hasNext() ) {
                Post post = iter.next();
                if( !post.getIncludeInHistory() ) {
                    iter.remove();
                }
            }

            Collections.sort( allPosts, new ReversePublicationDateComparator() );
            PagedPosts pages = new PagedPosts( allPosts, site.getPostsPerPage() );
            Navigation navigation = new Navigation();

            Template template = templateLoader.loadTemplate( LAYOUT_LINKS );

            File output = null;
            String dir = null;

            String baseUrl = site.getBlogRoot() + "/";
            if( StringUtils.isBlank( category ) ) {
                dir = site.getHistory();
                if( StringUtils.isBlank( dir ) ) {
                    dir = SiteMetadata.DEFAULT_HISTORY;
                }
            }
            else {
                dir = toTagDir( category );
            }

            baseUrl += dir;
            if( !baseUrl.endsWith( "/" ) ) {
                baseUrl += "/";
            }

            output = new File( siteDir, dir );
            output.mkdirs();

            int pageNumber = 1;
            File indexFile = null;
            String next = null;
            navigation.setPageCount( pages.getPageCount() );

            while( pages.hasNext() ) {
                List<Post> page = pages.next();

                indexFile = new File( output, "" + pageNumber );
                indexFile.mkdirs();
                indexFile = new File( indexFile, INDEX_HTML );

                if( pages.hasNext() ) {
                    navigation.setNext( baseUrl + ( pageNumber + 1 ) + "/" );
                }
                else {
                    navigation.setNext( null );
                }

                ///render
                String html = render( template, site, null, page, null, category, null, navigation, null );
                if( writeToDisk ){
                    FileUtils.write( indexFile, html );
                }

                navigation.setPrevious( baseUrl + pageNumber + "/" );
                pageNumber++;

            }
        }
        catch( Throwable t ) {
            logger.error( "Error rendering history", t );
        }
    }

    /// utils ///
    
    private String toHtml( Post post ) throws IOException {
        String html = post.getHtml();
        if( !StringUtils.isBlank( html ) ){
            return html;
        }
        
        return toHtml( post.getSource(), post.getSourceFormat() );
    }
    
    private String toHtml( String source, SourceFormat format ) throws IOException {
        String result;
        if( format == SourceFormat.Html ){
            result = source;
        }
        else {
            result = markdownToHtmlSnippet( source );
        }
        
        return result;
    }
    
    private String populateMetadata( String html, Post post, SiteMetadata site, Author author, String canonicalUrl ){
        if( html.contains( "og:title" ) ){
            return html;
        }
        
        StringBuilder metadata = new StringBuilder();
        
        String siteTitle = site.getTitle();
        String subtitle = site.getSubtitle();
        if( !StringUtils.isBlank( subtitle ) ){
            siteTitle += " - " + subtitle;
        }
        
        String authorTwitter = author == null ? "" : author.getTwitterHandle();
        
        metadata.append( "<!-- OpenGraph / Facebook metadata -->\n" );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:url", post.getOgUrl(), canonicalUrl ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:title", post.getOgTitle(), post.getTitle() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:description", post.getOgDescription(), post.getPullQuote() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:image", post.getOgImage() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:site_name", post.getOgSiteName(), siteTitle ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:audio", post.getOgAudio() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:video", post.getOgVideo() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:type", post.getOgType() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "og:locale", post.getOgLocale() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "fb:app_id", post.getFbAppId() ) );
        appendIfNotBlank( metadata, createMetadataTag( FACEBOOK_PROPERTY_NAME, "fb:profile_id", post.getFbProfileId() ) );
        
        metadata.append( "\n<!-- Twitter metadata -->\n" );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:card", post.getTwitterCard(), TwitterSummary.summary ) );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:title", post.getTwitterTitle(), post.getOgTitle(), post.getTitle() ) );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:image", post.getTwitterImage(), post.getOgImage() ) );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:url", post.getTwitterUrl(), post.getOgUrl(), canonicalUrl ) );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:site", post.getTwitterSite(), site.getTwitterHandle() ) );
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:creator", post.getTwitterCreator(), authorTwitter ) );
        
        String twitterDescription = post.getTwitterDescription();
        
        if( StringUtils.isBlank( twitterDescription ) ){
            twitterDescription = post.getOgDescription();
        }
        
        if( StringUtils.isBlank( twitterDescription ) ){
            twitterDescription = post.getPullQuote();
        }
        
        if( !StringUtils.isBlank( twitterDescription ) ){
            twitterDescription = twitterDescription.substring( 0, Math.min( 200, twitterDescription.length() ) );
        }
        
        appendIfNotBlank( metadata, createMetadataTag( TWITTER_PROPERTY_NAME, "twitter:description", twitterDescription ) );
        
        metadata.append( "\n<!-- Google / G+ metadata -->\n" );
        appendIfNotBlank( metadata, createMetadataTag( GOOGLE_PROPERTY_NAME, "name", post.getGoogleName(), post.getOgTitle(), post.getTwitterTitle(), post.getTitle() ) );
        appendIfNotBlank( metadata, createMetadataTag( GOOGLE_PROPERTY_NAME, "description", post.getGoogleDescription(), twitterDescription, post.getOgDescription() ) );
        appendIfNotBlank( metadata, createMetadataTag( GOOGLE_PROPERTY_NAME, "image", post.getGoogleImage(), post.getOgImage(), post.getTwitterImage() ) );
        
        int index = html.indexOf( SOCIAL_MEDIA_METADATA_PLACEHOLDER );
        if(index != -1 ){
            html = html.replace( SOCIAL_MEDIA_METADATA_PLACEHOLDER, metadata.toString() );
        }
        return html;
    }
    
    private static void appendIfNotBlank( StringBuilder builder, String text ){
        if( !StringUtils.isBlank( text ) ){
            builder.append( text );
        }
    }
    
    private static String createMetadataTag( String propertyKey, String property, String content, String ... alternatives ){
        if( StringUtils.isBlank( content ) ){
            for( String alternative : alternatives ){
                if( !StringUtils.isBlank( alternative ) ){
                    return createMetadataTag( propertyKey, property, alternative );
                }
            }
        }
        return createMetadataTag( propertyKey, property, content );
    }
    
    private static String createMetadataTag( String propertyKey, String property, String content ){
        if( StringUtils.isBlank( content ) ){
            return null;
        }
        content = stripHtmlTags( content );
        return "<meta " + propertyKey  + "=\"" + property + "\" content=\"" + content + "\" />\n";
    }
    
    private static String stripHtmlTags( String content ){
        StringBuilder result = new StringBuilder( content.length() );
        
        boolean insideTag = false;
        for( char c : content.toCharArray() ){
            if( insideTag ){
                if( c == '>' ) {
                    insideTag = false;
                }
            }
            else if( c == '<' ){
                insideTag = true;
            }
            else if( c == '\n' ){
                result.append(' ');
            }
            else{
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    private String markdownToHtmlSnippet( String body )
        throws IOException {
        Options options = new Options();
        options.setExecutable( new File( config.getPandoc() ) );
        options.setFrom( Format.markdown );
        options.setTo( Format.html );

        Pandoc pandoc = new Pandoc( new File( config.getPandoc() ) );
        return pandoc.render( options, body );
    }

    private List<Post> paginate( Post post ) {
        List<Post> result = new ArrayList();

        String body = post.getHtml();
        String[] tokens = body.split( PAGINATE );
        if( tokens.length == 1 ) {
            result.add( post );
        }
        else {
            for( String token : tokens ) {
                if( !token.trim().isEmpty() ) {
                    Post page = post.clone();
                    
                    page.setSource( null );
                    page.setHtml( token );
                    
                    result.add( page );
                }
            }
        }

        return result;
    }

    /// static ///
    @Override
    public void copyStatic() {
        if( staticDir.exists() ) {
            try {
                logger.info( "Copying static content" );
                FileUtils.copyDirectoryToDirectory( staticDir, siteDir, true, filter, verbose );
            }
            catch( Throwable t ) {
                logger.error( "Error copying static content", t );
            }
        }
        
        if( uploadsDir.exists() ) {
            try {
                logger.info( "Copying uploads" );
                FileUtils.copyDirectoryToDirectory( uploadsDir, siteDir, true, filter, verbose );
            }
            catch( Throwable t ) {
                logger.error( "Error copying uploads", t );
            }
        }
    }

    /// index ///
    @Override
    public void renderIndex() {
        try {
            File index = new File( siteDir, INDEX_HTML );
            if( indexPost != null ) {
                String link = indexPost.getLink();
                File dir = new File( siteDir, link );
                File postIndex = new File( dir, INDEX_HTML );
                if( postIndex.exists() ) {
                    FileUtils.copyFile( postIndex, index );
                    return;
                }
            }

            String history = site.getHistory();
            if( StringUtils.isBlank( history ) ) {
                history = SiteMetadata.DEFAULT_HISTORY;
            }

            File historyIndex = new File( siteDir, history + "/1/index.html" );
            if( historyIndex.exists() ) {
                FileUtils.copyFile( historyIndex, index );
                return;
            }

            Post post = new Post();
            post.setTitle( "Welcome" );
            post.setSource( "Welcome" );
            post.setLink( "/" );
            Template template = templateLoader.loadTemplate( post.getLayout() );
            post.setHtml( toHtml( post ) );
            String body = render( template, site, post, null, null, null, null, null, site.getWebAddress() );

            if( writeToDisk ){
                FileUtils.write( index, body );
            }
        }
        catch( Throwable t ) {
            logger.error( "Error rendering index", t );
        }
    }
    
    @Override
    public void renderChecksums(){
        logger.info( "Rendering checksums" );
        
        File rootFile = siteDir.getAbsoluteFile();
        Checksum rootNode = processChecksums( rootFile, "/", null, isVerbose() );
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson( rootNode );
        
        try {
            File feedDir = siteDir;
            String dir = site.getFeed();
            if( !StringUtils.isBlank( dir ) ) {
                feedDir = new File( feedDir, dir );
            }
            feedDir.mkdirs();
            
            File rssFile = new File( feedDir, "checksums.json" );
            FileUtils.writeStringToFile( rssFile, json );
        }
        catch( Throwable t ) {
            logger.error( "Error writing RSS Feed", t );
        }
    }
    
    public static Checksum processChecksums( File file, String name, Checksum parent, boolean verbose ){
        if( verbose){
            logger.info( "Generating checksum: " + file.getAbsolutePath() + " name: " + name );
        }
        
        Checksum checksum = new Checksum();
        if( parent != null ){
            parent.getChildren().add( checksum );
        }
        
        if( StringUtils.isBlank( name ) ){
            checksum.setName( file.getName() );
        }
        else {
            checksum.setName( name );
        }
        
        if( file.isDirectory() ){
            for( File child : file.listFiles() ){
                processChecksums( child, null, checksum, verbose );
            }
        }
        else{
            try {
                long cs = checksumCRC32( file );
                checksum.setChecksum( cs );
            }
            catch( IOException ioe ){
                logger.error( "Error generating checksum", ioe );
                checksum.setChecksum( -1 );
            }
        }
        
        return checksum;
    }

    /// getters ///
    @Override
    public File getPostsDir() {
        return publishedDir;
    }

    @Override
    public File getDraftsDir() {
        return draftsDir;
    }

    @Override
    public File getPrivateDir() {
        return privateDir;
    }

    @Override
    public File getLayoutsDir() {
        return layoutsDir;
    }

    @Override
    public File getIncludesDir() {
        return includesDir;
    }

    @Override
    public File getDataDir() {
        return dataDir;
    }

    @Override
    public File getStaticDir() {
        return staticDir;
    }

    @Override
    public File getUploadsDir(){
        return uploadsDir;
    }
    
    @Override
    public File getSiteDir() {
        return siteDir;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public SiteMetadata getSite(){
        return site;
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }

    @Override
    public void setVerbose( boolean verbose ) {
        this.verbose = verbose;
    }
}
