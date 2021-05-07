package model.zone;



import model.card.Card;

import java.util.HashMap;

public class Address {
    private String name;
    private Boolean opponent;
    private int place;
    public Address(String name, Boolean opponent, int place){
        this.name = name;
        this.opponent = opponent;
        this.place = place;
    }
    public Address(Zone zone, int place)
    {
        this.name = zone.getName();
        this.place = place;
        this.opponent = zone.getOpponent();
    }
    public void plusplus(){
        this.place++;
    }
    public static void main(String[] args) {
        
    }
}
