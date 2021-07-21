package controller;

import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginController {
    public Message register(String username, String password,String nickname) {
        Message message = User.register(username,password,nickname);
        return message;
    }

    public ArrayList<Object> login(String username, String password) {
        Message message = User.login(username, password);
        //enter main menu
       if (message.getTypeMessage() == TypeMessage.SUCCESSFUL) {
           return new ArrayList<>(Arrays.asList(message,User.readUser(username)));
        }
        return new ArrayList<>(Arrays.asList(message,null));
    }
}
