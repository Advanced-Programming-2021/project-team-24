package view;

import model.user.User;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    public ProfileMenu(User user){
        super(user);
    }
    private controller.ProfileMenu profileMenu = new controller.ProfileMenu(user);
    public void run(){
        while(true) {
            String command = Global.nextLine();
            if (command.equals("menu show-current")) {
                System.out.println("Profile Menu");
            }
            else if (checkMenuExit(command)) {
                exitMenu("Profile");
                return;
            }
            else if(checkEnterMenu(command)) {
                enterMenu("Profile", command );
            }
            else if (Global.regexFind(command, "profile change --nickname .*")){
                Matcher matcher = Global.getMatcher(command, "(?<=profile change --nickname ).*");
                if (matcher.find()){
                    String nickname = matcher.group();
                    System.out.println(profileMenu.changeNickname(nickname).getContent());
                }
            }
            else if (Global.regexFind(command, "profile change --password .*")){
                Matcher matcher = Global.getMatcher(command, "(?<=profile change --password --current ).*");
                if (matcher.find()){
                    String[] temp = matcher.group().split(" --new ");
                    String oldPassword = temp[0];
                    String newPassword = temp[1];
                    System.out.println(profileMenu.changePassword(oldPassword, newPassword).getContent());
                }
                matcher = Global.getMatcher(command, "(?<=profile change --password --new ).*");
                if (matcher.find()){
                    String[] temp = matcher.group().split(" --current ");
                    String oldPassword = temp[1];
                    String newPassword = temp[0];
                    System.out.println(profileMenu.changePassword(oldPassword, newPassword).getContent());
                }
            }
            else   System.out.println("invalid command");
        }
    }
}