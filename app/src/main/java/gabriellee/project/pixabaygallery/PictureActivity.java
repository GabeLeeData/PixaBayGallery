package gabriellee.project.pixabaygallery;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import gabriellee.project.pixabaygallery.models.Hit;
import gabriellee.project.pixabaygallery.util.BaseActivity;
import gabriellee.project.pixabaygallery.viewmodels.PictureViewModel;

public class PictureActivity extends BaseActivity {

    private static final String TAG = "RecipeActivity";

    //TO BE IMPLEMENTED

    private AppCompatImageView mSingleImage;
    private PictureViewModel mPictureViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        mSingleImage = findViewById(R.id.singleImage);
        mPictureViewModel = ViewModelProviders.of(this).get(PictureViewModel.class);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("recipe")){
            Hit hit = getIntent().getParcelableExtra("picture");
            Log.d(TAG, "getIncomingIntent: " + hit.getUserImageURL());
        }
    }
}
