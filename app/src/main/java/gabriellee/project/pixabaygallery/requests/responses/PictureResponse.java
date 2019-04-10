package gabriellee.project.pixabaygallery.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import gabriellee.project.pixabaygallery.models.Hit;

public class PictureResponse {

    @SerializedName("hits")
    @Expose()
    private List<Hit> hit;

    public List<Hit> getHit() {
        return hit;
    }

    @Override
    public String toString() {
        return "PictureResponse{" +
                "hit=" + hit +
                '}';
    }
}
