package controller;

import model.user.User;
import view.MainMenu;

public class LoginMenu {
    public void register(String username, String password,String nickname) {
        Message message = User.register(username,password,nickname);
        System.out.println(message.getContent());
    }

    public void login(String username, String password) {
        Message message = User.login(username, password);
        System.out.println(message.getContent());
        //enter main menu
        if (message.getTypeMessage() == TypeMessage.SUCCESSFUL) {
            new MainMenu(User.readUser(username)).run();
        }
    }
}
