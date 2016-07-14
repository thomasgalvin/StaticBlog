package blog;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Checksum
{
    private String name;
    private long checksum;
    private List<Checksum> children = new ArrayList();
    
    public Checksum(){}

    public Checksum( String name, long checksum ) {
        this.name = name;
        this.checksum = checksum;
    }
    
    @Override
    public String toString(){
        return name + ": " + checksum;
    }
}
