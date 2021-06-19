package model.duel;

import model.user.User;
import view.DuelMenu;

public class DuelRoundManager {
    public DuelRoundManager(User a, User b, int numberOfMatches)    
    {
        for(int i = 0; i < numberOfMatches; i++)
        {
            //check who is starter with some coin or ??            
            DuelMenu c = new DuelMenu(a, b);
            c.run();
            //c.getPlayer(isOpponent) check who is loser 
            //  calculate scores
            // save scores
            //side deck
        }
    }
}
