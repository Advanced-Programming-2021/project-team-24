package controller.process;

import controller.Message;
import controller.TypeMessage;
import model.Response;
import model.Situation;
import model.user.User;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    public ProfileMenu(User user){
        super(user);
    }
    private controller.ProfileMenu profileMenu = new controller.ProfileMenu(user);
    public synchronized Response process(String command){
        if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Profile Menu"), Situation.PROFILE);
        }
        else if (checkMenuExit(command)) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.MAIN);
        }
        else if(checkEnterMenu(command)) {
            return enterMenu("Profile", command );
        }
        else if (Global.regexFind(command, "profile change --nickname .*")){
            Matcher matcher = Global.getMatcher(command, "(?<=profile change --nickname ).*");
            if (matcher.find()){
                String nickname = matcher.group();
                return new Response(profileMenu.changeNickname(nickname), Situation.PROFILE);
            }
        }
        else if (Global.regexFind(command, "profile change --password .*")){
            Matcher matcher = Global.getMatcher(command, "(?<=profile change --password --current ).*");
            if (matcher.find()){
                String[] temp = matcher.group().split(" --new ");
                String oldPassword = temp[0];
                String newPassword = temp[1];
                return new Response(profileMenu.changePassword(oldPassword, newPassword), Situation.PROFILE);
            }
            matcher = Global.getMatcher(command, "(?<=profile change --password --new ).*");
            if (matcher.find()){
                String[] temp = matcher.group().split(" --current ");
                String oldPassword = temp[1];
                String newPassword = temp[0];
                return new Response(profileMenu.changePassword(oldPassword, newPassword), Situation.PROFILE);
            }
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.PROFILE);
    }
}