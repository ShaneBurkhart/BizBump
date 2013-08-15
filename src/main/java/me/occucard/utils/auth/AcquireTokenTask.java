package me.occucard.utils.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

import me.occucard.controller.HomeActivity;
import me.occucard.controller.LoginActivity;

/**
 * Created by Shane on 8/14/13.
 */
public class AcquireTokenTask extends AsyncTask<String, Void, String> {

    private static final String SCOPE = "audience:server:client_id:";
    private static final String SERVER_CLICENT_ID = "524711141393-vh6egfeifprssin81l4jkjev39q3l1l8.apps.googleusercontent.com";

    private Context context;
    private ProgressDialog dialog;
    private Handler handler;

    private String account;
    private boolean isNew = false;

    public AcquireTokenTask(Context context){
        this.context = context;
        this.handler = new Handler();
    }

    public AcquireTokenTask(Context context, boolean isNew){
        this.context = context;
        this.handler = new Handler();
        this.isNew = isNew;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = null;
        String occucardToken = null;
        account = strings.length > 0 ? strings[0] : null;
        try{
        Log.d("Token Account", account);
        Log.d("Token Scope", SCOPE + SERVER_CLICENT_ID);
        if(account != null)
            token = GoogleAuthUtil.getToken(context, account, SCOPE + SERVER_CLICENT_ID);
        if(token != null){
            //Do a request to our API to get Occucard token.
            occucardToken = token;
        }
        }catch (IOException e){
        }catch (UserRecoverableAuthException e){
        }catch (GoogleAuthException e){}
        return occucardToken;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Logging you in.");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if(s == null || s.equals("")){
            //Failed to get token from api
            if(isNew) //Doesn't have any prior accounts
                showToast();
            else //Has prior contacts
                showToastAndRedirect();
        }else{
            //Got Token from api
            OAuthUtils.saveLoggedInAccount(context, account);
            redirect();
        }
        super.onPostExecute(s);
    }

    private void showToastAndRedirect(){
        showToast();
        redirect();
    }

    private void showToast(){
        Toast.makeText(context, "Couldn't log you in.  Try again later.", Toast.LENGTH_LONG).show();
    }

    private void redirect(){
        Intent intent = new Intent(context, HomeActivity.class);
        //Add token probably.  We'll see.
        context.startActivity(intent);
        LoginActivity activity = (LoginActivity) context;
        activity.finish();
    }
}
