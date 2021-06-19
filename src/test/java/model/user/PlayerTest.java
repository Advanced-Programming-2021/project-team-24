package model.user;

import model.zone.Address;
import model.zone.Zone;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlayerTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    @Test
    public void getSelectedAddress(){
        Address address = new Address(new Zone("hand", player), 2);
        player.selectAddress(address);
        assertEquals(address, player.getSelectedAddress());
    }
    @Test
    public void getNicknameTest(){
        assertEquals(player.getNickname(), "sdfsdf");
    }
    @Test
    public void changeLifePointTest(){
        int lifePoint = player.getLifePoint();
        player.changeLifePoint(10);
        assertEquals(player.getLifePoint(), lifePoint + 10);
    }
    @Test
    public void isDeadTest(){
        assertFalse(player.isDead());
        player.changeLifePoint(-10000);
        assertTrue(player.isDead());
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
