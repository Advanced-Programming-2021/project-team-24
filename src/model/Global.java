package model;


import java.util.List;

import model.card.Card;
import model.user.User;

public class Global {
    private static List<User> users;
    private static List<Card> cards;
    public List<Card> getCardsList()
    {
        return cards;
    }
    public List<User> getUsersList()
    {
        return users;
    }
    public Card getCardByName(String cardName)
    {
        Card ans = null;
        for(int i = 0; i < cards.size(); i++)
        {
            
        }
        return ans;
    }
    //TODO
}
