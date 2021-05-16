package model.card;

import java.util.*;

import model.user.Player;
import model.user.User;




public abstract class CardHolder {
    protected int id;
    protected static int idCounter = 1;
    protected CardState cardState;
    protected CardType cardType;
    protected List <Integer> effectManagerId;        
    protected List <Integer> appliedEffects;    
    protected abstract void recalculateEffect(); 
    protected HashMap<String, String> cardMap = new HashMap<String, String>();
    protected Player owner;

    
    public String getOnwerName()
    {
        return this.owner.getNickname();
    }        
    public Player getOwner()
    {
        return this.owner;
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
    public CardHolder(CardState cardState) {
        this.cardState = cardState;
    }
    public static void main(String[] args) {
        
    }
    public void endPhase()
    {
        List<String> v = (new ArrayList<String>());
        for(int i = 0; i < v.size(); i++)
        {

        }
    }
    
       
    public abstract void makeEmpty();
    /*{
        this.cardState = null;
        this.id = idCounter ++;        
        this.effectManagerId = new ArrayList<Integer>();
        this.appliedEffects = new ArrayList<Integer>();
    }*/
    public void setMapValue(String key, String value)
    {
        cardMap.put(key, value);
    }
    protected Boolean convertStringToBool(String string)
    {
        if(string.equals("true"))
            return true;
        else
        if(string.equals("false"))
            return false;
        return null;
    }
    public abstract void flip();
    public String getValue(String string)
    {    
        return cardMap.get(string);
    }
    public Boolean getBoolMapValue(String string)
    {
        if(cardMap.get(string) == null)
            {
                if(string.substring(0, 3).equals("can"))
                    return true;
                return false;
            }
        else
            return convertStringToBool(cardMap.get(string));
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
    public HashMap<String, String> getCardMap(){
        return this.cardMap;
    }
}
