package gabriellee.project.pixabaygallery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

import gabriellee.project.pixabaygallery.adapters.HitRecyclerAdapter;
import gabriellee.project.pixabaygallery.adapters.OnPictureListener;
import gabriellee.project.pixabaygallery.models.Hit;
import gabriellee.project.pixabaygallery.requests.Resource;
import gabriellee.project.pixabaygallery.util.BaseActivity;
import gabriellee.project.pixabaygallery.util.Testing;
import gabriellee.project.pixabaygallery.util.VerticalSpacingItemDecorator;
import gabriellee.project.pixabaygallery.viewmodels.PictureListViewModel;

import static gabriellee.project.pixabaygallery.viewmodels.PictureListViewModel.QUERY_EXHAUSTED;

public class PictureListActivity extends BaseActivity implements OnPictureListener {

    private static final String TAG = "PictureListActivity";
    private PictureListViewModel mPictureListViewModel;
    private RecyclerView mRecyclerView;
    private HitRecyclerAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        mRecyclerView = findViewById(R.id.picture_list);
        mSearchView = findViewById(R.id.search_view);
        mPictureListViewModel = ViewModelProviders.of(this).get(PictureListViewModel.class);

        initRecyclerView();
        initSearchView();
        subscribeObservers();
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        searchPicturesApi("food");

    }

    private void subscribeObservers() {
        mPictureListViewModel.getPictures().observe(this, new Observer<Resource<List<Hit>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Hit>> listResource) {
                if(listResource != null) {
                    Log.d(TAG, "onChanged: status: " + listResource.status);

                    if(listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING: {
                                if(mPictureListViewModel.getPageNumber() > 1) {
                                    mAdapter.displayLoading();
                                }
                                else {
                                    mAdapter.displayOnlyLoading();
                                }
                                break;
                            }

                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh the cahce." + listResource.message);
                                Log.e(TAG, "onChanged: Error message: " + listResource.message );
                                Log.e(TAG, "onChanged: status: ERROR, #pPictures: " +listResource.data.size());
                                mAdapter.hideLoading();
                                mAdapter.setHits(listResource.data);
                               // Toast.makeText(PictureListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                if(listResource.message.equals(QUERY_EXHAUSTED)) {
                                    mAdapter.setQueryExhausted();
                                }
                                break;
                            }

                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, Pictures: " + listResource.data.size());
                                mAdapter.hideLoading();
                                mAdapter.setHits(listResource.data);
                                break;
                            }
                        }
                    }

                }
            }
        });
    }

    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }



    private void searchPicturesApi(String query){
        mRecyclerView.smoothScrollToPosition(0);
        mPictureListViewModel.searchPicturesApi(query, 1);
    }

    private void initRecyclerView() {
        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();
        mAdapter = new HitRecyclerAdapter(this, initGlide(), viewPreloader);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(40);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<String>(Glide.with(this), mAdapter, viewPreloader, 20);

        mRecyclerView.addOnScrollListener(preloader);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!mRecyclerView.canScrollVertically(1)) {
                    mPictureListViewModel.searchNextPage();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e(TAG, "onQueryTextSubmit: " + s );

                searchPicturesApi(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }

    @Override
    public void onPictureClick(int position) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("picture", mAdapter.getSelectedPicture(position));
        startActivity(intent);
    }

}
