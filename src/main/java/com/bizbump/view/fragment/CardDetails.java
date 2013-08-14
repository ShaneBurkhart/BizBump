package com.bizbump.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizbump.R;
import com.bizbump.controller.CardDetailsActivity;
import com.bizbump.model.Card;
import com.bizbump.storage.cache.BitmapDownloader;
import com.bizbump.storage.cache.MemoryCache;
import com.bizbump.utils.GravatarUtils;

/**
 * Created by Shane on 8/8/13.
 */
public class CardDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_details, container, false);

        CardDetailsActivity activity = (CardDetailsActivity) getActivity();
        Card card = activity.card;

        TextView email = (TextView) v.findViewById(R.id.email);
        TextView phone = (TextView) v.findViewById(R.id.phone_number);
        TextView name = (TextView) v.findViewById(R.id.name);

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        email.setText(card.getEmail());
        name.setText(card.getFullName());
        phone.setText(card.getPhoneNumber());

        Bitmap b = MemoryCache.getInstance().get(GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            //TODO Set some loading image
            new BitmapDownloader(thumbnail).execute(card.getEmail());
        }else{
            thumbnail.setImageBitmap(b);
        }

        activity.setTitle(card.getFullName());

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
