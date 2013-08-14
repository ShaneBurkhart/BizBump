package com.bizbump.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizbump.R;
import com.bizbump.storage.cache.BitmapDownloader;
import com.bizbump.storage.cache.MemoryCache;
import com.bizbump.model.Card;
import com.bizbump.utils.CardUtils;
import com.bizbump.utils.GravatarUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class CardsAdapter extends BaseAdapter {

    public static final int SEPERATOR = 0;
    public static final int CARD = 1;
    private static final int COUNT = CARD + 1;

    Context context;
    ArrayList<Object> objects;

    public CardsAdapter(Context context, ArrayList<Object> objects) {
        super();
        this.context = context;
        this.objects = objects;
        CardUtils.addSeperators(objects);
    }

    public void addCard(Card card){
        CardUtils.removeSeperators(this.objects);
        this.objects.add(card);
        CardUtils.addSeperators(this.objects);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean isEnabled(int position) {
        if(getItemViewType(position) == CARD)
            return true;
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if(objects.get(position) instanceof Card)
            return CARD;
        else
            return SEPERATOR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        //Get type
        int type = getItemViewType(position);

        if(v == null){
            //If no view then create one!
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            //Check type
            switch (type){
                case SEPERATOR:
                    v = vi.inflate(R.layout.letter_seperator, null);
                    break;
                case CARD:
                    v = vi.inflate(R.layout.card_item, null);
                    break;
            }
        }

        switch (type){
            case SEPERATOR:
                String letter = (String) getItem(position);
                ((TextView) v).setText(letter);
                break;
            case CARD:
                //Get card_details at position
                Card card = (Card) getItem(position);
                setCard(card, v);
                break;
        }

        return v;
    }

    private void setCard(Card card, View v){
        //Get The Views
        TextView email = (TextView) v.findViewById(R.id.email);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView phone_number = (TextView) v.findViewById(R.id.phone_number);
        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        LinearLayout social_container = (LinearLayout) v.findViewById(R.id.socials);

        Bitmap b = MemoryCache.getInstance().get(GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            new BitmapDownloader(thumbnail).execute(card.getEmail());
        }else{
            LinearLayout parent = (LinearLayout) thumbnail.getParent();
            parent.findViewById(R.id.thumbnail_progress).setVisibility(View.GONE);
            thumbnail.setVisibility(View.VISIBLE);
            thumbnail.setImageBitmap(b);
        }

        //Set text
        email.setText(card.getEmail());
        name.setText(card.getFullName());
        phone_number.setText(card.getPhoneNumber());

        //Check for social networks
    }
}
