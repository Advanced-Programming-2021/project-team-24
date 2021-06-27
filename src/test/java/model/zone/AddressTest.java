package model.zone;

import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class AddressTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    Zone zone = new Zone("hand", player);
    Address address = new Address(zone, 2);
    @Test
    public void getTest(){

    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
