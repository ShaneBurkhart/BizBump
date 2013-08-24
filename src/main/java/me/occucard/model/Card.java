package me.occucard.model;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.occucard.utils.URLUtils;

/**
 * Created by Shane on 8/5/13.
 */
public class Card {
    public static final String FILENAME = "contacts";
    public static final String MY_PROFILE_FILENAME = "my_profile";

    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;

    public Card(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

// Statics

    public static Card getMyProfile(Context context){
        try{
            FileInputStream is = context.openFileInput(MY_PROFILE_FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
                total.append(line);
             return Card.parseCard(new JSONObject(total.toString()));
        }catch (IOException e){
        }catch (JSONException e){}
        return null;
    }

    public static void saveMyProfile(Context context, Card card){
        try{
            FileOutputStream fos = context.openFileOutput(MY_PROFILE_FILENAME, Context.MODE_PRIVATE);
            fos.write(card.toJSON().getBytes());
            fos.close();
        } catch (IOException e){}
    }

    public static ArrayList<Object> all(Context context){
        try{
            FileInputStream is = context.openFileInput(FILENAME);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
                total.append(line);
            return Card.parseCards(new JSONObject(total.toString()));
        } catch (IOException e){
        } catch (JSONException e){}
        return new ArrayList<Object>();
    }

    public static Card findCardByEmail(Context context, String email){
        ArrayList<Object> cards = Card.all(context);
        for(Object o : cards){
            Card c = (Card) o;
            if(c.email.equals(email))
                return c;
        }
        return null;
    }

    public static boolean eraseCardStorage(Context context){
        File dir = context.getFilesDir();
        File file = new File(dir, FILENAME);
        return file.delete();
    }

    // Not thread safe
    public static void getCardFromAPI(Context context, String token){
        try{
            HttpResponse response = URLUtils.getContactsGETResponse(token);
            if(response != null){
                try {
                    String res = URLUtils.getResponseBodyString(response);
                    Log.d("Card Response", res);
                    JSONArray cards = new JSONArray(res);
                    JSONObject o = new JSONObject();
                    o.put("cards", cards);
                    FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(o.toString().getBytes());
                    fos.close();
                } catch (JSONException e){}
            }
        } catch (IOException e){}
    }

// Getters and Setters

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    //Formats
    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("email", this.email);
            obj.put("phoneNumber", this.phoneNumber);
            obj.put("firstName", this.firstName);
            obj.put("lastName", this.lastName);
            return obj;
        }catch (JSONException e){
            return obj;
        }
    }

    public String toJSON(){
        return toJSONObject().toString();
    }

    public static JSONArray cardsToJSON(ArrayList<Card> cards){
        JSONArray array = new JSONArray();
        for(Card c : cards)
            array.put(c.toJSONObject());
        return array;
    }

    public static JSONObject cardsToJSON(Card[] cards){
        JSONArray array = new JSONArray();
        for(Card c : cards)
            array.put(c.toJSONObject());
        JSONObject cont = new JSONObject();
        try{
            cont.put("cards", array);
        } catch (JSONException e){
            return cont;
        }
        return cont;
    }

    //Parse

    public static ArrayList<Object> parseCards(JSONObject root){
        ArrayList<Object> cards = new ArrayList<Object>();
        try{
            JSONArray array = root.getJSONArray("cards");
            for(int i = 0 ; i < array.length() ; i ++){
                Card c = Card.parseCard(array.getJSONObject(i));
                if(c != null) cards.add(c);
            }
            return cards;
        } catch (JSONException e){
            return cards;
        }
    }

    public static Card parseCard(JSONObject card){
        return new Card(card.optString("firstName"), card.optString("lastName"), card.optString("email"), card.optString("phoneNumber"));
    }
}
