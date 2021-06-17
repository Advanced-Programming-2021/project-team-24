package model.zone;

import model.user.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Zone {
    private String zone;
    private Player player;
    public static final String[] zoneStrings = {"graveyard", "monster", "spell", "hand", "field", "deck"};
    private static List<Zone> zones = new ArrayList<>();

    public static void init(Player player) {
        for (String zoneName : zoneStrings) {
            zones.add(new Zone(zoneName, player));
        }
    }

    public static Zone get(String zoneName, Player player) {
        for (Zone zone : zones) {
            if (zone.getName().equals(zoneName) && zone.getPlayer().equals(player)){
                return zone;
            }
        }
        return null;
    }

    public Zone(String zone, Player player) {
        this.zone = zone;
        this.player = player;
    }



    public String getName() {
        return zone;
    }

    public boolean isValid() {
        for (String zoneString : zoneStrings) {
            if (zoneString.compareTo(zone) == 0)
                return true;
        }
        return false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String zone() {
        return this.zone;
    }

    public static String[] getZoneStrings() {
        return zoneStrings;
    }
}
