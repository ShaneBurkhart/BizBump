package com.bizbump.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Shane on 8/12/13.
 */
public class FontUtils {

    public static Typeface getIconFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "icon-font.ttf");
    }
}
