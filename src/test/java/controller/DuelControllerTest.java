package controller;

import model.duel.Duel;
import model.user.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DuelControllerTest {
    @Test
    public void runPhaseTest(){
        User user1 = new User("testUser1", "1234", "qwer1");
        User user2 = new User("testUser2", "1234", "qwer1");
        Duel duel = new Duel(user1, user2, "3");
        DuelController duelController = new DuelController(duel);
        assertEquals(duelController.runPhase().getContent(), "STANDBY");
        // DRAW TEST
        User.deleteUser("user1");
        User.deleteUser("user2");
    }



}
