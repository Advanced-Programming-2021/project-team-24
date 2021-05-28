package controller;

import model.user.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;


public class ScoreboardMenuTest {
    @Test
    public void sortUserListTest(){
        User user1 = new User("user1", "12", "bb");
        User user2 = new User("user2", "34", "vv");
        List<String> usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");
        assertEquals(usernames, new ScoreboardMenu(user1).sortUserList(usernames));
        User.deleteUser("user1");
        User.deleteUser("user2");
    }
    @Test
    public void showScoreboardTest(){
        User user1 = new User("user1", "12", "bb");
        User user2 = new User("user2", "34", "vv");
        List<String> usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");
        String string = "1- bb: 8000\n" +
                "2- vv: 8000\n";
        assertEquals(string, new ScoreboardMenu(user1).showScoreboard().getContent());
        User.deleteUser("user1");
        User.deleteUser("user2");
    }
}
