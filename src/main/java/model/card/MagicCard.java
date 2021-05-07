package model.card;

import java.util.List;

import model.effect.*;



public class MagicCard extends Card {
    
    
    
    private boolean isActived;
    private List<Effect> effects;    
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
