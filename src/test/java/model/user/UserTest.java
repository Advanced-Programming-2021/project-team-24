package model.user;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import model.user.User;
public class UserTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    @Test
    public void getInformationTest(){
        assertEquals(user.getUsername(), "salam");
        assertEquals(user.getNickname(), "sdfsdf");
        assertEquals(user.getScore(), 8000);
        assertEquals(user.getCoin(), 100000);
    }
    @Test
    public void getUserByNameAndPasswordTest(){
        assertEquals(user.getNickname() ,User.getUserByNameAndPassword("salam", "aaaaaa").getNickname());
        assertNull(User.getUserByNameAndPassword("aaaa", "bbbb"));
        assertNull(User.getUserByNameAndPassword("salam", "bbbb"));
    }
    @Test
    public void changePasswordTest(){
        assertTrue(user.changePassword("aaaaaa", "bbbbbb"));
        assertFalse(user.changePassword("", "bbbbbb"));
    }
    @Test
    public void readUserTest(){
        assertEquals(User.readUser("salam").getNickname(), user.getNickname());
    }
    @Test
    public void registerTest(){
        assertEquals(User.register("salam", "aaaaaa", "sdfsdf").getContent(), "user with username salam already exists");
        assertEquals(User.register("behz", "fgh", "sdfsdf").getContent(), "user with nickname sdfsdf already exists");
        assertEquals(User.register("behz", "fgh", "sdfs").getContent(), "user created successfully!");
    }
    @Test
    public void loginTest(){
        assertEquals(User.login("benz", "aaaaaa").getContent(), "Username and password didn’t match!");
        assertEquals(User.login("salam", "fgh").getContent(), "Username and password didn’t match!");
        assertEquals(User.login("salam", "aaaaaa").getContent(), "user logged in successfully!");
    }
    @Test
    public void getUsernamesTest(){
        assertEquals(user.getUsername(), User.getUsernames().get(0));
    }
    @Test
    public void changeNickname(){
        user.changeNickname("hahaha");
        assertEquals("hahaha", user.getNickname());
    }
    @Test
    public void changeScore(){
        int score = user.getScore();
        user.changeScore(10);
        assertEquals(score + 10, user.getScore());
    }
    @Test
    public void increaseCoinTest(){
        int coin = user.getCoin();
        user.increaseCoin(100);
        assertEquals(user.getCoin(), 100 + coin);
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
        User.deleteUser("behz");
    }
}