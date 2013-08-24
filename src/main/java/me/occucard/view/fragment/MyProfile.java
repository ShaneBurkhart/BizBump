package me.occucard.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.occucard.R;
import me.occucard.model.Card;
import me.occucard.storage.async.BitmapDownloaderTask;
import me.occucard.storage.async.UpdateProfileTask;
import me.occucard.storage.cache.MemoryCache;
import me.occucard.storage.cache.OccucardTokenCache;
import me.occucard.utils.ConnectionUtils;
import me.occucard.utils.GravatarUtils;
import me.occucard.view.dialog.DefaultDialog;

/**
 * Created by Shane on 8/8/13.
 */
public class MyProfile extends Fragment {

    Card me = null;

    EditText email, name, phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_profile, container, false);

        me = Card.getMyProfile(getActivity());
        if(me == null && (!ConnectionUtils.hasInternet(getActivity()) || !OccucardTokenCache.getInstance().hasToken())){
            handleProfileNotFoundError();
            return v;
        }
        if(me == null)
            new UpdateProfileTask(this).execute();
        else
            setCard(me, v);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        return v;
    }

    public void setCard(Card card, View v){
        (v.findViewById(R.id.update_profile)).setOnClickListener(new UpdateProfileClickListener());
        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        phoneNumber = (EditText) v.findViewById(R.id.phone_number);

        name.setText(card.getFullName());
        email.setText(card.email);
        phoneNumber.setText(card.phoneNumber);

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        Bitmap b = MemoryCache.getInstance().get(getActivity(), GravatarUtils.getGravatarURL(card.getEmail()));
        if(b == null){
            if(ConnectionUtils.hasInternet(getActivity()))
                new BitmapDownloaderTask(getActivity(), thumbnail).execute(card.getEmail());
            else{
                thumbnail.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mystery_man));
                showThumbnail(thumbnail);
            }
        }else{
            thumbnail.setImageBitmap(b);
            showThumbnail(thumbnail);
        }
    }

    private void handleProfileNotFoundError(){
        AlertDialog d = DefaultDialog.create(getActivity(), "No Profile Found", "Your profile wasn't found and you don't have internet connection.  Please login later to sync your profile.");
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showThumbnail(ImageView thumbnail){
        LinearLayout parent = (LinearLayout) thumbnail.getParent();
        parent.findViewById(R.id.thumbnail_progress).setVisibility(View.GONE);
        thumbnail.setVisibility(View.VISIBLE);
    }

    private class UpdateProfileClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(me == null)
                me = new Card("", "", "", "");

            String fullName = name.getText().toString().trim();
            if(fullName.contains(" ")){
                String[] parts = fullName.split("\\s+", 1);
                me.firstName = parts[0];
                me.lastName = parts.length > 1 ? parts[1] : null;
            }else
                me.firstName = fullName;

            me.phoneNumber = phoneNumber.getText().toString().trim();
            me.email = email.getText().toString().trim();
            new UpdateProfileTask(MyProfile.this).execute(me);
        }
    }
}
