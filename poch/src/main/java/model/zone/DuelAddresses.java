package model.zone;

import java.util.ArrayList;
import java.util.List;

import model.duel.Duel;
import model.user.Player;

public class DuelAddresses {
    private List<Address> addresses = new ArrayList<>();
    private Duel duel;
    public DuelAddresses(Duel duel)
    {
        this.duel = duel;
        init(duel.getCurrentPlayer(), duel);
        init(duel.getOpponent(), duel);
    }

    public void init(Player player, Duel duel) {
        for(ZonesName zone : ZonesName.values()){
            initZone(zone,player);
        }
    }
    private void initZone(ZonesName zoneName, Player player) {
        for(int i=0;i<zoneName.capacity;i++){
            addresses.add(new Address(duel.duelZones.get(zoneName.label, player),i, duel));
        }
    }


    public Address get(Zone zone, int place) {
        for (Address address : addresses) {
            if (zone.getName().equals(address.getZone().getName()) && zone.getPlayer().getNickname().equals(address.getZone().getPlayer().getNickname())  &&  place == address.getPlace()){
                return address;
            }
        }
        return null;    
    }   
}
