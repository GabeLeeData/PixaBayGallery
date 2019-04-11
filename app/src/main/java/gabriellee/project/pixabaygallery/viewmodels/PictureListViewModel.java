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
        if(!isPerformingQuery) {
            if(pageNumber==0) {
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            this.query = query;
            isQueryExhausted = false;
            executeSearch();
        }


    }

    public void executeSearch(){
        isPerformingQuery = true;
        final LiveData<Resource<List<Hit>>> repositorySource = pictureRepository.searchPicturesApi(query, pageNumber);

        pictures.addSource(repositorySource, new Observer<Resource<List<Hit>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Hit>> listResource) {
                if(listResource != null){
                    pictures.setValue(listResource);
                    if(listResource.status == Resource.Status.SUCCESS ){
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
                        isPerformingQuery = false;
                        pictures.removeSource(repositorySource);
                    }
                }
                else {
                    pictures.removeSource(repositorySource);
                }
            }
        });
    }




}
