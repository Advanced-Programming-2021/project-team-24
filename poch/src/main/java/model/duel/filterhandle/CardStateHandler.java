package model.duel.filterhandle;

import model.card.CardHolder;
import model.duel.Duel;
import model.duel.Filter;

public class CardStateHandler extends FilterHandler{

    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        int flag = 0;
        if(filter.getCardStates() != null)
        {
            for(int i = 0; i < filter.getCardStates().size(); i++)
            {
                if(filter.getCardStates().get(i) == cardHolder.getCardState())
                    flag = 1;
            }
            if(flag == 0)
                return false;
        }
        if(nextFilterHandler != null)
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        else
            return true;

    }
    
}
