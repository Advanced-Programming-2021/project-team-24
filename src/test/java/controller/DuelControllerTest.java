package controller;

import model.card.Card;
import model.duel.Duel;
import model.user.User;
import view.DuelMenu;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DuelControllerTest {
    @Test
    public void runPhaseTest(){
        User user1 = new User("testUser1", "1234", "qwer1");
        User user2 = new User("testUser2", "1234", "qwer1");        
        //Card.intialize();
        
        user1.getCards().addAll(Card.getAllCards());
        user2.getCards().addAll(Card.getAllCards());        
        System.out.println(user1.getCards().size());
        DuelMenu duelMenu = new DuelMenu(user1, user2, "1");

        DuelController duelController = duelMenu.getDuelController();
        Duel duel = duelController.getDuel();
        


        
        assertEquals(duelController.runPhase().getContent(), "STANDBY");
        // DRAW TEST
        User.deleteUser("user1");
        User.deleteUser("user2");
    }



}
