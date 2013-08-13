package com.bizbump.view.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizbump.R;
import com.bizbump.cache.BitmapDownloader;
import com.bizbump.cache.MemoryCache;
import com.bizbump.model.Card;
import com.bizbump.utils.GravatarUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context, int resource, Card[] objects) {
        super(context, resource, 0, objects);
    }

    public CardsAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            //If no view then create one!
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.card_details, null);
        }
        //Get card_details at position
        Card card = getItem(position);

        //Get The Views
        TextView email = (TextView) v.findViewById(R.id.email);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView phone_number = (TextView) v.findViewById(R.id.phone_number);
        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        LinearLayout social_container = (LinearLayout) v.findViewById(R.id.socials);

        Bitmap b = MemoryCache.getInstance().get(GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            //TODO Set some loading image
           new BitmapDownloader(thumbnail).execute(card.getEmail());
        }else{
            thumbnail.setImageBitmap(b);
        }

        //Set text
        email.setText(card.getEmail());
        name.setText(card.getFullName());
        phone_number.setText(card.getPhoneNumber());

        //Check for social networks


        return v;
    }

}
