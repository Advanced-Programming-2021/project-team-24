package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import controller.Message;
import controller.TypeMessage;
import controller.process.Global;
import controller.server.TokenManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Request;
import model.Response;
import model.Situation;
import model.user.User;

public class ChatServer {
    static List<String> messagesContent = new ArrayList<>();
    static List<String> messageSenders = new ArrayList<>();
    static List<User> requestSingleRound = new ArrayList<>();
    static List<User> requestMatch = new ArrayList<>();
    static Timeline timeline = new Timeline();
    static{
        timeline.setDelay(2000);
        KeyFrame updateRequest = new KeyFrame(Duration.seconds(2), events->{
            for(User user : requestSingleRound)
            {
                
            }
        });

        timeline.getKeyFrames().add(new KeyFrame(2000, new EventHandler<>(){

        }));
    }

    public static synchronized Response handle(Request command)
    {
        timeline.setDelay(2);
        
        if(TokenManager.isValidToken(command.getToken())){
            String cmd = command.getInput();
            if(cmd.equals("--request [13]")){
                return new Response(addRequest(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }            
            else if(cmd.equals("--request --ignore [13]")){
                return new Response(ignoreRequent(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }   
            else if(Global.regexFind(cmd, "send --message .+")){
                return new Response(sendMessage(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }else if(cmd.equals("getAllMessages")){
                return new Response(new Message(TypeMessage.INFO, getAllMessages()), Situation.MAIN);
            }         
        }        
        return null;
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
                        return new Message(TypeMessage.ERROR, "you have requested before");
                }                
                requestSingleRound.add(user);
                return new Message(TypeMessage.SUCCESSFUL, "you request added");
            case 3:
                for(int i = 0; i < requestMatch.size(); i++){
                    if(requestMatch.get(i).getUsername().equals(requestMatch.get(i).getUsername())){
                        return new Message(TypeMessage.SUCCESSFUL, "you have requested before");
                    }
                }
                return new Message(TypeMessage.ERROR, "you request added");
        }
        return new Message(TypeMessage.ERROR, "invalid command");
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
            ans += messageSenders.get(i) + ": " + messagesContent.get(i) + "\n";
        }
        return new Message(TypeMessage.INFO, ans).getContent();
    }
}
