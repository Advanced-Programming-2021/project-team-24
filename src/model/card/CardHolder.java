package model.card;

import java.util.*;

import model.user.User;




public abstract class CardHolder {
    protected int id;
    protected static int idCounter = 1;    
    protected Card card;                    
    protected CardState cardState;
    protected User owner;
    public String getOnwerName()
    {
        return this.owner.getNickname();
    }        
    public CardHolder(User owner)
    {        
        this.owner = owner;
        this.card = null;
        this.cardState = null;
        this.id = idCounter ++;        
        this.appliedEffects = new ArrayList<Integer>();
        this.effectsId = new ArrayList<Integer>();        
    }
    
    List <Integer> effectsId;        
    List <Integer> appliedEffects;
    
    public void makeEmpty()
    {
        this.card = null;
        this.cardState = null;
        this.id = idCounter ++;        
        this.effectsId = new ArrayList<Integer>();
        this.appliedEffects = new ArrayList<Integer>();
    }
    
    public CardHolder(Card card, CardState cardState)
    {
        this.card = card;
        this.cardState = cardState;        
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
    
    
    private CardState getPosition()
    {
        return this.cardState;
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
