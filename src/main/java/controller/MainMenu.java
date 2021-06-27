package controller;

import model.user.User;

public class MainMenu {
    User user;
    public MainMenu(User user){
        this.user = user;
    }
    public void menuExit(){
        new LoginController();
    }
}