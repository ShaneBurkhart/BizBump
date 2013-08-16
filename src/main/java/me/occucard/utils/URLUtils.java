package me.occucard.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shane on 8/15/13.
 */
public class URLUtils {
    public static final String BASE_URL = "http://occucard.me";
    public static final String REGISTER_SUFFIX_URL = "/user/register";

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
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user", object.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

        }catch (IOException e){
        }catch (JSONException e){}
        return response;
    }

    public static String getResponseBodyString(HttpResponse response){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        }catch (IOException e) {
        }catch (Exception e) {}
        return null;
    }

    public static JSONObject getResponseBodyJSON(HttpResponse response){
        try{
            return new JSONObject(getResponseBodyString(response));
        }catch (JSONException e){
            return null;
        }
    }
}
