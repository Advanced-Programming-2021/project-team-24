package model.card;

import java.util.*;

import model.user.Player;
import model.user.User;




public abstract class CardHolder {
    protected int id;
    protected static int idCounter = 1;                
    protected CardState cardState;
    protected CardType cardType;
    protected HashMap<String, String> cardMap;



    protected Player owner;
    public String getOnwerName()
    {
        return this.owner.getNickname();
    }        
    public CardHolder(Player owner)
    {        
        this.owner = owner;        
        this.cardState = null;
        this.id = idCounter ++;        
        this.appliedEffects = new ArrayList<Integer>();
        this.effectManagerId = new ArrayList<Integer>();
        //TODO effectManager should be updated by creating effectManagerId        
    }
    public CardHolder(MonsterCard card, CardState cardState) {
    }
    public CardHolder(MagicCard card, CardState cardState) {
    }
    public abstract void endPhase();
    private List <Integer> effectManagerId;        
    private List <Integer> appliedEffects;    
    protected abstract void recalculateEffect();    
    public abstract void makeEmpty();
    /*{
        this.cardState = null;
        this.id = idCounter ++;        
        this.effectManagerId = new ArrayList<Integer>();
        this.appliedEffects = new ArrayList<Integer>();
    }*/
    
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
    
    
    public boolean isEmpty()
    {
        if(this.getCard() == null)
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
    
    
    
    public abstract Card getCard();    
    
    public int getId()
    {
        return id;
    }
    public void setCardMap(HashMap<String, String> cardMap){
        this.cardMap = cardMap;
    }
    public static HashMap<String, String> getCardMap(){
        return cardMap;
    }
}
