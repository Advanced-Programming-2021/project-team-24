package controller.server;

import model.user.User;

import java.util.HashMap;

public class TokenManager {
    private static final HashMap<String, User> loggedInUsers = new HashMap<>();
    public static String addUser(User user, String token) {
        synchronized (loggedInUsers) {
            loggedInUsers.put(token, user);
        }
        return token;
    }

    public static User getUser(String token) {
        synchronized (loggedInUsers) {
            return loggedInUsers.get(token);
        }
    }
    public static boolean isValidToken(String toekn){
        if(loggedInUsers.get(toekn) == null)
            return false;
        return true;
    }
}
