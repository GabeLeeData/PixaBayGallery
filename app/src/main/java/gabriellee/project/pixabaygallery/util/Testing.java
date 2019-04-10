package gabriellee.project.pixabaygallery.util;

import android.util.Log;

import java.util.List;

import gabriellee.project.pixabaygallery.models.Hit;


public class Testing {

    public static void printHits(List<Hit> list, String tag) {
        for(Hit hit:list) {
            Log.d(tag, "printHits: " +hit.getWebformatURL());
        }
    }
}
