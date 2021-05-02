package view;

import model.user.User;

import java.util.regex.Matcher;

public class ScoreboardMenu extends Menu {
    private programcontroller.ScoreboardMenu scoreboardMenu = new programcontroller.ScoreboardMenu(user);
    public ScoreboardMenu(User user){
        super(user);
    }
    public void run(){
        while(true) {
            String command = Global.nextLine();
            if (command.compareToIgnoreCase("scoreboard show") == 0){
                System.out.printf(scoreboardMenu.showScoreboard().getContent());
            }
        }
    }
}