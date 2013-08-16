package me.occucard.storage.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import me.occucard.R;
import me.occucard.storage.cache.FileCache;
import me.occucard.storage.cache.MemoryCache;
import me.occucard.utils.GravatarUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView thumbnail;
    private String url;
    private Handler handler;
    private Context context;

    public BitmapDownloaderTask(Context context, ImageView thumbnail) {
        this.thumbnail = thumbnail;
        this.handler = new Handler();
        this.context = context;
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
            return makeBitmapCircle(b);
        } catch (IOException e){
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    private Bitmap makeBitmapCircle(Bitmap b){
        Bitmap circleBitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader (b, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(b.getWidth()/2, b.getHeight()/2, b.getWidth()/2, paint);
        b.recycle();
        return  circleBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(!isCancelled()){
            Bitmap image = bitmap;
            if(image != null){
                MemoryCache.getInstance().add(this.url, image);
                FileCache.saveBitmap(context, url, bitmap);
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
