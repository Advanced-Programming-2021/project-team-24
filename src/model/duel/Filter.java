package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.card.CardHolder;
import model.card.CardType;

public class Filter {
    

    private int minLevel;
    private int maxLevel;
    private String cardType;
    private Integer minAttack;
    private Integer maxAttack;
    private Integer minDefence;
    private Integer maxDefence;
    private List<String> cardNames;
    private List<Integer> idCardHolder;    
    private List<String> zones;
    private String ownerName;
    
    public Filter(int cardHolderId, String ownerName)
    {
        this.idCardHolder = new ArrayList<Integer>();
        this.idCardHolder.add(cardHolderId);
        this.ownerName = ownerName;
    }
    
    public Filter(String cardName, List<String> zones, String ownerName)    
    {
        this.cardNames = new ArrayList<String>();
        this.cardNames.add(cardName);
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(List<String> zones, String ownerName)
    {
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(int minLevel, int maxLevel, String ownerName)
    {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.ownerName = ownerName;
    }
    public Filter(HashMap<String, Object> data, String ownerName)
    {
        this.ownerName = ownerName;
        //TODO others;
    }    
    public boolean Matches(CardHolder cardHolder)    
    {       
        boolean answer = true;         
        if(zones != null && zones.size() > 0)
        {

        }
                
        return answer;
    }
}
