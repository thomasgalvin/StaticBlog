package blog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Includes
{
    private HashMap<String, String> data = new HashMap();
    
    public void register( String key, String value ){
        data.put( key, value );
    }
    
    public void clear( String key ){
        data.remove( key );
    }
    
    public String get( String key ){
        String result = data.get( key );
        return StringUtils.isBlank( result ) ? "" : result;
    }
    
    public void printAll(){
       Set<String> set = data.keySet();
       List<String> keys = new ArrayList();
       keys.addAll( set );
       Collections.sort( keys );
       
       for( String key : keys ){
           System.out.println( "Key: " + key );
           System.out.println(get( key ) );
           System.out.println( "" );
       }
    }
    
    public static Includes load( File dir ) throws IOException {
        if( dir.exists() && dir.canRead() && dir.isDirectory() ){
            Includes result = new Includes();
            load( dir, result, "" );
            return result;
        }
        
        return null;
    }
    
    private static void load( File dir, Includes includes, String currentKey ) throws IOException {
        File[] files = dir.listFiles();
        if( files != null ) {
            for( File file : files ) {
                if( file.exists() && file.canRead() ) {
                    String fileName = file.getName();
                    String key = StringUtils.isBlank( currentKey ) ? fileName : currentKey + "/" + fileName;
                    
                    if( file.isDirectory() ) {
                        load( file, includes, key );
                    }
                    else {
                        String value = FileUtils.readFileToString( file );
                        includes.register( key, value );
                    }
                }
            }
        }
    }
}
