package model.card;


import java.util.*;



public abstract class CardHolder {
    private int id;
    private static int idCounter = 0;    
    private Card card;    
    
    
    
    
    private Position position;
    
    
        
    public CardHolder()
    {
        this.card = null;
        this.position = null;
        this.id = idCounter ++;        
        this.appliedEffects = new ArrayList<Integer>();
        this.effectsId = new ArrayList<Integer>();        
    }
    
    List <Integer> effectsId;        
    List<Integer> appliedEffects;
    
    public void makeEmpty()
    {
        this.card = null;
        this.position = null;
        this.id = idCounter ++;        
        this.effectsId = new ArrayList<Integer>();
        this.appliedEffects = new ArrayList<Integer>();
    }
    
    public CardHolder(Card card, Position position)
    {
        this.card = card;
        this.position = position;        
    }
    
    
    public boolean isEmpty()
    {
        if(card == null)
        {
            return true;
        }
        else
            return false;        
    }
    
    
    private Position getPosition()
    {
        return this.position;
    }
    
    
    
    public Card getCard()
    {
        return this.card;
    }
    
    public int getId()
    {
        return id;
    }
        
}
