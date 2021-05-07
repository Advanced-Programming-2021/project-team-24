package model.card;

import org.junit.jupiter.api.Test;
import java.util.*;

import model.user.Player;
import model.user.User;




public abstract class CardHolder {
    protected int id;
    protected static int idCounter = 1;    
    protected Card card;                    
    protected CardState cardState;
    protected CardType cardType;
    protected Player owner;
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
        this.effectManagerId = new ArrayList<Integer>();
        //TODO effectManager should be updated by creating effectManagerId        
    }

    public abstract void endPhase();
    private List <Integer> effectManagerId;        
    private List <Integer> appliedEffects;    
    protected abstract void recalculateEffect();    
    public void makeEmpty()
    {
        this.card = null;
        this.cardState = null;
        this.id = idCounter ++;        
        this.effectManagerId = new ArrayList<Integer>();
        this.appliedEffects = new ArrayList<Integer>();
    }
    
    public boolean haveEffectWithId(int idEffectManager)
    {
        for(int i = 0; i < appliedEffects.size(); i++)
        {
            if(this.appliedEffects.get(i) == idEffectManager)
                return true;
        }
        return false;        
    }
    
    public void addEffect(int idEffectManager)
    {
        if(haveEffectWithId(idEffectManager) == false)
        {
            appliedEffects.add(idEffectManager);
        }
    }
    
    public void removeEffect(int idEffectManager)
    {
        for(int i = 0; i < appliedEffects.size(); i++)
        {
            if(appliedEffects.get(i) == idEffectManager)
            {
                appliedEffects.remove(idEffectManager);                
                recalculateEffect();
            }
        }        
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
    
    
    public CardState getCardState()
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
