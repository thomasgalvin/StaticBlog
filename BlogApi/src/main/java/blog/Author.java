package blog;

import lombok.Data;

@Data
public class Author
{
    private String id;
    private String displayName;
    private String fullName;
    private String sortByName;
    private String bio;
    private String headshot;
    private String thumbnail;
    private String twitterHandle; //eg @my_handle
}
