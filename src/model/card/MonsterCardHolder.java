package model.card;

import java.util.HashMap;

import model.user.User;


public class MonsterCardHolder extends CardHolder {
    //IDEA hashmap
    HashMap<String, Boolean> cardMap;// find good name
    private boolean canChangeCardStateInRound;
    private int age;
    
    public void endPhase()
    {
        this.age ++;
        cardMap.put("can_change_state", false);
    }

    public boolean getMapValue(String string)
    {
        if(cardMap.get(string) == null)
            return false;
        else
            return cardMap.get(string);
    }
    
    public void changeCardState(CardState newCardState)
    {
        if(!cardMap.get("can_change_state"))
            this.cardState = newCardState;
    }

    public MonsterCardHolder(Card card, CardState cardState , User owner)
    {
        this.age = 0;    
        cardMap.put("can_change_state", true);
        this.owner = owner;
        this.card = card;
        this.cardState = cardState;
    }
    //TODO run hashmap or no

    public void flip()
    {
        if(this.cardState = CardState.SET_DEFENCE && cardMap.get("can_change_state"))
        {
            //run maybe some effect
            this.cardState = CardState.ATTACK_MONSTER;
        }
    }
    protected void recalculateEffect() {
        // TODO Auto-generated method stub        
    }
}
