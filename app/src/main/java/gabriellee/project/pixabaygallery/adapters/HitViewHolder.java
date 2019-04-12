package gabriellee.project.pixabaygallery.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import de.hdodenhof.circleimageview.CircleImageView;
import gabriellee.project.pixabaygallery.R;
import gabriellee.project.pixabaygallery.models.Hit;

public class HitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView user, views, tags;
    AppCompatImageView image;
    CircleImageView userImage;
    OnPictureListener onPictureListener;
    RequestManager requestManager;
    ViewPreloadSizeProvider viewPreloadSizeProvider;

    public HitViewHolder(@NonNull View itemView, OnPictureListener onPictureListener, RequestManager requestManager, ViewPreloadSizeProvider preloadSizeProvider) {
        super(itemView);

        this.onPictureListener = onPictureListener;
        this.requestManager = requestManager;
        this.viewPreloadSizeProvider = preloadSizeProvider;
        user = itemView.findViewById(R.id.hit_user);
        views = itemView.findViewById(R.id.hit_views);
        tags = itemView.findViewById(R.id.hit_tags);
        image = itemView.findViewById(R.id.hit_image);
        userImage = itemView.findViewById(R.id.hit_userImage);

        itemView.setOnClickListener(this);
    }

    public void onBind(Hit hit) {


        requestManager
                .load(hit.getWebformatURL())
                .into(image);

        requestManager
                .load(hit.getUserImageURL())
                .into(userImage);

        user.setText(hit.getUser());
        views.setText(String.valueOf(hit.getViews()));
        tags.setText(hit.getTags());

        viewPreloadSizeProvider.setView(image);

    }

    @Override
    public void onClick(View v) {
        onPictureListener.onPictureClick(getAdapterPosition());
    }

}
