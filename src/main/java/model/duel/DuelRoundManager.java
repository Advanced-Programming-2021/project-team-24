package model.duel;

import model.user.Player;
import model.user.User;
import view.DeckMenu;
import view.DuelMenu;
import view.MainMenu;
import view.SideDeckMenu;

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
    }

    public void run() {
        for(int i = 0; i < rounds; i++)
        {
            //check who is starter with some coin or ??
            //c.getPlayer(isOpponent) check who is loser
            //  calculate scores
            // save scores

            DuelMenu duelMenu = new DuelMenu(userPlayer, opponentPlayer);
            duelMenu.run();
            userPlayer.resetPlayerForNextRound();
            opponentPlayer.resetPlayerForNextRound();
            if (isLoser(userPlayer) || isLoser(opponentPlayer)){
                calculateCoin(userPlayer);
                calculateCoin(opponentPlayer);
                calculateScore(userPlayer);
                calculateScore(opponentPlayer);
                new MainMenu(user).run();
            }
            else {
                new SideDeckMenu(user).run();
            }
        }
    }

    public boolean isLoser(Player player){
        if (player.getIsDeadRounds() > (rounds/2)) return true;
        return false;
    }
    public void calculateCoin(Player player){
        if (isLoser(player)){
            player.getUser().increaseCoin(rounds * 100);
        }
        else player.getUser().increaseCoin(rounds * (1000 + player.getMaxLifePoint()));
    }
    public void calculateScore(Player player){
        if (!isLoser(player)) player.getUser().changeScore(rounds * 1000);
    }
}

