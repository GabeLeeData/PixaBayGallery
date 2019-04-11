package gabriellee.project.pixabaygallery.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "pictures")
public class Hit implements Parcelable {


    @PrimaryKey
    @NonNull
    private int user_id;

    @ColumnInfo(name = "user")
    private String user;

    @ColumnInfo(name = "tags")
    private String tags;

    @ColumnInfo(name = "userImageURL")
    private String userImageURL;

    @ColumnInfo(name = "webformatURL")
    private String webformatURL;

    @ColumnInfo(name = "views")
    private int views;

    @ColumnInfo(name = "timestamp")
    private int timestamp;

    public Hit(int user_id, String user, String tags, String userImageURL, String webformatURL, int views, int timestamp) {
        this.user_id = user_id;
        this.user = user;
        this.tags = tags;
        this.userImageURL = userImageURL;
        this.webformatURL = webformatURL;
        this.views = views;
        this.timestamp = timestamp;
    }

    public Hit() {

    }

    protected Hit(Parcel in) {
        user = in.readString();
        tags = in.readString();
        userImageURL = in.readString();
        webformatURL = in.readString();
        views = in.readInt();
        timestamp = in.readInt();
        user_id = in.readInt();
    }

    public static final Creator<Hit> CREATOR = new Creator<Hit>() {
        @Override
        public Hit createFromParcel(Parcel in) {
            return new Hit(in);
        }

        @Override
        public Hit[] newArray(int size) {
            return new Hit[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(tags);
        dest.writeString(userImageURL);
        dest.writeString(webformatURL);
        dest.writeInt(views);
        dest.writeInt(timestamp);
        dest.writeInt(user_id);
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "user_id=" + user_id +
                ", user='" + user + '\'' +
                ", tags='" + tags + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                ", webformatURL='" + webformatURL + '\'' +
                ", views=" + views +
                ", timestamp=" + timestamp +
                '}';
    }
}
