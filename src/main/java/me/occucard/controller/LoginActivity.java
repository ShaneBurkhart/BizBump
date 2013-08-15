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
import me.occucard.view.fragment.SelectAccount;

/**
 * Created by Shane on 8/13/13.
 */
public class LoginActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);



        //Check for logged in
        /*String accountName = OAuthUtils.getLoggedInAccount(this);
        if(accountName != null){
            for(Account a : accounts){
                if(a.name.equals(accountName)){
                    startTokenTask(accountName, false);
                    return;
                }
            }
        }*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectAccount()).commit();
    }


}
