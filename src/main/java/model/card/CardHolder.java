package model.card;

import java.util.*;

import controller.DuelController;
import model.duel.EffectParser;
import model.effect.EffectManager;
import model.user.Player;
import view.DuelMenu;




public abstract class CardHolder {
    protected int id;
    protected static int idCounter = 1;
    protected CardState cardState;
    protected CardType cardType;
    protected Boolean isEmpty;
    protected List <EffectManager> effectManagerList;        
    protected List <Integer> appliedEffects;       
    protected HashMap<String, String> cardMap = new HashMap<String, String>();
    protected HashMap<String, Integer> ageEffects = new HashMap<String, Integer>();
    protected HashMap<Event, List<EffectManager>> effects = new HashMap<Event, List<EffectManager>>();    
    protected Player owner;


    public HashMap<Event, List<EffectManager>> getEffects()
    {
        return this.effects;
    }
    
    public String getOwnerName()
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
        this.id = idCounter;   
        idCounter++;    
        this.appliedEffects = new ArrayList<Integer>();
        this.effectManagerList = new ArrayList<EffectManager>();        
        //TODO effectManager should be updated by creating effectManagerId        
    }
    public CardHolder(Player owner, CardState cardState) {
        this.owner = owner;
        this.cardState = cardState;
        id = idCounter;
        idCounter++;
        this.appliedEffects = new ArrayList<Integer>();
        this.effectManagerList = new ArrayList<EffectManager>();
    }    
    public abstract String toString();

    public void endTurn()
    {    
        for(Map.Entry<String, Integer> mapEntry : ageEffects.entrySet())
        {
            if(ageEffects.get(mapEntry.getKey()) == 0)
            {
                ageEffects.put(mapEntry.getKey(), null);
                cardMap.put(mapEntry.getKey(), null);
            }
            ageEffects.put(mapEntry.getKey(), mapEntry.getValue() - 1);
        }
    }
    
    public CardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
           
    public void setMapValue(String key, String value, Integer time)
    {
        cardMap.put(key, value);
        if(ageEffects.get(key) != null)
            ageEffects.put(key ,Math.max(ageEffects.get(key) , time));
        else
            ageEffects.put(key, time);
    }
    public abstract void flip();
    public String getValue(String string)
    {    
        if(cardMap.get(string) == null)
            return "";
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
            return Boolean.parseBoolean(cardMap.get(string));
    }

    public void prepareForDeath(DuelMenu duelMenu, DuelController duelController)
    {
        for(int i = 0; i < effects.get(Event.DEATH_OWNER).size(); i++)
        {
            if(effects.get(Event.DEATH_OWNER).get(i).isConditionSatisfied(new EffectParser(duelMenu, duelController, effects.get(Event.DEATH_OWNER).get(i))))
            {
                EffectParser temp = new EffectParser(duelMenu, duelController, effects.get(Event.DEATH_OWNER).get(i));
                temp.runEffect();
            }
        }
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
    
    protected void addEffectManager(EffectManager effectManager)
    {
        this.effectManagerList.add(effectManager);
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