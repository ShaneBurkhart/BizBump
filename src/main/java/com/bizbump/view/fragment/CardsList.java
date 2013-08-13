package com.bizbump.view.fragment;

import com.bizbump.controller.CardDetailsActivity;
import com.bizbump.model.Card;
import com.bizbump.view.adapter.CardsAdapter;

import com.bizbump.R;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.AdapterView;

/**
 * Created by Shane on 8/5/13.
 */
public class CardsList extends ListFragment {

    Card[] cards = new Card[] {
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkht@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
        new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setListAdapter(new CardsAdapter(this.getActivity(), R.layout.card_item, cards));
        return inflater.inflate(R.layout.cards_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Set divider
        this.getListView().setDivider(new ColorDrawable(Color.TRANSPARENT));
        this.getListView().setDividerHeight(10);
        this.getListView().setOnItemClickListener(new CardClickListener());

        super.onViewCreated(view, savedInstanceState);
    }

    View previous;

    private class CardClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getActivity(), CardDetailsActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
