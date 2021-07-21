package model.zone;

import java.util.ArrayList;
import java.util.List;

import model.duel.Duel;
import model.user.Player;

public class DuelZones {
    public static final String[] zoneStrings = {"graveyard", "monster", "magic", "hand", "field", "deck"};
    private List<Zone> zones = new ArrayList<>();
    Duel duel;
    public DuelZones(Duel duel){
        this.duel = duel;
        init(duel.getCurrentPlayer());
        init(duel.getOpponent());
    }   
    public Zone get(String zoneName, Player player)
    {
        for (Zone zone : zones) {
            if (zone.getName().equals(zoneName) && zone.getPlayer().equals(player)){
                return zone;
            }
        }
        return null;
    }
    public void init(Player player) {
        for (String zoneName : zoneStrings) {
            zones.add(new Zone(zoneName, player));
        }
    }
    public List<Zone> getAllZones()
    {
        return this.zones;
    }

}
