package model.duel.zone;

import model.user.Player;
import model.card.CardHolder;
import model.card.Card;


import java.util.*;

public abstract class Zone {
    private Player owner;
    public Player getOwner()
    {
        return this.owner;
    }
    public abstract boolean isFull();
    public abstract void addCard(Card card);
    
    public abstract void removeCardHolderById(int id);   
    public abstract List<CardHolder> getListOfCard();  
}
