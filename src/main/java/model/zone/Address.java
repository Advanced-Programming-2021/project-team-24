package model.zone;


import model.user.Player;

import java.util.ArrayList;
import java.util.List;

public class Address {
    private String name;
    private Zone zone;
    private int place;
    private static List<Address> addresses = new ArrayList<>();

    public static void init(Player player) {
        for(Zones zone : Zones.values()){
            initZone(zone,player);
        }
    }

    private static void initZone(Zones zone,Player player) {
        for(int i=0;i<zone.capacity;i++){
            addresses.add(new Address(Zone.get(zone.label, player),i));
        }
    }

    public static Address get(Zone zone, int place) {
        for (Address address : addresses) {
            if (zone.equals(address.getZone()) && place==address.getPlace()) {
                return address;
            }
        }
        return null;
    }

    public Address(Zone zone, int place) {
        this.place = place;
        this.zone = zone;
    }

    //copy constructor
    public Address(Address address) {
        this.name = address.name;
        this.zone = address.zone;
        this.place = address.place;
    }

    public Address getNextPlace() {
        return get(zone,place+1);
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
