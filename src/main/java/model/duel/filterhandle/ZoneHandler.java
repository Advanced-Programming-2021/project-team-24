package model.duel.filterhandle;

import java.util.List;

import model.duel.Duel;
import model.card.CardHolder;
import model.duel.Filter;
import model.zone.Zone;

public class ZoneHandler extends FilterHandler {
    
    @Override
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        Zone zone = duel.getCardHolderZone(duel.getCardHolderById(cardHolder.getId()));
        List<String> possibleZones = filter.getZones();
        int flag = 0;
        if(filter.getZones() != null)
        {
            for(int j = 0; j < possibleZones.size(); j++)
            {
                Zone currentPossibleZone = duel.parseZone(possibleZones.get(j), filter.getOwnerName());
                if(zone.equals(currentPossibleZone))
                    flag = 1;
            }
        }
        if(flag == 0)
            return false;
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
