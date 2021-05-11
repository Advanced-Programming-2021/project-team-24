package model.zone;

import model.user.Player;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    private String zone;
    private Player player;
    public static final String[] zoneStrings = {"graveyard","monster","spell","hand","field"};
    public Zone(String zone, Player player)
    {
        this.zone = zone;
        this.player = player;
    }

    public String getZoneName() {
        return zone;
    }

    public boolean isValid()
    {
        for (String zoneString : zoneStrings) {
            if(zoneString.compareTo(zone) == 0)
                return true;
        }
        return false;
    }

    public Player getPlayer()
    {
        return this.player;
    }
    public String zone()
    {
        return this.zone;
    }
    public static String[] getZoneStrings() {
        return zoneStrings;
    }
}
