package model.card;

import java.util.HashMap;

import model.user.User;


public class MonsterCardHolder extends CardHolder {
    //IDEA hashmap
    HashMap<String, Boolean> cardMap;// find good name
    
    private int age;
    private int attack;
    private int defence;
    public int getAttack()
    {
        return this.attack;
    }
    public int getDefence()
    {
        return this.defence;
    }
    public void flipSummon()
    {
        this.cardState = CardState.ATTACK_MONSTER;
    }
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

    public MonsterCardHolder(Card card, CardState cardState ,Player owner)
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
        if(this.cardState == CardState.SET_DEFENCE)
        {
            //run maybe some effect
            this.cardState = CardState.SET_DEFENCE;
        }
    }
    protected void recalculateEffect() {
        // TODO Auto-generated method stub        
    }
}
