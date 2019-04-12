package gabriellee.project.pixabaygallery.requests;

import java.util.concurrent.TimeUnit;

import gabriellee.project.pixabaygallery.util.Constants;
import gabriellee.project.pixabaygallery.util.LiveDataCallAdapter;
import gabriellee.project.pixabaygallery.util.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static gabriellee.project.pixabaygallery.util.Constants.CONNECTION_TIMEOUT;
import static gabriellee.project.pixabaygallery.util.Constants.READ_TIMEOUT;
import static gabriellee.project.pixabaygallery.util.Constants.WRITE_TIMEOUT;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient.Builder()

            //Establish connection to server
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

            //Time between each byte read from the server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

            //Time between each byte sent to server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)


            .retryOnConnectionFailure(false).build();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PixabayApi pixabayApi = retrofit.create(PixabayApi.class);

    public static PixabayApi getPixabayApi(){
        return pixabayApi;
    }

}
