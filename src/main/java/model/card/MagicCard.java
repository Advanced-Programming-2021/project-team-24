package model.card;

import java.util.List;

import model.effect.*;



public class MagicCard extends Card {
     
    
    private Boolean isActived;
    private Effect effect;  
    private Integer speed; 
    public Integer getSpeed()
    {
        return this.speed;
    }
    public boolean getIsActivated()
    {        
        return isActived;
    }
    public boolean isSpell()
    {
        if(super.getCardType() == CardType.SPELL)
            return true;
        else
            return false;
    }
}