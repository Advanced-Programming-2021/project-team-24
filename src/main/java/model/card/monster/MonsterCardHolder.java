package model.card.monster;

import java.util.HashMap;
import java.util.List;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;


public class MonsterCardHolder extends CardHolder {
    public MonsterCardHolder(Player owner, MonsterCard monsterCard, CardState cardState) {
        super(owner ,cardState);             
        this.card = monsterCard;
        cardMap.put("attack", ((Integer)monsterCard.getAttack()).toString());
        cardMap.put("defence",  ((Integer)monsterCard.getDefence()).toString());

    }
    private MonsterCard card;    
    
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
        if(!Boolean.parseBoolean(cardMap.get("can_change_state")))
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