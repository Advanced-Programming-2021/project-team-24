package model.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.Excluder;

//import javafx.scene.image.Image;
import model.deck.*;
import controller.Message;
import model.card.*;
import controller.*;

public class User {
    private String nickname;
    private String username;
    private String password;
    private int score;
    private int coin;
    private String imageAddress;
    private List<String> cardNames;

   

    private transient List<Card> cards = new ArrayList<>();// fill it after reading json
    private Decks decks = new Decks();
    private static List<String> usernames = new ArrayList<>();
    static{
        //read users from json files in "/users" folder
        //initialize();
    }
    public List<String> getCardNames() {
        return this.cardNames;
    }

    public void setCardNames(List<String> cardNames) {
        this.cardNames = cardNames;
    }
    public String getNickname()
    {
        return this.nickname;
    }
    public String getUsername()
    {
        return this.username;
    }
    public int getScore(){
        return score;
    }
    public int getCoin(){
        return coin;
    }
    public static List<String> getUsernames(){
        return usernames;
    }

    public List<Card> getCards() {
        return cards;
    }
    public static List <Card> getMonsterCards(){
        List<Card> cards = new ArrayList<>();
        for(Card card : cards){
            if(!card.isMagic()) cards.add(card);
        }
        return cards;
    }
    public static List <Card> getMagicCards(){
        List<Card> cards = new ArrayList<>();
        for(Card card : cards){
            if(card.isMagic()) cards.add(card);
        }
        return cards;
    }

    

    public String getImageAddress() {
        return imageAddress;
    }

    
        
    public static User getUserByNameAndPassword(String username, String password) {
        User loging = readUser(username);
        if (loging == null) {
            return null;
        } else {
            if (loging.comparePassword(password)) {
                return loging;
            } else
                return null;
        }
    }

    public void logout()
    {
        //update json file of user
    }


    public static User readUser(String username)
    {
        try {
            File file = new File("users/"+username+".json");
            if (file.exists()) {
                String json = new String(Files.readAllBytes(Paths.get("users/" + username + ".json")));
                User temp = fillUserCards(json);
                return temp;
            }
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static User fillUserCards(String json) {
        User temp = new Gson().fromJson(json, User.class);
        temp.cards = new ArrayList<>();
        for(int i = 0; i < temp.cardNames.size(); i++)
        {
            temp.cards.add(Card.getCardByName(temp.cardNames.get(i)));
        }
        for(int j = 0; j < temp.decks.getDecks().size(); j++)
        {
            for(int i = 0; i < temp.getDecks().getDecks().get(j).getMainCardName().size(); i++)
            {
                temp.getDecks().getDecks().get(j).getMainCards().add(Card.getCardByName(temp.getDecks().getDecks().get(j).getMainCardName().get(i)));                        
            }
            for(int i = 0; i < temp.getDecks().getDecks().get(j).getSideCardName().size(); i++)
            {
                temp.getDecks().getDecks().get(j).getSideCards().add(Card.getCardByName(temp.getDecks().getDecks().get(j).getSideCardName().get(i)));
            }
        }
        return temp;
    }

    private boolean comparePassword(String password)
    {
        if(password.compareTo(this.password) == 0) return true;
        else
        {
            return false;
        }

    }

    
    public static void updateUser(User user){
        //user =  new updated one
    }

    
    

    public Decks getDecks() {
        updateUser(this);
        return decks;
    }
    //for test
    public static void deleteUser(String username){
        try {
            File file = new File("users/"+username+".json");
            if (file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}