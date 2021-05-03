package view;

import model.user.User;

public class ScoreboardMenu extends MainMenu {
    private programcontroller.ScoreboardMenu scoreboardMenu = new programcontroller.ScoreboardMenu(user);
    private programcontroller.MainMenu mainMenu = new programcontroller.MainMenu(user);
    public ScoreboardMenu(User user){
        super(user);
    }
    public void run(){
        while(true) {
            String command = Global.nextLine();
            if (command.compareToIgnoreCase("scoreboard show") == 0){
                System.out.printf(scoreboardMenu.showScoreboard().getContent());
            }
            checkShowCurrentMenu(command);
            if(checkMenuExit(command)) {
                mainMenu.menuExit();
                return;
            }
        }
    }
}