package roman10reborn.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by milos on 11.5.2015.
 */

/*


This bug of Android OS could be the reason of your issue:

Typeface.createFromAsset leaks asset stream

Where are also a workaround in this bugreport:

I altered HTH's workaround so that the method does not assume the font path or format.
 The full path of the font asset must be submitted as a parameter.
 I also wrapped the call to createFromAsset() in a try-catch block so that
  the get() method will return null if the asset is not found.
 */





public class Typefaces {

    private static final String TAG = "Typefaces";

    public static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }


}
