package com.bizbump.controller;

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

import java.io.IOException;

/**
 * Created by Shane on 8/13/13.
 */
public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType("com.google");

        am.getAuthToken(accounts[1], "Manage your tasks", new Bundle(), this, new OnTokenAcquired(), new Handler());
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

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //Add token probably.  We'll see.
                startActivity(intent);

            } catch (IOException e){
                e.printStackTrace();
            } catch (OperationCanceledException e){
                e.printStackTrace();
            } catch (AuthenticatorException e){
                e.printStackTrace();
            }
        }
    }
}
