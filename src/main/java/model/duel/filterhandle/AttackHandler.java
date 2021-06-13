package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.card.CardType;
import model.card.monster.MonsterCardHolder;
import model.duel.Filter;

public class AttackHandler extends FilterHandler {

    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        MonsterCardHolder monster = (MonsterCardHolder)cardHolder;
        if(filter.getMinAttack() != null)
        {
            if(cardHolder.getCard().getCardType() == CardType.MONSTER)
            {
                if(monster.getAttack() < filter.getMinAttack())   
                    return false;
            }
            else
                return false;            
        }
        if(filter.getMaxAttack() != null)
        {
            if(cardHolder.getCard().getCardType() == CardType.MONSTER)
            {
                if(monster.getAttack() > filter.getMaxAttack())
                    return false;
            }
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
