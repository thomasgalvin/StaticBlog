package blog;

import java.io.File;

public enum SourceFormat
{
    Markdown,
    Html;
    
    public static SourceFormat get( File file ){
        String fileName = file.getName();
        if( fileName.endsWith( ".html" ) ||
            fileName.endsWith( ".htm" ) ){
            return Html;
        }
        
        return Markdown;
    }
}
