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
    public void plusplus(){
        this.place++;
    }
    public static void main(String[] args) {
        
    }
}
