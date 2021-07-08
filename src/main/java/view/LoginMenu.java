package view;

import java.util.regex.Matcher;

import controller.LoginController;

public class LoginMenu {
    private LoginController loginController = new LoginController();
    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                System.exit(0);
            }
            else if (command.equals("menu show-current")) {
                System.out.println("Login Menu");
            }
            else {
                Matcher matcher = Global.getMatcher(command, "menu enter (?<menuName>\\w+)");
                if (matcher.find()) {
                    System.out.println("please login first");
                    continue;
                }
                matcher = Global.getMatcher(command, "user create (?=.*(?:--username (?<username>\\w+)))(?=.*(?:--nickname (?<nickname>\\w+)))(?=.*(?:--password (?<password>\\w+)))");
                if (matcher.find()) {
                    loginController.register(matcher.group("username"),matcher.group("password"),matcher.group("nickname"));
                    continue;
                }
                matcher = Global.getMatcher(command, "user login (?=.*(?:--username (?<username>\\w+)))(?=.*(?:--password (?<password>\\w+)))");
                if (matcher.find()) {
                    loginController.login(matcher.group("username"),matcher.group("password"));
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