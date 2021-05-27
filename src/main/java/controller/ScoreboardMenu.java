package controller;

import model.user.User;

import java.util.Collections;
import java.util.List;


public class ScoreboardMenu extends MainMenu {
    private List<String> usernames = User.getUsernames();
    public ScoreboardMenu(User user){
        super(user);
    }
    private void sortUserList(List<String> usernames){
        for (int i = 0; i < usernames.size(); i++) {
            for (int j = i+1; j < usernames.size(); j++) {
                User userI = User.readUser(usernames.get(i));
                User userJ = User.readUser(usernames.get(j));
                if (userI.getScore() < userJ.getScore())  
                {
                    Collections.swap(usernames, i, j);
                }
                else if (userI.getScore() == userJ.getScore() && userI.getNickname().compareTo(userJ.getNickname()) > 0) Collections.swap(usernames, i, j);
            }
        }
    }
    public Message showScoreboard(){
        sortUserList(usernames);
        String content = "";
        for (int i = 1; i < usernames.size()+1; i++) {
            User user = User.readUser(usernames.get(i-1));
            content += i + "- " + user.getNickname() + ": " + user.getScore() + "\n";
        }
        return new Message(TypeMessage.INFO, content);
    }
}