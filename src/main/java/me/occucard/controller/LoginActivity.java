package me.occucard.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import me.occucard.R;
import me.occucard.utils.auth.OAuthUtils;
import me.occucard.view.fragment.SelectAccount;
import me.occucard.view.fragment.SelectPassword;

/**
 * Created by Shane on 8/13/13.
 */
public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);

        OAuthUtils.deleteLoggedInAccount(this);
        //Check for logged in
        String accountToken = OAuthUtils.getLoggedInAccount(this);
        if(accountToken != null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectAccount()).commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof SelectPassword)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectAccount()).commit();
        else
            super.onBackPressed();
    }
}
