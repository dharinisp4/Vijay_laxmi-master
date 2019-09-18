package Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by fujitsu on 3/28/2017.
 */
class FontCacheRagular {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    @Nullable
    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }
        return typeface;
    }

}
