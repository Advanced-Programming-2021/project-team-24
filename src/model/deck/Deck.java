package model.deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private List<String> cardNames;    
    public Deck(String name)
    {
        this.name = name;
        this.cardNames = new ArrayList<String>();
    }
    public String getName()
    {
        return this.name;
    }
    public List<String> getCardList()
    {
        return this.cardNames;
    }
    public int getCountOfCard(String name)
    {
        for(int i = 0; i < cardNames.size(); i++)
        {
            if(name.compareTo(name) == 0)
            {

            }
        }
        return 0;
    }
}
