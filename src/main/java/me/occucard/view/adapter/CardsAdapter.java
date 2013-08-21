package me.occucard.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.occucard.R;
import me.occucard.model.Card;
import me.occucard.storage.async.BitmapDownloaderTask;
import me.occucard.storage.cache.MemoryCache;
import me.occucard.utils.CardUtils;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.GravatarUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class CardsAdapter extends BaseAdapter {

    public static final int[] colors = new int[]{
      R.color.green,
      R.color.yellow,
      R.color.blue
    };

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
        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        View accent = v.findViewById(R.id.accent_tab);

        int pos = (Character.toLowerCase(card.firstName.charAt(0)) - 'a') % colors.length;
        accent.setBackgroundColor(context.getResources().getColor(colors[pos]));

        showLoading(thumbnail);
        Bitmap b = MemoryCache.getInstance().get(context, GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            if(ConnectionUtils.hasInternet(context))
                new BitmapDownloaderTask(context, thumbnail).execute(card.getEmail());
            else{
                thumbnail.setImageDrawable(context.getResources().getDrawable(R.drawable.mystery_man));
                showThumbnail(thumbnail);
            }
        }else{
            thumbnail.setImageBitmap(b);
            showThumbnail(thumbnail);
        }

        //Set text
        email.setText(card.getEmail());
        name.setText(card.getFullName());

        //Check for social networks
    }

    private void showThumbnail(ImageView thumbnail){
        LinearLayout parent = (LinearLayout) thumbnail.getParent();
        parent.findViewById(R.id.thumbnail_progress).setVisibility(View.GONE);
        thumbnail.setVisibility(View.VISIBLE);
    }

    private void showLoading(ImageView thumbnail){
        thumbnail.setVisibility(View.GONE);
        LinearLayout parent = (LinearLayout) thumbnail.getParent();
        parent.findViewById(R.id.thumbnail_progress).setVisibility(View.VISIBLE);
    }
}
