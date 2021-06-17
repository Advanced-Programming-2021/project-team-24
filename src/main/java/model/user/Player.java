package model.user;

import model.card.CardHolder;
import model.card.CardState;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.zone.Address;

import java.util.List;


public class Player {
    private User user;
    private int lifePoint;
    private CardHolder card;
    private Address selectedAddress;
    private List<CardHolder> cardHolders;
    
    public Player(User user) {
        this.lifePoint = 8000;
        this.user = user;
        this.card = new MonsterCardHolder(this, new MonsterCard(), CardState.NONE);//TODO That's general Dictionary maybe fixed later
    }


    
    public CardHolder getMap()
    {
        return this.card;
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

    public CardHolder getCardHolderById(int id) {
        for (CardHolder cardHolder : cardHolders)
            if (cardHolder.getId() == id) 
                return cardHolder;
        return null;
    }

}