package me.occucard.utils;

/**
 * Created by Shane on 8/13/13.
 */
public class MarketingUtils {

    public static final String APP_NAME = "OccuCard";
    public static final String APP_URL = "https://play.google.com/store/apps/details?id=me.occucard";

    public static String getSMSText(String name){
        return name + " has invited you to join " + APP_NAME + ". Download in the google play app store. " + APP_URL;
    }
}
