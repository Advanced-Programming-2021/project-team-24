package model.zones;

import model.card.Card;

import java.util.HashMap;

public class Address {
    private String name;
    private Boolean opponent;
    private int place;
    public static HashMap<Address, Card> map;
    public Address(String name,Boolean opponent,int place){
        this.name = name;
        this.opponent = opponent;
        this.place = place;
    }
}
