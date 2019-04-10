package gabriellee.project.pixabaygallery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import gabriellee.project.pixabaygallery.adapters.HitRecyclerAdapter;
import gabriellee.project.pixabaygallery.adapters.OnPictureListener;
import gabriellee.project.pixabaygallery.util.BaseActivity;
import gabriellee.project.pixabaygallery.util.VerticalSpacingItemDecorator;
import gabriellee.project.pixabaygallery.viewmodels.PictureListViewModel;

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

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
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
