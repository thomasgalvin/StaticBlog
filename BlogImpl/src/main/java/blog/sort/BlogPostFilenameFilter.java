package blog.sort;

import java.io.File;
import java.io.FilenameFilter;

public class BlogPostFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept( File dir, String name ) {
        return name.endsWith( ".md" ) ||
               name.endsWith( ".html" ) ||
               name.endsWith( ".htm" );
    }

}
