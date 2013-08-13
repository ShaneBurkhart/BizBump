package com.bizbump.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizbump.R;
import com.bizbump.model.Card;
import com.bizbump.storage.cache.BitmapDownloader;
import com.bizbump.storage.cache.MemoryCache;
import com.bizbump.utils.CardUtils;
import com.bizbump.utils.GravatarUtils;

import java.util.ArrayList;

/**
 * Created by Shane on 8/5/13.
 */
public class DrawerAdapter extends BaseAdapter {

    Context context;
    String[] objects;
    private int selected = 0;

    public DrawerAdapter(Context context, String[] objects) {
        super();
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Object getItem(int i) {
        return objects[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            //If no view then create one!
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.drawer_item, null);
        }
        TextView tv = (TextView) v;
        tv.setText(objects[position]);
        return v;
    }
}
