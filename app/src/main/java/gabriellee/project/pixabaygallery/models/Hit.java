package gabriellee.project.pixabaygallery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Hit implements Parcelable {
    private String user;
    private String tags;
    private String userImageURL;
    private String webformatURL;
    private int views;

    public Hit(String user, String tags, String userImageURL, String webformatURL, int views) {
        this.user = user;
        this.tags = tags;
        this.userImageURL = userImageURL;
        this.webformatURL = webformatURL;
        this.views = views;
    }

    public Hit() {

    }

    protected Hit(Parcel in) {
        user = in.readString();
        tags = in.readString();
        userImageURL = in.readString();
        webformatURL = in.readString();
        views = in.readInt();
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
    }
}
