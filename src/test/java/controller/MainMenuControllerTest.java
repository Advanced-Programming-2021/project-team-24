package controller;

import model.user.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class MainMenuControllerTest {
    @Test
    public void createDuelNoPlayerExistTest(){
        User user1 = new User("user1", "123", "qqq");
        assertEquals("there is no player with this username", new MainMenuController().createDuel(user1, "qqqqqqq", "3").getContent());
        User.deleteUser("user1");
    }
}
