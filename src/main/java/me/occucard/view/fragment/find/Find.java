package me.occucard.view.fragment.find;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.occucard.R;
import me.occucard.controller.HomeActivity;
import me.occucard.controller.find.FindByBluetoothActivity;
import me.occucard.controller.find.FindByEmailActivity;
import me.occucard.controller.find.FindByQRActivity;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.FontUtils;
import me.occucard.view.dialog.DefaultDialog;

/**
 * Created by Shane on 8/12/13.
 */
public class Find extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout v =  (LinearLayout) inflater.inflate(R.layout.find_options, container, false);

        v.findViewById(R.id.find_by_email).setOnClickListener(new FindItemClickListener());
        v.findViewById(R.id.find_by_qr_code).setOnClickListener(new FindItemClickListener());
        v.findViewById(R.id.find_by_bluetooth).setOnClickListener(new FindItemClickListener());

        Typeface font = FontUtils.getIconFont(getActivity());
        ((TextView) v.findViewById(R.id.email_icon)).setTypeface(font);
        ((TextView) v.findViewById(R.id.blutooth_icon)).setTypeface(font);
        ((TextView) v.findViewById(R.id.qr_icon)).setTypeface(font);

        HomeActivity activity = (HomeActivity) getActivity();
        activity.actionBarState = HomeActivity.NONE;
        activity.supportInvalidateOptionsMenu();

        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return v;
    }

    private class FindItemClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(!ConnectionUtils.hasInternet(getActivity())){
                String message;
                switch (view.getId()){
                    case R.id.find_by_email:
                        message = "You can't find by email without an internet connection.";
                        break;
                    case R.id.find_by_qr_code:
                        message = "You can't scan a qr code without an internet connection.";
                        break;
                    case R.id.find_by_bluetooth:
                        message = "You can't find people near me without an internet connection.";
                        break;
                    default:
                        message = "You can't find contacts without an internet connection.";
                        break;
                }
                DefaultDialog.create(getActivity(), "No Connection.", message).show();
                return;
            }
            //Temp!!!
            if(view.getId() == R.id.find_by_bluetooth){
                DefaultDialog.create(getActivity(), "Coming Soon!", "This feature hasn't been implemented yet.  Thanks for your patience!").show();
                return;
            }
            //Temp!!!
            Intent i = new Intent(getActivity(), getFindOptionActivityClass(view));
            getActivity().startActivity(i);
        }

        private Class getFindOptionActivityClass(View view){
            switch (view.getId()){
                case R.id.find_by_email:
                    return FindByEmailActivity.class;
                case R.id.find_by_qr_code:
                    return FindByQRActivity.class;
                case R.id.find_by_bluetooth:
                    return FindByBluetoothActivity.class;
                default:
                    return FindByEmailActivity.class;
            }
        }
    }
}
