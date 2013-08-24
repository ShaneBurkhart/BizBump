package me.occucard.storage.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;

import me.occucard.model.Card;
import me.occucard.storage.cache.OccucardTokenCache;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.URLUtils;
import me.occucard.view.dialog.DefaultDialog;

/**
 * Created by Shane on 8/14/13.
 */
public class FindByEmailTask extends AsyncTask<String, Void, String>{

    ProgressDialog dialog;
    Context context;

    public FindByEmailTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String email = null;
        if(strings.length < 1)
            return null;
        HttpResponse response = URLUtils.getFindByEmailPOSTResponse(OccucardTokenCache.getInstance().getToken(), strings[0]);
        if(response != null){
            //No need for this at the moment
            //JSONObject object = URLUtils.getResponseBodyJSON(response);
            if(response.getStatusLine().getStatusCode() == 200){
                Card.getCardFromAPI(context, OccucardTokenCache.getInstance().getToken());
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        if(!ConnectionUtils.hasInternet(context)){
            DefaultDialog.create(context, "No Connection", "We can not find anyone without an internet connectino.").show();
            return;
        }
        dialog = new ProgressDialog(this.context);
        dialog.setMessage("Finding contacts.");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if(s == null || s.equals("")){
            Toast.makeText(context, "Couldn't find user with that email.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Found user!", Toast.LENGTH_LONG).show();
        }
    }
}
