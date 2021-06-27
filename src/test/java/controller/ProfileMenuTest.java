package controller;

import static org.junit.Assert.assertEquals;

import model.user.User;
import org.junit.Test;

public class ProfileMenuTest {
    @Test
    public void changePasswordTest(){
        User user = new User("salam", "aaaaaa", "sdfsdf");
        assertEquals("password changed successfully!", new ProfileMenu(user).changePassword("aaaaaa","bbbbb").getContent());
        assertEquals("please enter a new password", new ProfileMenu(user).changePassword("aaaaaa","aaaaaa").getContent());
        assertEquals("current password is invalid", new ProfileMenu(user).changePassword("a","bbbbb").getContent());
        User.deleteUser("salam");
    }
    @Test
    public void changeNicknameTest(){
        User user1 = new User("user1", "aaaaaa", "nickname1");
        User user2 = new User("user2", "aaaaaa", "nickname2");
        assertEquals("user with nickname nickname2 already exists", new ProfileMenu(user1).changeNickname("nickname2").getContent());
        assertEquals("nickname changed successfully!", new ProfileMenu(user1).changeNickname("nickname").getContent());
        User.deleteUser("user1");
        User.deleteUser("user2");
    }
}
