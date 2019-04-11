package gabriellee.project.pixabaygallery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

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
                                    mAdapter.displayLoading();
                                }
                                break;
                            }

                            case ERROR: {
                                Log.e(TAG, "onChanged: cannot refresh the cahce." + listResource.message);
                                Log.e(TAG, "onChanged: Error message: " + listResource.message );
                                Log.e(TAG, "onChanged: status: ERROR, #recipes: " +listResource.data.size());
                                mAdapter.hideLoading();
                                mAdapter.setHits(listResource.data);
                                Toast.makeText(PictureListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                if(listResource.message.equals(QUERY_EXHAUSTED)) {
                                    mAdapter.setQueryExhausted();
                                }
                                break;
                            }

                            case SUCCESS: {
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, #Recipes: " + listResource.data.size());
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

    private void searchPicturesApi(String query){
        mPictureListViewModel.searchPicturesApi(query, 1);
    }

    private void initRecyclerView() {
        mAdapter = new HitRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(40);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
