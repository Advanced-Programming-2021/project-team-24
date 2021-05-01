package model.user;

import java.util.ArrayList;
import java.util.List;


import model.deck.*;
import programcontroller.Message;
import model.card.*;
import programcontroller.*;

public class User {
    private String nickname;
    private String username;
    private String password;
    private int score;
    private List<String> cardNames;
    private List<Card> cards;//fill it after reading json
    private static List<String> usernames;
    private List<Deck> decks;
    private Deck activeDeck;
    public String getNickname()
    {
        return this.nickname;
    }       
    public String getUsername()
    {
        return this.username;
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
        this.decks = new ArrayList<Deck>();
        this.activeDeck = null;
        //update usernames list        
        // add json file of user to directory
    }    
    
    
    public static void intialize()
    {
        // get list of all users from folder
        usernames = new ArrayList<String>();

    }
    public void logout()
    {
        //update json file of user
    }    
    private boolean changePassword(String oldPassword, String newPassword)
    {
        if(this.password.compareTo(oldPassword) == 0)
        {
            this.password = newPassword;
            return true;
        }
        else
            return false;
    }
    private static User readUser(String username)
    {
        //TODO reading by GSON
        return null;
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
    public void addDeck(String deckName)
    {
        //return Errors.DeckWithSameNameExitsts;        
    }
    public Deck getActiveDeck()
    {
        return this.activeDeck;
    }
    public void removeDeck(String deckName)
    {
        if(activeDeck != null)
        {
            if(activeDeck.getName().compareTo(deckName) == 0)
                activeDeck = null;
        }
        for(int i = 0; i < decks.size(); i++)
        {
            if(decks.get(i).getName().compareTo(deckName) == 0)
            {
                decks.remove(i);
                return ;
            }
        }
    }
    public int getScore()
    {
        return this.score;
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
                    return new Message(TypeMessage.ERROR, "user with nickname " + nickname + " already exists");
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
    public static User getUserByNameAndPassword(String username, String password)
    {
        User loging = readUser(username);
        if(loging == null)
        {
            return null;
        }
        else
        {
            if(loging.comparePassword(password))
            {
                return loging;
            }
            else
                return null;
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
    public Deck getDeckByName(String deckName)
    {
        for(int i = 0; i < decks.size(); i++)
        {
            if(decks.get(i).getName().compareTo(deckName) == 0)
            {
                return this.decks.get(i);
            }
        }
        return null;
    }

    //TODO
}
