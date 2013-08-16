package me.occucard.storage.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import me.occucard.utils.StorageUtils;

/**
 * Created by Shane on 8/16/13.
 */
public class FileCache {

    private static final String DIR = "bitmaps";

    public static void saveBitmap(Context context, String url, Bitmap bit){
        if(!StorageUtils.hasStorage())
            return;
        try{
            FileOutputStream out = new FileOutputStream(new File(getBitmapDir(context), url));
            bit.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
        }catch (IOException e){}
    }

    public static boolean hasBitmap(Context context, String url){
        return new File(getBitmapDir(context), url).exists();
    }

    public static Bitmap getBitmap(Context context, String url){
        if(!hasBitmap(context, url))
            return null;
        Bitmap b = null;
        try{
            FileInputStream fis = new FileInputStream(new File(getBitmapDir(context), url));
            b = BitmapFactory.decodeStream(fis);
            fis.close();
        }catch (IOException e){}
        return b;
    }

    private static File getBitmapDir(Context context){
        File dir = new File(context.getExternalFilesDir(null), DIR);
        if(!dir.exists())
            dir.mkdirs();
        return dir;
    }
}
