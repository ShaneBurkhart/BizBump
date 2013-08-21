package me.occucard.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.occucard.R;
import me.occucard.controller.CardDetailsActivity;
import me.occucard.model.Card;
import me.occucard.storage.async.BitmapDownloaderTask;
import me.occucard.storage.cache.MemoryCache;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.GravatarUtils;
import me.occucard.view.adapter.CardDetail;
import me.occucard.view.adapter.CardDetailsAdapter;

/**
 * Created by Shane on 8/8/13.
 */
public class CardDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_details, container, false);

        CardDetailsActivity activity = (CardDetailsActivity) getActivity();
        Card card = activity.card;

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(card.getFullName());

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        addDetails(card, (LinearLayout) v.findViewById(R.id.details_container));

        Bitmap b = MemoryCache.getInstance().get(getActivity(), GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            if(ConnectionUtils.hasInternet(getActivity()))
                new BitmapDownloaderTask(getActivity(), thumbnail).execute(card.getEmail());
            else{
                thumbnail.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mystery_man));
                showThumbnail(thumbnail);
            }
        }else{
            thumbnail.setImageBitmap(b);
            showThumbnail(thumbnail);
        }

        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return v;
    }

    private void addDetails(Card card, LinearLayout container){
        ArrayList<CardDetail> details = new ArrayList<CardDetail>();
        details.add(new CardDetail("Phone Number", card.phoneNumber));
        details.add(new CardDetail("Email", card.email));
        new CardDetailsAdapter(getActivity(), container).setDetails(details);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showThumbnail(ImageView thumbnail){
        LinearLayout parent = (LinearLayout) thumbnail.getParent();
        parent.findViewById(R.id.thumbnail_progress).setVisibility(View.GONE);
        thumbnail.setVisibility(View.VISIBLE);
    }
}
