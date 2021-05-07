package model.zone;


public class Address {
    private String name;
    private Zone zone;
    private int place;
    public Address(Zone zone, int place)
    {
        this.place = place;
        this.zone = zone;
    }

    //copy constructor
    public Address(Address address){
        this.name = address.name;
        this.zone = address.zone;
        this.place = address.place;
    }

    public void plusplus(){
        this.place++;
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
