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
            else if (command.equals("menu show-current")) {
                System.out.println("Scoreboard Menu");
            }
            else if (checkMenuExit(command)) {
                exitMenu("Scoreboard");
                return;
            }
            else if(checkEnterMenu(command)) {
                enterMenu("Scoreboard", command );
            }
            else System.out.println("invalid command");
        }
    }
}