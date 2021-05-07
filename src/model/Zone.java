package model;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    private String zone;
    private boolean opponent;
    private List<String> zoneStrings = new ArrayList<String>();
    public Zone(String zone, boolean opponent)
    {
        this.zone = zone;
        this.opponent = opponent;
        zoneStrings.add("graveyard");
        zoneStrings.add("monster");
        zoneStrings.add("spell");
        zoneStrings.add("hand");    
        zoneStrings.add("field");        
    }   
    public boolean isValid()
    {
        for (String zoneString : zoneStrings) {
            if(zoneString.compareTo(zone) == 0)
                return true;
        }
        return false;
    } 
    public boolean getOpponent()
    {
        return this.opponent;
    }
    public String zone()
    {
        return this.zone;
    }    
}
