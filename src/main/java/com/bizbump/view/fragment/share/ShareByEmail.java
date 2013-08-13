package com.bizbump.view.fragment.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bizbump.R;
import com.bizbump.controller.MainActivity;
/**
 * Created by Shane on 8/12/13.
 */
public class ShareByEmail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout v =  (LinearLayout) inflater.inflate(R.layout.share_options, container, false);

        MainActivity activity = (MainActivity) getActivity();
        activity.actionBarState = MainActivity.NONE;
        activity.supportInvalidateOptionsMenu();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return v;
    }
}
