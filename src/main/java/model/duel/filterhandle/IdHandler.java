package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.duel.Filter;

public class IdHandler extends FilterHandler {
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getIdCardHolder() != null)            
            if(filter.getIdCardHolder().size() > 0)
            {
                int flag = 0;
                for(int i = 0; i < filter.getIdCardHolder().size(); i++)
                {
                    if(Integer.parseInt(filter.getIdCardHolder().get(i)) == cardHolder.getId())
                        flag = 1;
                }            
                if(flag == 0)
                    return false;
            }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
