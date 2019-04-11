package gabriellee.project.pixabaygallery.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import gabriellee.project.pixabaygallery.models.Hit;
import gabriellee.project.pixabaygallery.persistence.PictureDao;
import gabriellee.project.pixabaygallery.persistence.PictureDatabase;
import gabriellee.project.pixabaygallery.requests.NetworkBoundResource;
import gabriellee.project.pixabaygallery.requests.Resource;
import gabriellee.project.pixabaygallery.requests.ServiceGenerator;
import gabriellee.project.pixabaygallery.requests.responses.ApiResponse;
import gabriellee.project.pixabaygallery.requests.responses.PictureResponse;
import gabriellee.project.pixabaygallery.util.AppExecutors;
import gabriellee.project.pixabaygallery.util.Constants;

public class PictureRepository {

    private static final String TAG = "PictureRepository";
    private static PictureRepository instance;
    private PictureDao pictureDao;

    public static PictureRepository getInstance(Context context) {
        if(instance == null) {
            instance = new PictureRepository(context);
        }
        return instance;
    }

    private PictureRepository(Context context) {
        pictureDao = PictureDatabase.getInstance(context).getPictureDao();
    }

    public LiveData<Resource<List<Hit>>> searchPicturesApi (final String query, final int pageNumber) {
        return new NetworkBoundResource<List<Hit>, PictureResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull PictureResponse item) {
                if(item.getHit() != null) { //Recipe list will be null if the api key is expired

                    Hit[] hits = new Hit[item.getHit().size()];
                    int index = 0;
                    for (long rowid: pictureDao.insertPictures((Hit[]) (item.getHit().toArray(hits)))) {
                        if (rowid == -1) {
                            Log.d(TAG, "saveCallResult: CONFLICT... This picture is already in the cache");
                            //If the picture already exists... I dont want to set the timestamp
                            //They will be erased

                            pictureDao.updatePicture(
                                    hits[index].getUser_id(),
                                    hits[index].getUser(),
                                    hits[index].getTags(),
                                    hits[index].getUserImageURL(),
                                    hits[index].getWebformatURL(),
                                    hits[index].getViews()
                            );
                        }
                        index++;
                    }


                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Hit> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Hit>> loadFromDb() {
                return pictureDao.searchPictures(query, pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PictureResponse>> createCall() {
                return ServiceGenerator.getPixabayApi()
                        .searchPicture(
                                Constants.API_KEY,
                                query,
                                String.valueOf(pageNumber)
                        );
            }
        }.getAsLiveData();

    }


}
