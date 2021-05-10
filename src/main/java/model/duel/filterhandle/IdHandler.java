package model.duel.filterhandle;

import model.Duel;
import model.card.CardHolder;
import model.duel.Filter;

public class IdHandler extends FilterHandler {
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getIdCardHolder().size() > 0)
        {
            for(int i = 0; i < filter.getIdCardHolder().size(); i++)
            {
                if(filter.getIdCardHolder().get(i) == cardHolder.getId())
                {
                    return true;
                }
            }            
            return false;
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
