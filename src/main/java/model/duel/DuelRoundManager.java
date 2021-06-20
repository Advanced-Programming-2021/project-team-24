package model.duel;

import model.deck.Deck;
import model.deck.Decks;
import model.user.Player;
import model.user.User;
import view.DuelMenu;
import view.MainMenu;

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
                //go to main menu
                new MainMenu(user).run();
            }
            else {
                //TODO Side Deck
            }
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
}

