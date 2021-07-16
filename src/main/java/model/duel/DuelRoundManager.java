package model.duel;

import model.user.Player;
import model.user.User;
import sample.Common;
import sample.DuelController;
import sample.MenuController;
import view.DeckMenu;
import view.DuelMenu;
import view.MainMenu;
import view.SideDeckMenu;

import java.io.IOException;

public class DuelRoundManager {
    private User user;
    private User opponent;
    private Player userPlayer;
    private Player opponentPlayer;
    private int rounds;
    private int round=1;
    public DuelRoundManager(User user, User opponent, int rounds)
    {
        this.user = user;
        this.opponent = opponent;
        this.userPlayer = new Player(user);
        this.opponentPlayer = new Player(opponent);
        this.rounds = rounds;
    }

    public void nextRound() {
        //for(int i = 0; i < rounds; i++) {
            //check who is starter with some coin or ??
            //c.getPlayer(isOpponent) check who is loser
            //  calculate scores
            // save scores

//            DuelMenu duelMenu = new DuelMenu(userPlayer, opponentPlayer);
//            duelMenu.run();
            if(round==rounds){
                endGame();
            }
            else {
                round++;
                userPlayer.resetPlayerForNextRound();
                opponentPlayer.resetPlayerForNextRound();
                if (isLoser(userPlayer) || isLoser(opponentPlayer)) {
                    endGame();
                } else {
                    //new SideDeckMenu(user).run();
                    //new MenuController(user).switchToSceneDuel();
                    System.out.println("go to next round!!");
                }
            }
    }

    private void endGame() {
        calculateCoin(userPlayer);
        calculateCoin(opponentPlayer);
        calculateScore(userPlayer);
        calculateScore(opponentPlayer);
        try {
            Common.switchToSceneMainMenu(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new MainMenu(user).run();
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

