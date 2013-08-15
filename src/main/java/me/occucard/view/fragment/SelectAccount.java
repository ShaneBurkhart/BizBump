package me.occucard.view.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.auth.GoogleAuthUtil;

import me.occucard.R;
import me.occucard.utils.auth.AcquireTokenTask;
import me.occucard.utils.auth.OAuthUtils;

/**
 * Created by Shane on 8/15/13.
 */
public class SelectAccount extends Fragment {

    Account[] accounts;
    AccountManager am;

    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_account, null);

        am = OAuthUtils.getManager(getActivity());
        accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

        ListView lv = (ListView) v.findViewById(R.id.account_list);

        String[] aStrings = new String[accounts.length];
        for(int i = 0 ; i < aStrings.length ; i ++)
            aStrings[i] = accounts[i].name;

        lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.account_item, aStrings));
        lv.setOnItemClickListener(new AccountSelectionListener());

        v.findViewById(R.id.choose_email).setOnClickListener(new SelectAccountListener());
        email = (EditText) v.findViewById(R.id.account_email);

        ActionBarActivity activity = (ActionBarActivity) getActivity();

        activity.getSupportActionBar().setIcon(me.occucard.R.drawable.logo);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return v;
    }

    private class AccountSelectionListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String e = accounts[i].name;
            goToPasswordScreen(e);
        }
    }

    private class SelectAccountListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String e = email.getText().toString();
            //Check if email
            if(!e.equals("")){
                goToPasswordScreen(e);
            }
        }
    }

    private void goToPasswordScreen(String email){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectPassword(email)).commit();
    }
}
