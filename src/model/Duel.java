package model;

import model.card.CardHolder;
import model.effect.*;
import model.user.Player;
import model.user.User;


import java.util.List;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    private static List<EffectManager> effectManagerList;


    public static EffectManager getEffectManagerById(int id){
        for (int i = 0; i < effectManagerList.size(); i++) {
            if (effectManagerList.get(i).getId() == id) return effectManagerList.get(i);
        }
        return null;
    }
    public Duel(User user, User opponent){
        this.user = new Player(user);
        this.opponent = new Player(opponent);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void addEffectManager(EffectManager effectManager)
    {
        effectManagerList.add(effectManager);
    }
    public List<CardHolder> getAllCardHolder()
    {
        return null;    
    }

    public void removeEffect(int id)
    {
        List<CardHolder> allCard = this.getAllCardHolder();
        for(int i = 0; i < allCard.size(); i++)
        {
            allCard.get(i).removeEffect(id);            
        }
    }
}
