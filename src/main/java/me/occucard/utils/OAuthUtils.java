package me.occucard.utils;

import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by Shane on 8/14/13.
 */
public class OAuthUtils {

    private static String token = null;
    private static String account_type = null;

    public static boolean isLoggedIn(Context context){
        AccountManager am = AccountManager.get(context);
        return am.getAuthenticatorTypes().length > 0;
    }

    public static AccountManager getManager(Context context){
        return AccountManager.get(context);
    }

    public static void logout(Context context){
        AccountManager.get(context).invalidateAuthToken(account_type, token);
    }

    public static void setToke(String account_type, String token){
        OAuthUtils.account_type = account_type;
        OAuthUtils.token = token;
    }
}
