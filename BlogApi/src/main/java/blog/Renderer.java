package blog;

import java.io.File;
import java.util.List;

public interface Renderer
{
    
    Config getConfig();
        
    void copyStatic();

    File getDataDir();

    File getPostsDir();
    
    File getDraftsDir();
    
    File getPrivateDir();

    File getIncludesDir();

    File getLayoutsDir();

    File getSiteDir();
    
    File getStaticDir();
    
    File getUploadsDir();

    void loadPosts();

    void loadTemplates();

    void render();
    
    void renderSourceFiles( List<File> files );

    void renderHistory();

    void renderIndex();

    void renderPosts();
    
    void renderRss();

    void renderTags();
    
    void renderChecksums();
    
    public void setVerbose( boolean verbose );
    
    public boolean isVerbose();
}
