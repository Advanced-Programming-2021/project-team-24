package model.user;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.deck.Deck;
import model.zone.Address;

import java.util.List;


public class Player {
    private User user;
    private int lifePoint;
    private int isDeadRounds;
    private int maxLifePoint = 0;
    private CardHolder card;
    private Address selectedAddress;
    private Deck deck;
    
    public Player(User user) {
        this.lifePoint = 8000;
        this.isDeadRounds = 0;
        this.user = user;
        this.card = new MonsterCardHolder(this, new MonsterCard(), CardState.NONE);//TODO That's general Dictionary maybe fixed later
        this.deck = user.getDecks().getActiveDeck();
    }
    public void resetPlayerForNextRound()
    {
        card = new MonsterCardHolder(this, new MonsterCard(), CardState.NONE);
        selectedAddress = null;
        this.setMaxLifePoint();
        this.setLifePoint(8000);
    }
    
    public User getUser(){
        return user;
    }
    public boolean canChangeCards(Card main, Card side){
        if (deck.doesContainCard(main, true) && deck.doesContainCard(side, false)) return true;
        return false;
    }
    public void setLifePoint(int lifePoint){
        this.lifePoint = lifePoint;
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


    public int getLifePoint()
    {
        return this.lifePoint;
    }

    public boolean isDead() {
        if (lifePoint <= 0) {
            isDeadRounds++;
            return true;
        }
        return false;
    }

    public void setMaxLifePoint(){
        if (maxLifePoint < lifePoint) maxLifePoint = lifePoint;
    }
    public int getMaxLifePoint(){
        return maxLifePoint;
    }
    public int getIsDeadRounds(){
        return isDeadRounds;
    }
    public void setIsDeadRounds(int isDeadRounds){
        this.isDeadRounds = isDeadRounds;
    }


}