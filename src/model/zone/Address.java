package model.zone;

public class Address {
    private Zone zone;
    private int place;
    public Address(Zone zone,int place){
        this.zone = zone;
        this.place = place;
    }
    public void plusplus(){
        this.place++;
    }
}
