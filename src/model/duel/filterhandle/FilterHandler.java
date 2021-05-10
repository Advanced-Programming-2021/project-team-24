package model.duel.filterhandle;

import model.Duel;
import model.card.CardHolder;
import model.duel.Filter;

public abstract class FilterHandler {
    public abstract boolean Handle(Filter filter, CardHolder cardHolder, Duel duel);
    
    protected FilterHandler nextFilterHandler;
    

    public void setNextFilterHandler(FilterHandler nextFilterHandler) {
        this.nextFilterHandler = nextFilterHandler;
    }

}
