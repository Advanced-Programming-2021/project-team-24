package server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import controller.TypeMessage;
import controller.client.TokenManager;
import model.Request;
import model.Response;
import model.Situation;
import model.user.User;
import view.Global;

public class ChatServer {
    static List<String> messagesContent = new ArrayList<>();
    static List<String> messageSenders = new ArrayList<>();
    static List<User> requestSingleRound = new ArrayList<>();
    static List<User> requestMatch = new ArrayList<>();

    public synchronized Response handle(Request command)
    {
        
        if(TokenManager.isValidToken(command)){
            String cmd = command.getInput();
            if(cmd.equals("--request [13]")){
                return new Response(addRequest(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }            
            else if(cmd.equals("--request --ignore [13]")){
                return new Response(ignoreRequent(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }   
            else if(Global.regexFind(cmd, "send --message .+")){
                return new Respone(sendMessage(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }else if(cmd.equals("getAllMessages")){
                return new Response(new Message(TypeMessage.INFO, getAllMessages()), Situation.MAIN);
            }         
        }        
        return new Response(new Message(TypeMessage.ERROR, "invalid token"), Situation.Main);
    }
    public static Message addRequest(String request, User user){
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
    public static Message ignoreRequent(String requst, User user){
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
                        return new Message(TypeMessage.SUCCESSFUL, "your request removed");
                    }
                }                
                requestSingleRound.add(user);
                return new Message(TypeMessage.ERROR, "you didn't requested yet");
            case 3:
                for(int i = 0; i < requestMatch.size(); i++){
                    if(requestMatch.get(i).getUsername().equals(requestMatch.get(i).getUsername())){
                        requestMatch.remove(i);
                        return new Message(TypeMessage.SUCCESSFUL, "your request removed");
                    }
                }
                return new Message(TypeMessage.ERROR, "you didn't requested yet");
        }
        return new Message(TypeMessage.ERROR, "invalid command");
    }
    public static Message sendMessage(String message, User user){
        Matcher matcher = Global.getMatcher(message, "send --message (.+)");
        matcher.find();
        messageSenders.add(user.getUsername());
        messagesContent.add(matcher.group(1));
        return new Message(TypeMessage.SUCCESSFUL, "message send successfully");
    }
    public static String getLastMessage(){
        return messagesContent.get(messagesContent.size()-1);
    }
    public static String getAllMessages(){
        String ans = "";
        for(int i = 0; i < messagesContent.size(); i++){
            ans += messageSenders.get(i) + " " + messagesContent.get(i) + "\n";
        }
        return new Message(TypeMessage.INFO, ans);
    }
}
