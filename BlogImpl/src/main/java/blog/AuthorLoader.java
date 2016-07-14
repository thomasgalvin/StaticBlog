package blog;

import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorLoader {
    private static final Logger logger = LoggerFactory.getLogger( AuthorLoader.class );
    public static final String AUTHORS = "authors.yml";
    private List<Author> authors = new ArrayList();

    public AuthorLoader( File dataDir ) {
        loadAuthors( dataDir );
    }

    private void loadAuthors( File dataDir ) {
        try {
            authors.clear();
            if( dataDir.exists() ) {
                File authorsFile = new File( dataDir, AUTHORS );
                if( authorsFile.exists() ) {
                    YamlReader reader = new YamlReader( new FileReader( authorsFile ) );
                    while( true ) {
                        Author author = reader.read( Author.class );
                        if( author == null ) {
                            break;
                        }
                        else {
                            String id = author.getId();
                            if( StringUtils.isBlank( id ) ){
                                author.setId( author.getDisplayName() );
                            }
                            
                            authors.add( author );
                        }
                    }
                }
            }
        }
        catch( Throwable t ) {
            logger.error( "Error loading authors", t );
        }
    }

    public Author get( String id ){
        for( Author author: authors ){
            if( id.equals( author.getId() ) ){
                return author;
            }
        }
        
        for( Author author: authors ){
            if( id.equals( author.getDisplayName() ) ){
                return author;
            }
        }
        
        for( Author author: authors ){
            if( id.equals( author.getSortByName() ) ){
                return author;
            }
        }
        
        for( Author author: authors ){
            if( id.equals( author.getFullName() ) ){
                return author;
            }
        }
        
        return null;
    }
}
