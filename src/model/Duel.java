package model;

import model.card.Card;
import model.card.CardHolder;
import model.user.Player;
import model.user.User;
import model.zones.Address;

import java.util.HashMap;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    public HashMap<Address, CardHolder> map;
    public Duel(User user, User opponent){
        this.user = new Player(user);
        this.opponent = new Player(opponent);
    }

    public HashMap<Address, CardHolder> getMap() {
        return this.map;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
