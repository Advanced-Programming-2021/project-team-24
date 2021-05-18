package view;

import java.util.regex.Matcher;

import controller.Message;
import controller.TypeMessage;
import model.user.User;

import static model.user.User.login;
import static model.user.User.register;

public class LoginMenu {



    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                return;
            } else if (command.equals("menu show-current")) {
                System.out.println("Login Menu");
            } else {
                Matcher matcher = Global.getMatcher(command, "menu enter (?<menuName>\\w+)");
                if (matcher.find()) {
                    System.out.println("please login first");
                    continue;
                }
                matcher = Global.getMatcher(command, "user create (?=.*(?:--username (?<username>\\w)))(?=.*(?:--nickname (?<nickname>\\w)))(?=.*(?:--password (?<password>\\w)))");
                if (matcher.find()) {
                    register(matcher.group("username"),matcher.group("password"),matcher.group("nickname"));
                    continue;
                }
                matcher = Global.getMatcher(command, "user login (?=.*(?:--username (?<username>\\w)))(?=.*(?:--nickname (?<nickname>\\w)))");
                if (matcher.find()) {
                    login(matcher.group("username"),matcher.group("password"));
                    continue;
                }
                System.out.println("invalid command");
            }
        }
    }

    public static void main(String[] args) {
        new LoginMenu().run();


    }
}