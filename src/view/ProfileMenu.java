package view;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import model.user.User;
import programcontroller.Message;
import programcontroller.TypeMessage;

public class ProfileMenu extends Menu{
    private programcontroller.ProfileMenu profileMenu = new programcontroller.ProfileMenu(user);
    public ProfileMenu(User user){
        super(user);
    }
    public void run(){
        while(true) {
            String command = Global.nextLine();
            if (Global.regexFind(command, "profile change --nickname .*")){
                Matcher matcher = Global.getMatcher(command, "(?<=profile change --nickname ).*");
                if (matcher.find()){
                    String nickname = matcher.group();
                    System.out.printf(profileMenu.changeNickname(nickname).getContent());
                }
            }
            else if (Global.regexFind(command, "profile change --password .*")){
                Matcher matcher = Global.getMatcher(command, "(?<=profile change --password --current ).*");
                if (matcher.find()){
                    String[] temp = matcher.group().split(" --new ");
                    String oldPassword = temp[0];
                    String newPassword = temp[1];
                    System.out.printf(profileMenu.changePassword(oldPassword, newPassword).getContent());
                }
            }
        }
    }
}