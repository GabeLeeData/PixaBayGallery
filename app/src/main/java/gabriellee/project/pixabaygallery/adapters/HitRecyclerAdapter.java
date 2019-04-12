package gabriellee.project.pixabaygallery.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gabriellee.project.pixabaygallery.R;
import gabriellee.project.pixabaygallery.models.Hit;

import static android.support.constraint.Constraints.TAG;

public class HitRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ListPreloader.PreloadModelProvider<String> {


    private static final int PICTURE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<Hit> mHits;
    private OnPictureListener mOnPictureListener;
    private RequestManager requestManager;
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

    public HitRecyclerAdapter(OnPictureListener mOnPictureListener, RequestManager requestManager, ViewPreloadSizeProvider<String> viewPreloadSizeProvider) {
        this.mOnPictureListener = mOnPictureListener;
        this.requestManager = requestManager;
        this.preloadSizeProvider = viewPreloadSizeProvider;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        Log.d(TAG, "onCreateViewHolder: " + i);
        switch(i) {

            case PICTURE_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_hit_list_item, viewGroup, false);
                return new HitViewHolder(view, mOnPictureListener, requestManager, preloadSizeProvider);
            }

            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_exhausted, viewGroup, false);
                return new ExhaustedViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_hit_list_item, viewGroup, false);
                return new HitViewHolder(view, mOnPictureListener, requestManager, preloadSizeProvider);
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType == PICTURE_TYPE) {
            ((HitViewHolder)viewHolder).onBind(mHits.get(i));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mHits.get(position).getTags().equals("LOADING...")) {
            return LOADING_TYPE;
        }
        else if (mHits.get(position).getTags().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        }
        else if (mHits.get(position).getTags().equals("New")) {
            return PICTURE_TYPE;
        }
        else {
            return PICTURE_TYPE;
        }
    }

    //Display loading during search request.
    public void displayOnlyLoading(){
        clearHitList();
        Hit hit = new Hit();
        hit.setTags("LOADING...");
        mHits.add(hit);
        notifyDataSetChanged();


    }

    public void clearHitList() {
        if(mHits == null) {
            mHits = new ArrayList<>();
        }
        else {
            mHits.clear();
        }
        notifyDataSetChanged();
    }

    public void setQueryExhausted(){
        Log.d(TAG, "setQueryExhausted: QUERY HAS BEEN EXHAUSTED");
        hideLoading();
        Hit exhaustedHit = new Hit();
        exhaustedHit.setTags("EXHAUSTED...");
        mHits.add(exhaustedHit);
        notifyDataSetChanged();
    }

    public void newSearch() {
        Hit newsearch = new Hit();
        newsearch.setTags("New");
        mHits.add(newsearch);
        notifyDataSetChanged();
    }

    public void hideLoading(){
        if(isLoading()) {
            if(mHits.get(0).getTags().equals("LOADING...")) {
                mHits.remove(0);
            }
            else if(mHits.get(mHits.size() -1).equals("LOADING...")) {
                mHits.remove(mHits.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    //Pagination Loading.
    public void displayLoading(){
        if(mHits == null) {
            mHits = new ArrayList<>();
        }

        if(!isLoading()){
            Hit hit = new Hit();
            hit.setTags("LOADING...");
            mHits.add(hit);
            notifyDataSetChanged();
        }
    }



    private boolean isLoading(){
        if (mHits != null) {
            if(mHits.size() > 0) {
                if(mHits.get(mHits.size() - 1).getTags().equals("LOADING...")){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        if(mHits != null) {
            return mHits.size();
        }
        return 0;
    }

    public void setHits(List<Hit> hits) {
        mHits = hits;
        notifyDataSetChanged();
    }

    public Hit getSelectedPicture(int position) {
        if(mHits != null) {
            if(mHits.size() > 0) {
                return mHits.get(position);
            }
        }
        return null;
    }


    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url = mHits.get(position).getWebformatURL();
        if(TextUtils.isEmpty(url)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(url);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return requestManager.load(item);
    }
}
