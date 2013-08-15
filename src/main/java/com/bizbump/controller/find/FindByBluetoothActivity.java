package com.bizbump.controller.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.bizbump.R;
import com.bizbump.view.fragment.find.FindByBluetooth;
import com.bizbump.view.fragment.share.ShareByQR;

/**
 * Created by Shane on 8/13/13.
 */
public class FindByBluetoothActivity extends ActionBarActivity{

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
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FindByBluetooth()).commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setIcon(com.bizbump.R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
