package model.duel.filterhandle;

import model.card.CardHolder;
import model.duel.Duel;
import model.duel.Filter;

public class CardStateHandler extends FilterHandler{

    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        
        for(int i = 0; i < filter.getCardStates().size(); i++)
        {
            if(filter.getCardStates().get(i) == cardHolder.getCardState())
                return true;
        }
        return false;
    }
    
}
