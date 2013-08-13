package com.bizbump.controller.share;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.bizbump.R;
import com.bizbump.view.fragment.share.ShareByEmail;
import com.bizbump.view.fragment.share.ShareByQR;

/**
 * Created by Shane on 8/12/13.
 */
public class ShareByQRActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);

        //Check if container exists
        if(findViewById(R.id.fragment_container) != null){
            //Now add some fragment.
            //Return if already opened or something like that.
            if(savedInstanceState != null){
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ShareByQR()).commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
