package model.zone;

import model.user.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Zone {
    private String zone;
    private Player player;
    

    
    public Zone(String zone, Player player) {
        this.zone = zone;
        this.player = player;
    }



    public String getName() {
        return zone;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String zone() {
        return this.zone;
    }
   
}
