package me.occucard.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Shane on 8/14/13.
 */
public class ContactUtils {

    public static void launchCall(Context context, String phone){
        String uri = "tel:" + phone.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void launchEmail(Context context, String email){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.trim(), null));
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void launchText(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phone));
        context.startActivity(intent);
    }

}
