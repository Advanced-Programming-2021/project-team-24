package controller.client;

import model.Request;
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
    public static synchronized boolean isValidToken(Request token){
        if(loggedInUsers.get(token.getToken()) == null)
        {
            return false;
        }
        else
            return true;
    }    
}
