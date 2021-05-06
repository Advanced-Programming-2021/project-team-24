package model;

import model.effect.EffectManger;
import model.user.Player;
import model.user.User;

import java.util.List;

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
    public void addEffect(int idEffect){

    }
    public void removeEffect(int idEffect){

    }
}
