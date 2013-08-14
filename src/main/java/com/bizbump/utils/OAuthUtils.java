package com.bizbump.utils;

import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by Shane on 8/14/13.
 */
public class OAuthUtils {

    public static boolean isLoggedIn(Context context){
        AccountManager am = AccountManager.get(context);
        return am.getAuthenticatorTypes().length > 0;
    }

    public static AccountManager getManager(Context context){
        return AccountManager.get(context);
    }
}
