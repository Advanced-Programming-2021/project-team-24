package model.card;

import java.util.HashMap;

import model.user.Player;
import model.user.User;


public class MonsterCardHolder extends CardHolder {
    public MonsterCardHolder(MonsterCard card, CardState cardState) {
        super(card, cardState);
        //TODO Auto-generated constructor stub
    }
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
