package gabriellee.project.pixabaygallery.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import gabriellee.project.pixabaygallery.R;

public class HitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView user, views, tags;
    AppCompatImageView image;
    CircleImageView userImage;
    OnPictureListener onPictureListener;

    public HitViewHolder(@NonNull View itemView, OnPictureListener onPictureListener) {
        super(itemView);

        this.onPictureListener = onPictureListener;

        user = itemView.findViewById(R.id.hit_user);
        views = itemView.findViewById(R.id.hit_views);
        tags = itemView.findViewById(R.id.hit_tags);
        image = itemView.findViewById(R.id.hit_image);
        userImage = itemView.findViewById(R.id.hit_userImage);
    }


    @Override
    public void onClick(View v) {
        onPictureListener.onPictureClick(getAdapterPosition());
    }

}
