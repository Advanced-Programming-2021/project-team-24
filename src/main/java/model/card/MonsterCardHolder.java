package model.card;

import java.util.HashMap;
import java.util.List;

import model.effect.EffectManager;
import model.user.Player;
import model.user.User;


public class MonsterCardHolder extends CardHolder {
    public MonsterCardHolder(Player owner, MonsterCard monsterCard, CardState cardState) {
        super(owner ,cardState);             
        this.card = monsterCard;
        this.onDeath = new EffectManager(card.onDeath, owner);
        cardMap.put("attack", ((Integer)monsterCard.getAttack()).toString());
        cardMap.put("defence",  ((Integer)monsterCard.getDefence()).toString());

    }
    private MonsterCard card;    
    private HashMap<Event, List<EffectManager>> effects = new HashMap<Event, List<EffectManager>>();     
    public List<EffectManager> getEventEffect(Event event)
    {
        return effects.get(event);
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
        if(!super.convertStringToBool(cardMap.get("can_change_state")))
            this.cardState = newCardState;
    }

    
    public void flip()
    {
        if(this.cardState == CardState.SET_DEFENCE)
        {
            this.cardState = CardState.SET_DEFENCE;
        }
    }
    public Card getCard() {        
        return this.card;
    }
}
