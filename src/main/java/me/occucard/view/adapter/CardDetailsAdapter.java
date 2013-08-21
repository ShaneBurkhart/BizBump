package me.occucard.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.occucard.R;

/**
 * Created by Shane on 8/19/13.
 */
public class CardDetailsAdapter {
    private ArrayList<CardDetail> details;
    private LinearLayout container;
    private Context context;

    public CardDetailsAdapter(Context context, LinearLayout container){
        this.container = container;
        this.context = context;
    }

    public void setDetails(ArrayList<CardDetail> details){
        this.details = details;
        if(container == null)
            return;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        container.removeAllViews();
        for(CardDetail detail : details){
            if(detail == null)
                continue;

            View v = inflater.inflate(R.layout.detail_item, null);

            TextView title = (TextView) v.findViewById(R.id.title);
            TextView info = (TextView) v.findViewById(R.id.info);
            title.setText(detail.title);
            info.setText(detail.info);
            this.container.addView(v);
        }
    }
}

