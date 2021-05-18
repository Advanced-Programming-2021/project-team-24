package view;

import model.user.User;

public class ScoreboardMenu extends Menu {
    private controller.ScoreboardMenu scoreboardMenu = new controller.ScoreboardMenu(user);
    private controller.MainMenu mainMenu = new controller.MainMenu(user);
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