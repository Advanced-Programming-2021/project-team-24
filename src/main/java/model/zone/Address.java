package model.zone;


import model.duel.Duel;
import model.user.Player;

import java.util.ArrayList;
import java.util.List;

public class Address {
    private Duel duel;    
    private Zone zone;
    private int place;


    public Address(Zone zone, int place, Duel duel) {
        this.place = place;
        this.zone = zone;
        this.duel = duel;
    }

    //copy constructor
    public Address(Address address) {
        this.zone = address.zone;
        this.place = address.place;
    }

    public Address getNextPlace() {
        return duel.duelAddresses.get(zone,place+1);
    }

    public int getPlace() {
        return place;
    }

    public Zone getZone() {
        return zone;
    }

    public static void main(String[] args) {

    }
}
