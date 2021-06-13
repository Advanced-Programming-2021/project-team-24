package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.card.CardType;
import model.card.monster.MonsterCardHolder;
import model.duel.Filter;

public class DefenceHandler extends FilterHandler {

    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        MonsterCardHolder monster = (MonsterCardHolder)cardHolder;
        if(filter.getMinDefence() != null)
        {
            if(cardHolder.getCard().getCardType() == CardType.MONSTER)
            {
                if(monster.getDefence() < filter.getMinDefence())   
                    return false;
            }
            else
                return false;            
        }
        if(filter.getMaxDefence() != null)
        {
            if(cardHolder.getCard().getCardType() == CardType.MONSTER)
            {
                if(monster.getDefence() > filter.getMaxDefence())
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
