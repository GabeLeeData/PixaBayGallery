package gabriellee.project.pixabaygallery.requests;

import gabriellee.project.pixabaygallery.requests.responses.PictureResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayApi {

    // SEARCH Image
    @GET("api/")
    Call<PictureResponse> searchPicture(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

}