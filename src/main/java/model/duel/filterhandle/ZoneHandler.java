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
        int flag = -1;
        if(filter.getZones() != null)
        {
            flag = 0;
            for(int j = 0; j < possibleZones.size(); j++)
            {
                Zone currentPossibleZone = duel.parseZone(possibleZones.get(j), filter.getOwnerName());
                if(zone.equals(currentPossibleZone))
                    flag = 1;
            }
        }
        else
        {
            if(zone.getName().equals("deck") || zone.getName().equals("graveyard"))
            {
                IdHandler id = new IdHandler();
                if(id.singleHandle(filter, cardHolder, duel)) 
                {
                    flag = 1;
                }
                else
                flag = 0;
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
