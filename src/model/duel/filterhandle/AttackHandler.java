package model.duel.filterhandle;

import model.Duel;
import model.card.CardHolder;
import model.duel.Filter;

public class AttackHandler extends FilterHandler {

    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getMinAttack() != null)
        {
            
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
