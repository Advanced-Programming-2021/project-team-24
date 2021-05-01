package model.card;

import java.util.List;

public abstract class Card {
    private String name;
    private String description;
    private Integer score;
    private LimitType limitType;    
    public String getDescription;
    private static List<Card> allCards;
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
    public String getName()
    {
        return this.name;
    }
    public LimitType getLimitType()
    {
        return this.limitType;
    }
    public String getDescription()
    {
        return this.description;
    }
    public int getScore()
    {
        return this.score;
    }
    //TODO
}
