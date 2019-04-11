package gabriellee.project.pixabaygallery.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import gabriellee.project.pixabaygallery.models.Hit;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PictureDao {

    //Passing in a list of pictures
    @Insert(onConflict = IGNORE)
    long[] insertPictures(Hit...hits);

    //Updating a single picture with replace
    @Insert(onConflict = REPLACE)
    void insertPicture(Hit hit);

    //Querying (Updating)
    @Query("UPDATE pictures SET user = :user, tags = :tags, userImageURL = :userImageURL, webformatURL = :webformatURL, views = :views " +
            "WHERE user_id = :user_id")
    void updatePicture(int user_id, String user, String tags, String userImageURL, String webformatURL, int views);

    @Query("SELECT * FROM pictures WHERE tags LIKE '%' || :query || '%' " + "ORDER BY views DESC LIMIT (:pageNumber * 20)")
    LiveData<List<Hit>> searchPictures(String query, int pageNumber);


}
