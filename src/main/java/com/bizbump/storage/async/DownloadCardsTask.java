package com.bizbump.storage.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.bizbump.controller.HomeActivity;
import com.bizbump.model.Card;

/**
 * Created by Shane on 8/14/13.
 */
public class DownloadCardsTask extends AsyncTask<String, Void, String>{

    ProgressDialog dialog;
    HomeActivity context;

    public DownloadCardsTask(HomeActivity context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        Card.getCardFromAPI(context, strings[0]);
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(this.context);
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        context.showInitialView();
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
