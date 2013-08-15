package me.occucard.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;

import me.occucard.R;
import me.occucard.utils.auth.AcquireTokenTask;
import me.occucard.utils.auth.OAuthUtils;

/**
 * Created by Shane on 8/13/13.
 */
public class LoginActivity extends ActionBarActivity {

    Account[] accounts;
    AccountManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        am = OAuthUtils.getManager(this);
        accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

        //Check for logged in
        String accountName = OAuthUtils.getLoggedInAccount(this);
        if(accountName != null){
            for(Account a : accounts){
                if(a.name.equals(accountName)){
                    startTokenTask(accountName, false);
                    return;
                }
            }
        }

        setContentView(R.layout.select_account);

        ListView lv = (ListView) findViewById(R.id.account_list);

        String[] aStrings = new String[accounts.length];
        for(int i = 0 ; i < aStrings.length ; i ++)
            aStrings[i] = accounts[i].name;

        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.account_item, aStrings));
        lv.setOnItemClickListener(new AccountSelectionListener());

        getSupportActionBar().setIcon(me.occucard.R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private class AccountSelectionListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            startTokenTask(accounts[i].name, true);
        }
    }

    private void startTokenTask(String accountName, boolean isNew){
        new AcquireTokenTask(LoginActivity.this, isNew).execute(accountName);
    }
}
