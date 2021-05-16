package model.card;

import java.util.ArrayList;
import java.util.List;

import model.effect.Effect;


public abstract class Card {
    protected String name;
    protected String description;    
    protected Integer price;
    protected LimitType limitType;
    protected CardType cardType;    
    protected Effect onDeath;

    
    static List <Card> allCards = new ArrayList<Card>();
    public static List <Card> getAllCards()
    {
        return allCards;
    }
    public static void intialize()
    {
        // TODO add all cards, parse from json to Card       
    }
    public static Card getCardByName(String cardName)
    {
        for(int i = 0; i < allCards.size(); i++)
        {
            if(allCards.get(i).getName().compareTo(cardName) == 0)
                return allCards.get(i);
        }
        return null;
    }
    public CardType getCardType()
    {
        return this.cardType;
    }
    public String getName()
    {
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
    public LimitType getLimitType()
    {
        return this.limitType;
    }
    public String getDescription()
    {
        return this.description;
    }
    public boolean isMagic()
    {
        if(this.cardType == CardType.MONSTER)
            return false;
        else
            return true;
    }
    //TODO
}
