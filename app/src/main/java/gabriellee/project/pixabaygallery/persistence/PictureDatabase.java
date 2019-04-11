package gabriellee.project.pixabaygallery.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import gabriellee.project.pixabaygallery.models.Hit;

@Database(entities = {Hit.class}, version = 1)
public abstract class PictureDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "pictures_db";

    private static PictureDatabase instance;

    public static PictureDatabase getInstance(final Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PictureDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract PictureDao getPictureDao();
}
