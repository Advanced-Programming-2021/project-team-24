package model.user;

import model.zones.Address;
import model.zones.DrawDeckZone;
import model.zones.FieldZone;
import model.zones.Graveyard;
import model.zones.MagicCardsZone;
import model.zones.MonsterZone;
import model.card.CardHolder;

public class Player {
    private User user;
    private int lifePoint;
    private Address selectedAddress;
    private Graveyard graveyard;
    private MonsterZone monsterZone;
    private MagicCardsZone magicCardsZone;
    private DrawDeckZone drawDeckZone;
    private FieldZone fieldZone;

    public Player(User user) {
        this.lifePoint = 8000;
        this.user = user;
        this.fieldZone = new FieldZone();
        this.drawDeckZone = new DrawDeckZone(user.getActiveDeck());
        this.graveyard = new Graveyard();
        this.magicCardsZone = new MagicCardsZone();
        this.monsterZone = new MonsterZone();
    }

    public void selectAddress(Address address) {
        this.selectedAddress = address;
    }

    public Address getSelectedAddress() {
        return this.selectedAddress;
    }

    public String getNickname() {
        return user.getNickname();
    }

    public void changeLifePoint(int change) {
        this.lifePoint += change;
    }

    public boolean isDead() {
        if (lifePoint <= 0)
            return true;
        else
            return false;
    }

}