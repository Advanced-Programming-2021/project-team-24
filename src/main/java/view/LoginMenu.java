package view;

import java.util.regex.Matcher;

import controller.Message;
import controller.TypeMessage;
import model.user.User;

public class LoginMenu {

    public void register(String username, String password,String nickname) {
            Message message = User.register(username,password,nickname);
            System.out.println(message.getContent());
    }

    public void login(String username, String password) {
        Message message = User.login(username, password);
        System.out.println(message.getContent());
        //enter main menu
        if (message.getTypeMessage() == TypeMessage.SUCCESSFUL) {
            new MainMenu(User.readUser(username)).run();
        }
    }

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
