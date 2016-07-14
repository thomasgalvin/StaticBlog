package blog;

import galvin.StringUtils;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkGenerator {
    private static final Logger logger = LoggerFactory.getLogger( LinkGenerator.class );
    
    public static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789-._~#[]@!$*+";
    private Set<String> usedLinks = new HashSet();
    
    public void clear(){
        usedLinks.clear();
    }
    
    public void ensureLink( File file, Post post ) {
        if( StringUtils.isBlank( post.getLink() ) ) {
            String title = post.getTitle();
            
            String link = toLink( title );
            link = incrementLink( link );
            
            post.setLink( link );
        }
    }
    
    public static String toLink( String title ) {
        if( StringUtils.isBlank( title ) ){
            return "";
        }
        
        title = title.toLowerCase();

        boolean whitespace = false;
        StringBuilder result = new StringBuilder( title.length() );
        for( char c : title.toCharArray() ) {
            if( c == ' ' ) {
                if( !whitespace ) {
                    result.append( "-" );
                    whitespace = true;
                }
            }
            else {
                String charString = "" + c;
                if( ALLOWED_CHARACTERS.contains( charString ) ) {
                    result.append( charString );
                    whitespace = false;
                }
            }
        }

        return result.toString();
    }

    public String incrementLink( String linkFragment ){
        String result = linkFragment;
        int increment = 2;
        while( usedLinks.contains( result ) ){
            result = linkFragment + "_" + increment;
            increment++;
        }
        return result;
    }
}
