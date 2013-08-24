package me.occucard.storage.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import me.occucard.model.Card;
import me.occucard.storage.cache.OccucardTokenCache;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.URLUtils;
import me.occucard.utils.auth.OAuthUtils;
import me.occucard.view.dialog.DefaultDialog;
import me.occucard.view.fragment.MyProfile;

/**
 * Created by Shane on 8/14/13.
 */
public class UpdateProfileTask extends AsyncTask<Card, Void, Card> {

    private MyProfile frag;
    private ProgressDialog dialog;
    private Handler handler;

    private String error = "Server error. Please try again later.";

    public UpdateProfileTask(Fragment frag){
        this.frag = (MyProfile) frag;
        this.handler = new Handler();
    }

    @Override
    protected Card doInBackground(Card... input) {
        Card card = null;

        if(!ConnectionUtils.hasInternet(frag.getActivity()))
            return card;

        HttpResponse response = null;
        if(OccucardTokenCache.getInstance().hasToken()){
            if(input.length > 0)
                response = URLUtils.getUpdatePofilePOSTResponse(OccucardTokenCache.getInstance().getToken(), input[0]);
            else
                response = URLUtils.getMyProfilePOSTResponse(OccucardTokenCache.getInstance().getToken());
        }
        if(response != null){
            JSONObject json = URLUtils.getResponseBodyJSON(response);
            if(json != null){
                Log.d("JSON Output", json.toString());
                if(response.getStatusLine().getStatusCode() == 200){
                    card = Card.parseCard(json);
                    if(input.length > 0)
                        Toast.makeText(frag.getActivity(), "Updated profile", Toast.LENGTH_LONG);
                }else
                    error = "Profile not found.  Server could be temporarily unavailable.  Please try again later.";
            }
        }
        return card;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(frag.getActivity());
        dialog.setMessage("Getting your profile.");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Card c) {
        dialog.dismiss();
        if(c == null){
            AlertDialog dial = DefaultDialog.create(frag.getActivity(), "No Profile Found", error);
            dial.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    frag.getActivity().finish();
                }
            });
        }else{
            Card.saveMyProfile(frag.getActivity(), c);
            frag.setCard(c, frag.getView());
        }
    }

}
