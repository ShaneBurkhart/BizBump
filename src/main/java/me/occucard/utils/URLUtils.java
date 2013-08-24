package me.occucard.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
    public static final String API_TOKEN = "3020ebc5-ef9a-414a-b387-0cd98d244b9f";
    //public static final String BASE_URL = "http://occucard.me";
    public static final String BASE_URL = "http://occucard.herokuapp.com";
    public static final String REGISTER_SUFFIX_URL = "/user/register";
    public static final String CONTACTS_SUFFIX_URL = "/contacts";
    public static final String UPDATE_PROFILE_SUFFIX_URL = "/user/update";
    public static final String FIND_BY_EMAIL_SUFFIX_URL = "/contacts/add/email";
    public static final String USER_TOKEN_KEY = "userToken";
    public static final String API_TOKEN_KEY = "apiToken";

    public static HttpResponse getContactsGETResponse(String token) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(BASE_URL + CONTACTS_SUFFIX_URL + "?" + USER_TOKEN_KEY + "=" + token + "&" + API_TOKEN_KEY + "=" + API_TOKEN);
        HttpResponse response = null;
        try{
            response = httpclient.execute(httpget);
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
            root.put(API_TOKEN_KEY, API_TOKEN);

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

    public static HttpResponse getFindByEmailPOSTResponse(String token, String email) {
        if(email == null || email.equals(""))
            return null;
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL + FIND_BY_EMAIL_SUFFIX_URL);
        HttpResponse response = null;
        try{
            //Get JSON
            JSONObject object = new JSONObject();
            object.put(USER_TOKEN_KEY, token);
            object.put(API_TOKEN_KEY, API_TOKEN);
            object.put("email", email);
            httppost.setHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(object.toString()));
            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
        }catch (IOException e){
            Log.d("Register Response", e.getMessage());
        }catch (JSONException e){
            Log.d("Register Response", e.getMessage());
        }
        return response;
    }

    public static HttpResponse getMyProfilePOSTResponse(String token) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL + UPDATE_PROFILE_SUFFIX_URL);
        HttpResponse response = null;
        try{
            //Get JSON
            JSONObject object = new JSONObject();
            object.put(USER_TOKEN_KEY, token);
            object.put(API_TOKEN_KEY, API_TOKEN);
            httppost.setHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(object.toString()));
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
