package me.occucard.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.occucard.R;
import me.occucard.model.Card;
import me.occucard.utils.ContactUtils;
import me.occucard.view.fragment.CardDetails;

/**
 * Created by Shane on 8/8/13.
 */
public class CardDetailsActivity extends ActionBarActivity {

    public Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);

        String email = getIntent().getExtras().getString("email");

        if(email != null)
            card = Card.findCardByEmail(this, email);

        //Check if container exists
        if(findViewById(R.id.fragment_container) != null){
            //Now add some fragment.
            //Return if already opened or something like that.
            if(savedInstanceState != null){
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new CardDetails()).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
      public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_call:
                ContactUtils.launchCall(this, card.phoneNumber);
                return true;
            case R.id.action_email:
                ContactUtils.launchEmail(this, card.email);
                return true;
            case R.id.action_text:
                ContactUtils.launchText(this, card.phoneNumber);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
