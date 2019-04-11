package gabriellee.project.pixabaygallery.requests;

import android.arch.lifecycle.LiveData;

import gabriellee.project.pixabaygallery.requests.responses.ApiResponse;
import gabriellee.project.pixabaygallery.requests.responses.PictureResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayApi {

    // SEARCH Image
    @GET("api/")
    LiveData<ApiResponse<PictureResponse>> searchPicture(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

}