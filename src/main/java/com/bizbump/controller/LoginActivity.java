package com.bizbump.controller;

import android.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.accounts.OperationCanceledException;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import android.accounts.AuthenticatorException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bizbump.utils.OAuthUtils;

import java.io.IOException;

/**
 * Created by Shane on 8/13/13.
 */
public class LoginActivity extends ActionBarActivity {

    Account[] accounts;
    AccountManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        redirect();
        finish();


        am = OAuthUtils.getManager(this);

        //Check for logged in
        if(OAuthUtils.isLoggedIn(this)){

            //return;
        }

        ListView lv = new ListView(this);
        setContentView(lv);

        accounts = am.getAccountsByType("com.google");

        String[] aStrings = new String[accounts.length];
        for(int i = 0 ; i < aStrings.length ; i ++)
            aStrings[i] = accounts[i].type + " - " + accounts[i].name;

        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item_1, R.id.text1, aStrings));
        lv.setOnItemClickListener(new AccountSelectionListener());

        getSupportActionBar().setIcon(com.bizbump.R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private class AccountSelectionListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            am.getAuthToken(accounts[i], "Manage your tasks", new Bundle(), LoginActivity.this, new OnTokenAcquired(), new Handler());
        }
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try{
                // Get the result of the operation from the AccountManagerFuture.
                Bundle bundle = result.getResult();
                // The token is a named value in the bundle. The name of the value
                // is stored in the constant AccountManager.KEY_AUTHTOKEN.
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                Log.d("Account - Token", token);
                //redirect();
            } catch (IOException e){
                e.printStackTrace();
            } catch (OperationCanceledException e){
                e.printStackTrace();
            } catch (AuthenticatorException e){
                e.printStackTrace();
            }
        }
    }

    private void redirect(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        //Add token probably.  We'll see.
        startActivity(intent);
    }
}
