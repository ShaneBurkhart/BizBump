package com.bizbump.storage.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bizbump.R;
import com.bizbump.storage.cache.MemoryCache;
import com.bizbump.utils.ConnectionUtils;
import com.bizbump.utils.GravatarUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class BitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    private ImageView thumbnail;
    private String url;
    private Handler handler;

    public BitmapDownloader(ImageView thumbnail) {
        this.thumbnail = thumbnail;
        this.handler = new Handler();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if(strings == null || strings.length < 1)
            return null;
        this.url = GravatarUtils.getGravatarURL(strings[0]);
        try{
            Log.d("Gravatar URL", this.url);
            URL u = new URL(this.url);
            URLConnection urlConnection = u.openConnection();
            InputStream input = urlConnection.getInputStream();
            Bitmap b = BitmapFactory.decodeStream(input);
            input.close();
            return b;
        } catch (IOException e){
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(!isCancelled()){
            Bitmap image = bitmap;
            if(image != null){
                MemoryCache.getInstance().add(this.url, image);
            } else {
                //TODO Some default image
            }
            this.handler.post(new BitmapChangerRunnable(bitmap, this.thumbnail));
        }
    }

    private class BitmapChangerRunnable implements Runnable{
        Bitmap bit;
        ImageView thumb;

        public BitmapChangerRunnable(Bitmap bit, ImageView thumb){
            this.thumb = thumb;
            this.bit = bit;
        }

        @Override
        public void run() {
            LinearLayout parent = (LinearLayout) thumbnail.getParent();
            parent.findViewById(R.id.thumbnail_progress).setVisibility(View.GONE);
            thumbnail.setVisibility(View.VISIBLE);
            this.thumb.setImageBitmap(this.bit);
        }
    }
}
