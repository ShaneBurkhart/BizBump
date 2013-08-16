package me.occucard.utils.auth;

import android.accounts.AccountManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Shane on 8/14/13.
 */
public class OAuthUtils {

    private static final String FILENAME = "user_account";

    private static String token = null;
    private static String account_type = null;

    public static String getLoggedInAccount(Context context){
        try{
            FileInputStream is = context.openFileInput(FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
                total.append(line);
            return total.toString();
        }catch (IOException e){
            return null;
        }
    }

    public static void saveLoggedInAccount(Context context, String occucardToken){
        try{
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(occucardToken.getBytes());
            fos.close();
        } catch (IOException e){}
    }

    public static boolean deleteLoggedInAccount(Context context){
        File dir = context.getFilesDir();
        File file = new File(dir, FILENAME);
        return file.delete();
    }

    public static boolean logout(Context context){
        int ITERATIONS = 3;
        for(int i = 0 ; i < ITERATIONS ; i ++){
            if (OAuthUtils.deleteLoggedInAccount(context)) return true;
        }
        return false;
    }

    public static AccountManager getManager(Context context){
        return AccountManager.get(context);
    }
}
