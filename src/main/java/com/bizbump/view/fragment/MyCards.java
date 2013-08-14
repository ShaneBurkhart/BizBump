package com.bizbump.view.fragment;

import com.bizbump.controller.CardDetailsActivity;
import com.bizbump.controller.HomeActivity;
import com.bizbump.model.Card;
import com.bizbump.utils.ContactUtils;
import com.bizbump.view.adapter.CardsAdapter;

import com.bizbump.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.AdapterView;

/**
 * Created by Shane on 8/5/13.
 */
public class MyCards extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setListAdapter(new CardsAdapter(this.getActivity(), Card.all()));
        return inflater.inflate(R.layout.cards_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Set divider
        this.getListView().setDivider(new ColorDrawable(Color.TRANSPARENT));
        this.getListView().setDividerHeight(10);
        CardInputListener listener = new CardInputListener();
        this.getListView().setOnItemClickListener(listener);
        this.getListView().setOnItemLongClickListener(listener);

        //Set state and invalidate
        HomeActivity activity = (HomeActivity) getActivity();
        activity.actionBarState = HomeActivity.MY_CARDS;
        activity.supportInvalidateOptionsMenu();

        activity.setTitle("My Cards");

        //Set other action bar attrs
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        super.onViewCreated(view, savedInstanceState);
    }

    private class CardInputListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getAdapter().getItemViewType(i) == CardsAdapter.CARD){
                final Card card = (Card) getListAdapter().getItem(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option")
                .setItems(new String[]{"Call", "Text", "Email"}, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                ContactUtils.launchCall(MyCards.this.getActivity(), card.phoneNumber);
                                break;
                            case 1:
                                ContactUtils.launchText(MyCards.this.getActivity(), card.phoneNumber);
                                break;
                            case 2:
                                ContactUtils.launchEmail(MyCards.this.getActivity(), card.email);
                                break;
                        }
                        dialog.dismiss();
                    }

                });
                builder.show();
                return true;
            }
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getAdapter().getItemViewType(i) == CardsAdapter.CARD){
                Intent intent = new Intent(getActivity(), CardDetailsActivity.class);
                CardsAdapter adapter = (CardsAdapter) getListAdapter();
                Card card = (Card) adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putString("email", card.email);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        }
    }
}
