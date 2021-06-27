package model.card.monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;


public class MonsterCardHolder extends CardHolder {
    public MonsterCardHolder(Player owner, MonsterCard monsterCard, CardState cardState) {        
        super(owner ,cardState);             
        if(cardState.equals(CardState.SPECIAL_SUMMON))
        {
            this.isSpecialSummoned = true;            
        }
        this.card = monsterCard;
        for(Map.Entry<Event, String> b : monsterCard.getEffects().entrySet())
        {
            List<EffectManager> c = new ArrayList<EffectManager>();
            c.add(new EffectManager(new Effect((String)b.getValue()), owner, getId()));
            effects.put((Event)b.getKey(), c);
        }
        
        cardMap.put("attack", ((Integer)monsterCard.getAttack()).toString());
        cardMap.put("defence",  ((Integer)monsterCard.getDefence()).toString());
        cardMap.put("level", ((Integer)monsterCard.getLevel()).toString());
        ageEffects.put("attack", 10000);
        ageEffects.put("level", 10000);
        ageEffects.put("defence", 10000);
        

    }
    
    private boolean isSpecialSummoned;

    

    private MonsterCard card;    
    
    public List<EffectManager> getEventEffect(Event event)
    {
        return effects.get(event);
    }
    public boolean isIsSpecialSummoned() {
        return this.isSpecialSummoned;
    }

    public void setIsSpecialSummoned(boolean isSpecialSummoned) {
        this.isSpecialSummoned = isSpecialSummoned;
    }
    public int getAttack()
    {
        return Integer.parseInt(getValue("attack"));
    }
    public int getDefence()
    {
        return Integer.parseInt(getValue("defence"));
    }
    public void flipSummon()
    {
        this.cardState = CardState.ATTACK_MONSTER;
    }

    
    
    public void changeCardState(CardState newCardState)
    {
        if(!Boolean.parseBoolean(cardMap.get("can_change_state")))
            this.cardState = newCardState;
    }

    
    public void flip()
    {
        if(this.cardState == CardState.SET_DEFENCE)
        {
            this.cardState = CardState.DEFENCE_MONSTER;
        }
    }
    public Card getCard() {        
        return this.card;
    }
    @Override
    public String toString() {
        String ans = "";
        ans = ans.concat("Name :" + card.getName() + "\n");
        ans = ans.concat("Monster\n");
        ans = ans.concat("Type: " +  card.getMonsterType().toString() + "\n");
        ans = ans.concat("ATK :" + getAttack() + "\n");
        ans = ans.concat("DEF :" + getDefence() + "\n");
        ans = ans.concat("Description: " + card.getDescription() + "\n");                
        return ans;
    }
}