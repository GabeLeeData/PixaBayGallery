package gabriellee.project.pixabaygallery.requests;

import gabriellee.project.pixabaygallery.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PixabayApi pixabayApi = retrofit.create(PixabayApi.class);

    public static PixabayApi getPixabayApi(){
        return pixabayApi;
    }

}
