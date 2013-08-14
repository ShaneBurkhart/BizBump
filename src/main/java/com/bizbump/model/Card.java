package com.bizbump.model;

import java.util.ArrayList;

/**
 * Created by Shane on 8/5/13.
 */
public class Card {
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

    public static ArrayList<Object> all(){
        ArrayList<Object> obj = new ArrayList<Object>();
        for(int i = 0 ; i < cards.length ; i ++)
            obj.add(i, cards[i]);
        return obj;
    }

    public static Card findCardByEmail(String email){
        ArrayList<Object> cards = Card.all();
        for(Object o : cards){
            Card c = (Card) o;
            if(c.email.equals(email))
                return c;
        }
        return null;
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
}
