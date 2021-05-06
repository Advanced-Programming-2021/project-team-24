package model;

import model.user.Player;
import model.user.User;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    public Duel(User user, User opponent){
        this.user = new Player(user);
        this.opponent = new Player(opponent);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
