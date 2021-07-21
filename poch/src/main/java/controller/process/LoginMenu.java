package controller.process;

import java.util.UUID;
import java.util.regex.Matcher;

import controller.LoginController;
import controller.Message;
import controller.TypeMessage;
import controller.server.TokenManager;
import model.Response;
import model.Situation;
import model.user.User;

public class LoginMenu {
    private LoginController loginController = new LoginController();
    public synchronized Response process(String command) {
        if (command.equals("menu exit")) {
            System.exit(0);
        }
        else if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Login Menu"), Situation.LOGIN);
        }
        else {
            Matcher matcher = Global.getMatcher(command, "menu enter (?<menuName>\\w+)");
            if (matcher.find()) {
                return new Response(new Message(TypeMessage.SUCCESSFUL, "please login first"), Situation.LOGIN);
            }
            matcher = Global.getMatcher(command, "user create (?=.*(?:--username (?<username>\\w+)))(?=.*(?:--nickname (?<nickname>\\w+)))(?=.*(?:--password (?<password>\\w+)))");
            if (matcher.find()) {
                return new Response(loginController.register(matcher.group("username"),matcher.group("password"),matcher.group("nickname")), Situation.LOGIN);
            }
            matcher = Global.getMatcher(command, "user login (?=.*(?:--username (?<username>\\w+)))(?=.*(?:--password (?<password>\\w+)))");
            if (matcher.find()) {
                Response response = new Response(((Message)loginController.login(matcher.group("username"),matcher.group("password")).get(0)), Situation.MAIN);
                String token = UUID.randomUUID().toString();
                response.setToken(token);
                TokenManager.addUser(User.getUserByNameAndPassword(matcher.group("username"),matcher.group("password")),token);
                return response;
            }
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.LOGIN);
    }
}