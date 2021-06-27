package model.duel.filterhandle;

import model.card.CardHolder;
import model.card.monster.MonsterCard;
import model.duel.Duel;
import model.card.*;
import model.duel.Filter;

public class MonsterTypeHandler extends FilterHandler{

    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getMonsterType() != null)
        {
            if(cardHolder.getCard().isMagic())
                return false;
            MonsterCard monster = (MonsterCard)cardHolder.getCard();
            if(!monster.getMonsterType().equals(filter.getMonsterType()))
                return false;
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
