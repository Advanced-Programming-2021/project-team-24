package model.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
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
    private List<String> cardNames;
    private List<Card> cards;//fill it after reading json
    private Decks decks;
    private static List<String> usernames;
    static{
        //read users from json files in "/users" folder
        initialize();
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

    private User(String username, String password, String nickname)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.cards = new ArrayList<Card>();
        this.score = 8000;
        usernames.add(username);
        this.cardNames = new ArrayList<String>();
        this.cards = new ArrayList<Card>();
        addUser();
    }    

    private void addUser(){
        try {
            FileWriter fileWriter = new FileWriter("/users/"+this.username+".json");
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
        usernames = new ArrayList<String>();
        try {
            //"" means project directory
            File directoryPath = new File("/users");
            File[] filesList = directoryPath.listFiles();
            assert filesList != null;
            for(File file : filesList) {
                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                User user = new Gson().fromJson(json,User.class);
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
            return true;
        }
        else
            return false;
    }


    public static User readUser(String username)
    {
        try {
            String json = new String(Files.readAllBytes(Paths.get("/users/"+username+".json")));
            return new Gson().fromJson(json,User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean comparePassword(String password)

    {
        if(password.compareTo(this.password) == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void addCard(Card newCard){
        cards.add(newCard);
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
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public Decks getDecks() {
        return decks;
    }
}
