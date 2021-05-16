package model.duel.filterhandle;

import model.card.CardHolder;
import model.card.CardType;
import model.duel.Duel;
import model.duel.Filter;

public class CardTypeHandler extends FilterHandler{

    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        
        if(filter.getCardType() != null)
        {
            if(filter.getCardType() != cardHolder.getCardType())
            {
                if(!(filter.getCardType() == CardType.MAGIC && (cardHolder.getCardType() == CardType.SPELL || cardHolder.getCardType() == CardType.TRAP)))
                {
                    return false;
                }
            }
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
