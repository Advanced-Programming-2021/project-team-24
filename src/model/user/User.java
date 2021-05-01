package model.user;

import java.util.ArrayList;
import java.util.List;

//import com.google.gson;
import model.*;

import model.deck.*;
import model.card.*;

public class User {
    private String nickname;
    private String username;
    private String password;
    private int score;
    private List<String> cardNames;
    private List<Card> cards;//fill it after reading json

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
        //TODO add this file to directory
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
    private User readUser(String username)
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
    public static LoginMenuMessage register(String username, String password, String nickname)
    {
        return null;
    }
    public static User login(String username, String password, String nickname)
    {
        return null;
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
