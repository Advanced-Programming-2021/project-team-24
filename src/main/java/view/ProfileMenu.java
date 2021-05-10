package view;

import java.util.regex.Matcher;

import model.user.User;

public class ProfileMenu extends MainMenu {
    public ProfileMenu(User user){
        super(user);
    }
    private controller.ProfileMenu profileMenu = new controller.ProfileMenu(user);
    private controller.MainMenu mainMenu = new controller.MainMenu(user);
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
                matcher = Global.getMatcher(command, "(?<=profile change --password --new ).*");
                if (matcher.find()){
                    String[] temp = matcher.group().split(" --current ");
                    String oldPassword = temp[1];
                    String newPassword = temp[0];
                    System.out.printf(profileMenu.changePassword(oldPassword, newPassword).getContent());
                }
            }
            checkShowCurrentMenu(command);
            if(checkMenuExit(command)) {
                mainMenu.menuExit();
                return;
            }
        }
    }
}