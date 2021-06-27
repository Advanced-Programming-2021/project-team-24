package model.zone;

import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class ZoneTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    Zone zone = new Zone("hand", player);
    @Test
    public void isValidTest(){
        assertTrue(zone.isValid());
        assertFalse(new Zone("jjj", player).isValid());
    }
    @Test
    public void zoneTest(){
        assertEquals(zone.zone(), "hand");
    }
    @Test
    public void getZoneStringsTest(){
        assertEquals(Zone.getZoneStrings()[3], "hand");
    }
    @Test
    public void getValueTest(){
        assertEquals(Zones.HAND.getValue(), "hand");
    }
    @Test
    public void valueOfLabelTest(){
        assertEquals(Zones.valueOfLabel("hand"), Zones.HAND);
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
