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

    @SerializedName("error")
    @Expose()
    private String error;

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "PictureResponse{" +
                "hit=" + hit +
                ", error='" + error + '\'' +
                '}';
    }
}
