package gabriellee.project.pixabaygallery.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import gabriellee.project.pixabaygallery.models.Hit;
import gabriellee.project.pixabaygallery.repositories.PictureRepository;
import gabriellee.project.pixabaygallery.requests.Resource;


public class PictureListViewModel extends AndroidViewModel {

    private static final String TAG = "PictureListViewModel";
    public static final String QUERY_EXHAUSTED = "No more results.";
    private PictureRepository pictureRepository;

    private MediatorLiveData<Resource<List<Hit>>> pictures = new MediatorLiveData<>();

    // QUERY EXTRAS
    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private int pageNumber;
    private String query;
    private long requestStartTime;

    public PictureListViewModel(@NonNull Application application) {
        super(application);
        pictureRepository = PictureRepository.getInstance(application);


    }

    public LiveData<Resource<List<Hit>>> getPictures(){
        return pictures;
    }

    public int getPageNumber() {
        return pageNumber;
    }



    public void searchPicturesApi(String query, int pageNumber) {

        Log.d(TAG, "searchPicturesApi: Searching Test");
        if(pageNumber==0) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        this.query = query;
        isQueryExhausted = false;
        executeSearch();

    }

    public void searchNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            pageNumber++;
            executeSearch();
        }
    }

    public void executeSearch(){
        requestStartTime = System.currentTimeMillis();
        isPerformingQuery = true;
        final LiveData<Resource<List<Hit>>> repositorySource = pictureRepository.searchPicturesApi(query, pageNumber);

        pictures.addSource(repositorySource, new Observer<Resource<List<Hit>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Hit>> listResource) {

                if(listResource != null){
                    pictures.setValue(listResource);
                    if(listResource.status == Resource.Status.SUCCESS ){
                        Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime / 1000 + " seconds."));
                        isPerformingQuery = false;
                        if(listResource.data != null) {
                            if (listResource.data.size() == 0) {
                                Log.d(TAG, "onChanged: query is EXHAUSTED...");
                                pictures.setValue(new Resource<List<Hit>>(
                                        Resource.Status.ERROR,
                                        listResource.data,
                                        QUERY_EXHAUSTED
                                ));
                                isPerformingQuery = true;
                            }
                        }
                        // must remove or it will keep listening to repository
                        pictures.removeSource(repositorySource);
                    }
                    else if(listResource.status == Resource.Status.ERROR ){
                        Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime / 1000 + " seconds."));
                        isPerformingQuery = false;
                        pictures.removeSource(repositorySource);
                    }
                }
                else {
                    Log.d(TAG, "onChanged: data null");
                    pictures.removeSource(repositorySource);
                }
            }
        });
    }
}
