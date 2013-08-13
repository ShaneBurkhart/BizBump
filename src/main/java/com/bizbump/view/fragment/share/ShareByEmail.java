package com.bizbump.view.fragment.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bizbump.R;
import com.bizbump.controller.MainActivity;
/**
 * Created by Shane on 8/12/13.
 */
public class ShareByEmail extends Fragment {

    EditText emailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout v =  (LinearLayout) inflater.inflate(R.layout.share_by_email, container, false);

        emailText = (EditText) v.findViewById(R.id.share_email_text);
        v.findViewById(R.id.share_send_email).setOnClickListener(new EmailSendClickListener());

        return v;
    }

    public class EmailSendClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String emsil = emailText.getText().toString();
            //Send email to api
        }
    }
}
