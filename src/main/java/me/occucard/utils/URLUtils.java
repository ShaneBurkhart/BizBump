package me.occucard.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Shane on 8/15/13.
 */
public class URLUtils {
    //public static final String BASE_URL = "http://occucard.me";
    public static final String BASE_URL = "http://bizcardbackend.herokuapp.com";
    public static final String REGISTER_SUFFIX_URL = "/user/register";
    public static final String CONTACTS_SUFFIX_URL = "/user/register";
    public static final String USER_TOKEN_KEY = "apiToken";

    public static HttpResponse getContactsGETResponse(String token) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL + REGISTER_SUFFIX_URL + "?" + USER_TOKEN_KEY + "=" + token);
        HttpResponse response = null;
        try{
            response = httpclient.execute(httppost);
        }catch (IOException e){
            Log.d("Register Response", e.getMessage());
        }
        return response;
    }

    public static HttpResponse getRegisterPOSTReponse(String email, String password) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL + REGISTER_SUFFIX_URL);
        HttpResponse response = null;
        try{
            //Get JSON
            JSONObject object = new JSONObject();
            object.put("email", email);
            object.put("password", password);
            JSONObject root = new JSONObject();
            root.put("user", object);

            httppost.setHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(root.toString()));
            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

        }catch (IOException e){
            Log.d("Register Response", e.getMessage());
        }catch (JSONException e){
            Log.d("Register Response", e.getMessage());
        }
        return response;
    }

    public static String getResponseBodyString(HttpResponse response){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        }catch (IOException e) {
            Log.d("Register Response String", e.getMessage());
        }catch (Exception e) {
            Log.d("Register Response String", e.getMessage());
        }
        return null;
    }

    public static JSONObject getResponseBodyJSON(HttpResponse response){
        try{
            return new JSONObject(getResponseBodyString(response));
        }catch (JSONException e){
            Log.d("Register Response JSON", e.getMessage());
        }
        return null;
    }
}
