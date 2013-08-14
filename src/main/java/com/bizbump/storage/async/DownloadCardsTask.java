package com.bizbump.storage.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.bizbump.controller.HomeActivity;
import com.bizbump.model.Card;
import com.bizbump.utils.ConnectionUtils;

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
        if(!ConnectionUtils.hasInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Connection")
                    .setMessage("We can not get your cards due to lack of connection.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
            return;
        }
        dialog = new ProgressDialog(this.context);
        dialog.setTitle("Getting your contacts.");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if(!isCancelled()){
            if(dialog != null)
                dialog.dismiss();
            context.showInitialView();
        }
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
