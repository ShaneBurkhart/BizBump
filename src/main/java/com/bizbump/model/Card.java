package com.bizbump.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Shane on 8/5/13.
 */
public class Card {
    public static final String FILENAME = "contacts";

    public String firstName, lastName, email, phoneNumber;

    public Card(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

// Statics

    static Card[] cards = new Card[] {
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkht@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Ryan", "Endacott", "rzendacott@gmail.com", "417-209-2813"),
            new Card("Daniel", "Holmgren", "dansdebate@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("a", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813"),
            new Card("Shane", "Burkhart", "shaneburkhart@gmail.com", "417-209-2813")
    };

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

    // Not thread safe
    public static void getCardFromAPI(Context context, String token){
        try{
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(Card.cardsToJSON(cards).toString().getBytes());
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

// Getters and Setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Formats
    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("email", this.email);
            obj.put("phone_number", this.phoneNumber);
            obj.put("first_name", this.firstName);
            obj.put("last_name", this.lastName);
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
        try{
            return new Card(card.getString("first_name"), card.getString("last_name"), card.getString("email"), card.getString("phone_number"));
        }catch (JSONException e){
            return null;
        }
    }
}
