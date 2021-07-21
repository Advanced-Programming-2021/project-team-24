package server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import model.user.User;
import view.Global;

public class ChatServer {
    static List<String> messagesContent = new ArrayList<>();
    static List<String> messageSenders = new ArrayList<>();
    static List<User> requestSingleRound = new ArrayList<>();
    static List<User> requestMatch = new ArrayList<>();

    public synchronized String handle(String command)
    {
        if(TokenManager.isValidToken(command)){
            String cmd = TokenManager.deleteTokenFromCommand(command);
            if(cmd.equals("--request [13]")){

            }            
            else if(cmd.equals("--request --ignore [13]")){

            }   
            else if(Global.regexFind(cmd, "send --message .+")){

            }else if(cmd.equals("get chat contents")){
                return new Gson().toJson(messageSenders) + new Gson().toJson(messagesContent);
            }         
        }        
        return null;
    }
    public static String addRequest(String request, User user){
        Matcher matcher = Global.getMatcher(request, "--request ([13])");
        matcher.find();
        Integer v = Integer.parseInt(matcher.group(1));
        switch(v)
        {
            case 1:
                for(int i = 0; i < requestSingleRound.size(); i++){
                    if(requestSingleRound.get(i).getUsername().equals(user.getUsername()))
                        return "you have requested before";
                }                
                requestSingleRound.add(user);
                return "you request added";
            case 3:
                for(int i = 0; i < requestMatch.size(); i++){
                    if(requestMatch.get(i).getUsername().equals(requestMatch.get(i).getUsername())){
                        return "you have requested before";
                    }
                }
                return "you request added";
        }
        return "invalid command";
    }    
    public static String ignoreRequent(String requst, User user){
        Matcher matcher = Global.getMatcher(requst, "--request --ignore ([13])");        
        matcher.find();
        Integer v = Integer.parseInt(matcher.group(1));
        switch(v)
        {
            case 1:
                for(int i = 0; i < requestSingleRound.size(); i++){
                    if(requestSingleRound.get(i).getUsername().equals(user.getUsername()))
                    {
                        requestSingleRound.remove(i);
                        return "your request removed";
                    }
                }                
                requestSingleRound.add(user);
                return "you didn't requested yet";
            case 3:
                for(int i = 0; i < requestMatch.size(); i++){
                    if(requestMatch.get(i).getUsername().equals(requestMatch.get(i).getUsername())){
                        requestMatch.remove(i);
                        return "your request removed";
                    }
                }
                return "you didn't requested yet";
        }
        return "invalid command";
    }
    public static String sendMessage(String message, User user){
        Matcher matcher = Global.getMatcher(message, "send --message (.+)");
        matcher.find();
        messageSenders.add(user.getUsername());
        messagesContent.add(matcher.group(1));
        return "message send successfully";
    }
    public static String getLastMessage(){
        return messagesContent.get(messagesContent.size()-1);
    }
}
