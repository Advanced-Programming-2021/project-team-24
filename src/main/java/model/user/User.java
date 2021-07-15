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

import javafx.scene.image.Image;
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

    public User(String username, String password, String nickname)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.cards = new ArrayList<>();
        this.score = 8000;
        this.coin = 100000;        
        usernames.add(username);
        this.cardNames = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.imageAddress = "images/duel/characters/Chara001.dds"+ getRandomNumberInRange(1, 38) +".png";
        addUser();
    }

    public String getImageAddress() {
        return imageAddress;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void addUser(){
        try {
            File file = new File("users/"+this.username+".json");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);        
            fileWriter.write(new Gson().toJson(this));            
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialize()
    {
        // get list of all users from folder
        //??but what is the List<String> usernames purpose??
        usernames = new ArrayList<>();
        try {
            //"" means project directory
            File directoryPath = new File("users");
            File[] filesList = directoryPath.listFiles();
            assert filesList != null;
            for(File file : filesList) {
                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                User user = new Gson().fromJson(json, User.class);
                if(user != null)
                    usernames.add(user.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }     
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
    public boolean changePassword(String oldPassword, String newPassword)
    {
        if(this.password.compareTo(oldPassword) == 0)
        {
            this.password = newPassword;
            addUser();
            return true;
        }
        else
            return false;
    }


    public static User readUser(String username)
    {
        try {
            File file = new File("users/"+username+".json");
            if (file.exists()) {
                String json = new String(Files.readAllBytes(Paths.get("users/" + username + ".json")));
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
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean comparePassword(String password)

    {
        if(password.compareTo(this.password) == 0) return true;
        else
        {
            return false;
        }
    }
    public void addCard(Card newCard){
        cards.add(newCard);
        this.cardNames.add(newCard.getName());
        addUser();
    }

    public static Message register(String username, String password, String nickname)
    {
        User register = readUser(username);
        if(register == null)
        {
            for(int i = 0; i < usernames.size(); i++)
            {
                if(readUser(usernames.get(i)).getNickname().compareTo(nickname) == 0)
                {
                    return new Message(TypeMessage.SUCCESSFUL, "user with nickname " + nickname + " already exists");
                }
            }
            //add new user
            new User(username, password, nickname);
            return new Message(TypeMessage.SUCCESSFUL, "user created successfully!");
        }
        else
        {
            return new Message(TypeMessage.ERROR, "user with username " + username + " already exists");
        }
    }
    public static Message login(String username, String password)
    {
        User loging = readUser(username);
        if(loging == null)
        {
            return new Message(TypeMessage.ERROR, "Username and password didn’t match!");
        }
        else
        {
            if(loging.comparePassword(password))
            {
                return new Message(TypeMessage.SUCCESSFUL, "user logged in successfully!");
            }
            else
                return new Message(TypeMessage.ERROR, "Username and password didn’t match!");
        }
    }

    public void changeScore(int changeScore)
    {
        this.score += changeScore;
        addUser();
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
        addUser();
    }

    public Decks getDecks() {
        return decks;
    }
    public void setDecks(Decks decks){
        this.decks = decks;
        addUser();
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
    public void setCoin(int coin){
        this.coin = coin;
        addUser();
    }
    public void increaseCoin(int increment){
        this.coin += increment;
        addUser();
    }
}