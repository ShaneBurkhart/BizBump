package com.bizbump.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shane on 8/6/13.
 */
public class GravatarUtils {

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i)
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String getGravatarURL(String email){
        String hash = GravatarUtils.md5Hex(email);
        hash = hash == null ? "" : hash;
        Log.d("Gravatar Hash", hash);
        return "http://www.gravatar.com/avatar/" + hash + "?s=256&d=mm";
    }
}
