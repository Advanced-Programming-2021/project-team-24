package controller.process;

import controller.Message;
import controller.TypeMessage;
import model.Response;
import model.Situation;
import model.user.User;

public class ScoreboardMenu extends Menu {
    private controller.ScoreboardMenu scoreboardMenu = new controller.ScoreboardMenu(user);
    public ScoreboardMenu(User user){
        super(user);
    }
    public synchronized Response process(String command){
        if (command.compareToIgnoreCase("scoreboard show") == 0){
            return new Response(scoreboardMenu.showScoreboard(), Situation.SCOREBOARD);
        }
        else if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Scoreboard Menu"), Situation.SCOREBOARD);
        }
        else if (checkMenuExit(command)) {
            return new Response(new Message(TypeMessage.ERROR, ""), Situation.MAIN);
        }
        else if(checkEnterMenu(command)) {
            return enterMenu("Scoreboard", command );
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.SCOREBOARD);
    }
}