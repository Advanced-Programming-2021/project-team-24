package view;


import model.user.User;

public class Menu {
    protected User user;
    public Menu(User user){
        this.user = user;
    }
    public void checkShowCurrentMenu(String command){
        if(Global.regexFind(command , "menu show-current")){
            System.out.println("Main Menu");
        }
    }
    public boolean checkMenuExit(String command){
        if(command.compareToIgnoreCase("menu exit") == 0) return true;
        return false;
    }
}