package blog;

/**
 * Represents the various Facebook Open Graph og:type values.
 * For complete documentation, see:
 * <https://developers.facebook.com/docs/reference/opengraph#object-type>
 */
public class OGType
{
    //This object represents an article on a website. It is the preferred type 
    //for blog posts and news stories.
    public static final String article = "article";
    
    //A website
    public static final String website = "website";
    
    //This object type represents a single author of a book
    public static final String book_author = "books.author";
    
    //This object type represents a book or publication
    public static final String book_book = "books.book";
    
    //This object type represents the genre of a book or publication.
    public static final String book_genre = "books.genre";
    
    //This object type represents a place of business that has a location, operating hours and contact information.
    public static final String business_business = "business.business";
    
    //This object type represents the user's activity contributing to a particular run, walk, or bike course.
    public static final String fitness_course = "fitness.course";
    
    //This object type represents a specific achievement in a game.
    public static final String game_achievement = "game.achievement";
    
    //This object type represents a music album;
    public static final String music_album = "music.album";
    
    //This object type represents a music playlist, an ordered collection of songs from a collection of artists.
    public static final String music_playlist = "music.playlist";
    
    //This object type represents a 'radio' station of a stream of audio
    public static final String music_radio_station = "music.radio_station";
    
    //This object type represents a single song.
    public static final String music_song = "music.song";
    
    //This object type represents a place - such as a venue, a business, a landmark, or any other location which can be identified by longitude and latitude.
    public static final String place = "place";
    
    //This object type represents a product. This includes both virtual and physical products, but it typically represents items that are available in an online store.
    public static final String product = "product";
    
    //This object type represents a group of product items.
    public static final String product_group = "product.group";
    
    //This object type represents a product item.
    public static final String product_item = "product.item";
    
    //This object type represents a person. While appropriate for celebrities, artists, or musicians, this object type can be used for the profile of any individual. The `fb:profile_id` field associates the object with a Facebook user.
    public static final String profile = "profile";
    
    //This object type represents a restaurant's menu.
    public static final String restaurant_menu = "restaurant.menu";
    
    //This object type represents a single item on a restaurant's menu.
    public static final String restaurant_menu_item = "restaurant.menu_item";
    
    //This object type represents a section in a restaurant's menu. 
    public static final String restaurant_menu_section = "restaurant.menu_section";
    
    //This object type represents a restaurant at a specific location.
    public static final String restaurant_restaurant = "restaurant.restaurant";
    
    //This object type represents an episode of a TV show and contains references to the actors and other professionals involved in its production.
    public static final String video_episode = "video.episode";
    
    //This object type represents a movie, and contains references to the actors and other professionals involved in its production. 
    public static final String video_movie = "video.movie";
    
    //This object type represents a generic video, and contains references to the actors and other professionals involved in its production.
    public static final String video_other = "video.other";
    
    //This object type represents a TV show, and contains references to the actors and other professionals involved in its production.
    public static final String video_tv_show = "video.tv_show";
}
