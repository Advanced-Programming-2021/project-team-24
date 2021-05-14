package model.card;

import java.util.HashMap;


import model.user.Player;
import model.user.User;


public class MonsterCardHolder extends CardHolder {
    public MonsterCardHolder(MonsterCard monsterCard, CardState cardState) {
        super(cardState);                
        //do in default
        this.card = monsterCard;
        cardMap.put("attack", ((Integer)monsterCard.getAttack()).toString());
        cardMap.put("defence",  ((Integer)monsterCard.getDefence()).toString());
        //TODO Auto-generated constructor stub
    }
    private MonsterCard card;    
    private HashMap<Event, String> effects;        
    public String getEventEffect(Event event)
    {
        return effects.get(event);
    }
    private int age;
    
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
    public void endPhase()
    {
        this.age ++;
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
            //run maybe some effect
            this.cardState = CardState.SET_DEFENCE;
        }
    }
    protected void recalculateEffect() {
        // TODO Auto-generated method stub        
    }
    public Card getCard() {        
        return this.card;
    }
    @Override
    public void makeEmpty() {
        // TODO Auto-generated method stub
        
    }
}
