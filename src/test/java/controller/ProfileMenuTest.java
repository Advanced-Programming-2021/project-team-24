package controller;

import static org.junit.Assert.assertEquals;

import model.user.User;
import org.junit.Test;

public class ProfileMenuTest {
    @Test
    public void changePasswordSuccessfullyTest(){
        User user = new User("salam", "aaaaaa", "sdfsdf");
        assertEquals("password changed successfully!", new ProfileMenu(user).changePassword("aaaaaa","bbbbb").getContent());
        User.deleteUser("salam");
    }
    @Test
    public void changePasswordSamePasswordsTest(){
        User user = new User("salam", "aaaaaa", "sdfsdf");
        assertEquals("please enter a new password", new ProfileMenu(user).changePassword("aaaaaa","aaaaaa").getContent());
        User.deleteUser("salam");
    }
    @Test
    public void changePasswordInvalidPasswordTest(){
        User user = new User("salam", "aaaaaa", "sdfsdf");
        assertEquals("current password is invalid", new ProfileMenu(user).changePassword("a","bbbbb").getContent());
        User.deleteUser("salam");
    }
    @Test
    public void changeNicknameSuccessfullyTest(){
        User user = new User("salam", "aaaaaa", "nickname1");
        assertEquals("nickname changed successfully!", new ProfileMenu(user).changeNickname("nickname2").getContent());
        User.deleteUser("salam");
    }
    @Test
    public void changeNicknameDuplicateNickname(){
        User user1 = new User("user1", "aaaaaa", "nickname1");
        User user2 = new User("user2", "aaaaaa", "nickname2");
        assertEquals("user with nickname nickname2 already exists", new ProfileMenu(user1).changeNickname("nickname2").getContent());
        User.deleteUser("user1");
        User.deleteUser("user2");
    }
}
