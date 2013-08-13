package com.bizbump.utils;

import android.util.Log;

import java.util.ArrayList;

import com.bizbump.model.Card;

/**
 * Created by Shane on 8/13/13.
 */
public class CardUtils {

    public static void sort(ArrayList<Object> cards){
        CardUtils.removeSeperators(cards);
        Object temp;
        for (int i = 0;  i < cards.size() - 1;  i ++ ){
            for (int j = i + 1;  j < cards.size();  j ++ ){
                Card first = (Card) cards.get(i);
                Card second = (Card) cards.get(j);
                if (first.getFullName().compareToIgnoreCase(second.getFullName()) > 0){// ascending sort
                    Log.d("Sorting", "Swapping");
                    temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }

    public static void removeSeperators(ArrayList<Object> cards){
        for(int i = 0; i < cards.size(); i ++){
            if(!(cards.get(i) instanceof Card)){
                cards.remove(i);
            }
        }
    }

    public static void addSeperators(ArrayList<Object> cards){
        CardUtils.sort(cards);
        char letter = 0;
        for(int i = 0 ; i < cards.size() ; i ++){
            char c = Character.toLowerCase(((Card) cards.get(i)).getFullName().charAt(0));
            if(c > letter){
                cards.add(i, Character.toUpperCase(c) + "");
                letter = c;
            }
        }
    }

}
