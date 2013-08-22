package me.occucard.storage.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;

import me.occucard.controller.HomeActivity;
import me.occucard.controller.LoginActivity;
import me.occucard.storage.cache.OccucardTokenCache;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.URLUtils;
import me.occucard.utils.auth.OAuthUtils;

/**
 * Created by Shane on 8/14/13.
 */
public class AcquireTokenTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog dialog;
    private Handler handler;

    private String error = "Server error. Please try again later.";

    private boolean isNew = false;

    public AcquireTokenTask(Context context){
        this.context = context;
        this.handler = new Handler();
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings.length < 2)
            return null;
        String email = strings[0];
        String password = strings[1];
        String occucardToken = null;

        if(!ConnectionUtils.hasInternet(context))
            return occucardToken;

        HttpResponse response = null;
        if(email != null && password != null)
            response = URLUtils.getRegisterPOSTReponse(email, password);
        if(response != null){
            JSONObject json = URLUtils.getResponseBodyJSON(response);
            if(json != null){
                Log.d("Register Response", json.toString());
                try {
                    if(response.getStatusLine().getStatusCode() == 200)
                        occucardToken = json.getString(URLUtils.USER_TOKEN_KEY);
                    else
                        error = "Invalid email or password.";
                } catch (JSONException e){}
            }
        }
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
        if(s != null && !s.equals("")){
            OAuthUtils.saveLoggedInAccount(context, s);
            OccucardTokenCache.getInstance().setToken(s);
            Toast.makeText(context, "Succesfuly logged in.", Toast.LENGTH_LONG).show();
            redirect();
        }else{
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(s);
    }

    private void showToast(){
        String start = "Couldn't log you in";
        String middle = ".  ";
        String end = "Try again later.";
        if(!isNew)
            middle = " but you can still view your contacts.  ";
        Toast.makeText(context, start + middle + end, Toast.LENGTH_LONG).show();
    }

    private void redirect(){
        Intent intent = new Intent(context, HomeActivity.class);
        //Add token probably.  We'll see.
        context.startActivity(intent);
        LoginActivity activity = (LoginActivity) context;
        activity.finish();
    }
}
