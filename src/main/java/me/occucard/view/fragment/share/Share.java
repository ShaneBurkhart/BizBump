package me.occucard.view.fragment.share;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.occucard.R;
import me.occucard.controller.HomeActivity;
import me.occucard.controller.share.ShareByEmailActivity;
import me.occucard.controller.share.ShareByQRActivity;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.FontUtils;
import me.occucard.utils.MarketingUtils;
import me.occucard.view.dialog.DefaultDialog;

/**
 * Created by Shane on 8/12/13.
 */
public class Share extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout v =  (LinearLayout) inflater.inflate(R.layout.share_options, container, false);

        v.findViewById(R.id.share_by_email).setOnClickListener(new ShareItemClickListener());
        v.findViewById(R.id.share_by_qr_code).setOnClickListener(new ShareItemClickListener());
        v.findViewById(R.id.share_by_text).setOnClickListener(new ShareItemClickListener());

        Typeface font = FontUtils.getIconFont(getActivity());
        ((TextView) v.findViewById(R.id.email_icon)).setTypeface(font);
        ((TextView) v.findViewById(R.id.text_icon)).setTypeface(font);
        ((TextView) v.findViewById(R.id.qr_icon)).setTypeface(font);

        HomeActivity activity = (HomeActivity) getActivity();
        activity.actionBarState = HomeActivity.NONE;
        activity.supportInvalidateOptionsMenu();

        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return v;
    }

    private class ShareItemClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.share_by_email && !ConnectionUtils.hasInternet(getActivity())){
                DefaultDialog.create(getActivity(), "No Connection.", "You can't share by email without an internet connection.").show();
                return;
            }
            Class c = getShareOptionActivityClass(view);
            if(c != null){
                Intent i = new Intent(getActivity(), c);
                getActivity().startActivity(i);
            }
            if(view.getId() == R.id.share_by_text){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                intent.putExtra("sms_body", MarketingUtils.getSMSText("Shane"));
                startActivity(intent);
            }
        }

        private Class getShareOptionActivityClass(View view){
            switch (view.getId()){
                case R.id.share_by_email:
                    return ShareByEmailActivity.class;
                case R.id.share_by_qr_code:
                    return ShareByQRActivity.class;
            }
            return null;
        }
    }
}
