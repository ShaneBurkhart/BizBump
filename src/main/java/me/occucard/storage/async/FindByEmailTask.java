package me.occucard.storage.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import me.occucard.controller.HomeActivity;
import me.occucard.model.Card;
import me.occucard.utils.ConnectionUtils;
import me.occucard.view.dialog.DefaultDialog;

/**
 * Created by Shane on 8/14/13.
 */
public class FindByEmailTask extends AsyncTask<String, Void, String>{

    ProgressDialog dialog;
    HomeActivity context;

    public FindByEmailTask(HomeActivity context){
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
            DefaultDialog.create(context, "No Connection", "We can not get your cards due to lack of connection.").show();
            return;
        }
        dialog = new ProgressDialog(this.context);
        dialog.setMessage("Getting your contacts.");
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
