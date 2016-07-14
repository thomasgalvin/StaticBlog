package blog;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class IncludesTest
{
    File dir = new File( "target/includes/" );
    String keyOne = "foo.html";
    String valueOne = "<h1>This is a header</h1>";
    
    String keyTwo = "data/table.html";
    String valueTwo = "TODO: insert data here";
    
    String keyThree = "xxx/yyy/zzz/test.html";
    String valueThree = "Here's some more HTML!";
    
    @Test
    public void testLoadIncludes() throws Exception{
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        System.out.println( "***" );
        write();
        read();
    }
    
    private void write() throws Exception{
        dir.mkdirs();
        
        File file = new File( dir, keyOne );
        FileUtils.write( file, valueOne );
        
        file = new File( dir, keyTwo );
        FileUtils.write( file, valueTwo );
        
        file = new File( dir, keyThree );
        FileUtils.write( file, valueThree );
    }
    
    private void read() throws Exception{
        Includes includes = Includes.load( dir );
        //includes.printAll();
        
        Assert.assertEquals( "Bad value loaded", valueOne, includes.get( keyOne ) );
        Assert.assertEquals( "Bad value loaded", valueTwo, includes.get( keyTwo ) );
        Assert.assertEquals( "Bad value loaded", valueThree, includes.get( keyThree ) );
        Assert.assertEquals( "Bad value loaded", "", includes.get( "sdahdjashd" ) );
    }
}
