package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.duel.Filter;

public class CardNameHandler extends FilterHandler {

    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getCardNames() != null && filter.getCardNames().size() > 0)
        {
            for(int i = 0; i < filter.getCardNames().size(); i++)
            {
                if(filter.getCardNames().get(i).equals(cardHolder.getCard().getName()))
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
