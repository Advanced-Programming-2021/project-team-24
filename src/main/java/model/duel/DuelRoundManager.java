package model.duel;

import model.user.Player;
import model.user.User;
import view.DuelMenu;

public class DuelRoundManager {
    private User user;
    private User opponent;
    private Player userPlayer;
    private Player opponentPlayer;
    private int rounds = 0;
    public DuelRoundManager(User user, User opponent, int rounds)
    {
        this.user = user;
        this.opponent = opponent;
        this.userPlayer = new Player(user);
        this.opponentPlayer = new Player(opponent);
        this.rounds = rounds;
        run();
    }

    private void run() {
        for(int i = 0; i < rounds; i++)
        {
            //check who is starter with some coin or ??
            DuelMenu c = new DuelMenu(userPlayer, opponentPlayer);
            c.run();
            //c.getPlayer(isOpponent) check who is loser
            //  calculate scores
            // save scores
            //side deck
            DuelMenu duelMenu = new DuelMenu(userPlayer, opponentPlayer);

        }
    }

    private boolean isLoser(Player player){
        if (player.getIsDeadRounds() > rounds/2) return true;
        return false;
    }
    private void calculateCoin(Player player){
        if (isLoser(player)){
            player.getUser().increaseCoin(rounds * 100);
        }
        else player.getUser().changeScore(rounds * (1000 + player.getMaxLifePoint()));
    }
    private void calculateScore(Player player){
        if (!isLoser(player)) player.getUser().changeScore(rounds * 1000);
    }
    private boolean isRoundFinished(){
        if (userPlayer.isDead()){
            opponentPlayer.setMaxLifePoint();
            userPlayer.setLifePoint(8000);
            opponentPlayer.setLifePoint(8000);
            return true;
        }
        else if (opponentPlayer.isDead()){
            userPlayer.setMaxLifePoint();
            userPlayer.setLifePoint(8000);
            opponentPlayer.setLifePoint(8000);
            return true;
        }
        return false;
    }
}

