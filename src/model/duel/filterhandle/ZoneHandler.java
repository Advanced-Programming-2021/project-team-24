package model.duel.filterhandle;

import java.util.HashMap;

import model.Duel;
import model.card.CardHolder;
import model.duel.Filter;
import model.zone.Zone;
import view.Global;

public class ZoneHandler extends FilterHandler {
    
    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        Zone zone = duel.getCardHolderZone(duel.getCardHolderById(cardHolder.getId()));
        if(filter.getZones() != null)
        {
            
            //TODO
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
