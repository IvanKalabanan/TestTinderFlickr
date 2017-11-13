package ivan.example.com.domain.repository;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICalls {

    // as we have not auth logic so I used hardcoded url for get list of albums from my test profile with public photo permission

    @GET("services/rest/?" +
            "method=flickr.photosets.getList&" +
            "api_key=089d7706e8ef346a2cac28a5d1bcfbbb&" +
            "user_id=138196690%40N06&" +
            "primary_photo_extras=url_o&" +
            "format=json&" +
            "nojsoncallback=1"
    )
    Call<ResponseBody> getAlbums();

    @GET("services/rest/?" +
            "method=flickr.photosets.getPhotos&" +
            "api_key=089d7706e8ef346a2cac28a5d1bcfbbb&" +
//            "photoset_id=72157666215399579&" +
            "user_id=138196690%40N06&" +
            "extras=url_o%2Cdate_upload&" +
            "format=json&" +
            "nojsoncallback=1")
    Call<ResponseBody> getPhotos(@Query("photoset_id") String albumId);

}
