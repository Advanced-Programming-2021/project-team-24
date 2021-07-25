package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import controller.Message;
import controller.TypeMessage;
import controller.process.DuelMenu;
import controller.process.Global;
import controller.server.Server;
import controller.server.TokenManager;
import controller.*;
import model.Request;
import model.Response;
import model.Situation;
import model.user.Player;
import model.user.User;

public class ChatServer {
    static List<ChatMessage> chatMessages = new ArrayList<>();
    static List<User> requestSingleRound = new ArrayList<>();
    static List<User> requestMatch = new ArrayList<>();

    static int pinIndex = -1;

    static Boolean isGameStarted = false;

    public static synchronized Response handle(Request command)
    {

        if(TokenManager.isValidToken(command.getToken())){
            String cmd = command.getInput();
            System.out.println(cmd);
            if(Global.regexFind(cmd,"--request [13]")){
                return addRequest(cmd, TokenManager.getUser(command.getToken()));
            }
            else if(cmd.equals("getOnlineMembers")){
                return getOnlineMembers();
            }
            else if (cmd.equals("update")){
                return update(cmd,TokenManager.getUser(command.getToken()));
            }else if(cmd.equals("getPin")){
                return new Response(getPin(), Situation.MAIN);
            }else if(Global.regexFind(cmd,"--request --ignore [13]")){
                return new Response(ignoreRequent(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }else if(Global.regexFind(cmd, "unpin")){
                return unpinMessage();
            }else if(Global.regexFind(cmd, "pin --number [0-9]+")){
                return pinMessage(cmd);
            }else if(Global.regexFind(cmd, "delete --number [0-9]+")){
                return new Response(deleteMessage(cmd), Situation.MAIN);
            }            else if(cmd.equals("getAllMessages")){
                return new Response(new Message(TypeMessage.INFO, getAllMessages()), Situation.MAIN);
            }
            else if(Global.regexFind(cmd,"edit --number ([0-9]+) --message (.+)")){
                return editMessage(cmd,TokenManager.getUser(command.getToken()));
            }
            else{
                return new Response(sendMessage(cmd, TokenManager.getUser(command.getToken())), Situation.MAIN);
            }
        }
        return null;
    }
    public static Response pinMessage(String request){
        Matcher matcher = Global.getMatcher(request, "pin --number ([0-9]+)");
        matcher.find();
        Integer pinC = Integer.parseInt(matcher.group(1));
        if(pinC > -1 && pinC < chatMessages.size()){
            pinIndex = pinC;
            return new Response(new Message(TypeMessage.SUCCESSFUL, "pinned successfully"), Situation.MAIN);
        }
        else
            return new Response(new Message(TypeMessage.ERROR, "invalid index"), Situation.MAIN);
    }
    public static Message getPin(){
        if(pinIndex != -1)
            return new Message(TypeMessage.INFO, new Gson().toJson((chatMessages.get(pinIndex))));
        else
            return new Message(TypeMessage.ERROR, "no message pinned yet");
    }
    public static Response unpinMessage(){
        pinIndex = -1;
        return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.MAIN);
    }


    public static Response editMessage(String request, User user){
        Matcher matcher = Global.getMatcher(request, "edit --number ([0-9]+) --message (.+)");
        matcher.find();
        Integer number = Integer.parseInt(matcher.group(1));
        ChatMessage x = new Gson().fromJson(matcher.group(2), ChatMessage.class);
        if(number > chatMessages.size() - 1 || number < 0){
            return new Response(new Message(TypeMessage.ERROR, "Invalid index"), Situation.MAIN);
        }
        else
        {
            if(chatMessages.get(number).getUser().getUsername().equals(user.getUsername())){
                chatMessages.get(number).message = x.getMessage();
                chatMessages.get(number).inReplyToId = x.inReplyToId;
                return new Response(new Message(TypeMessage.SUCCESSFUL, "message edited successfully"), Situation.MAIN);
            }
            else
                return new Response(new Message(TypeMessage.ERROR, "this is not your message"), Situation.MAIN);
        }
    }

    public static Message deleteMessage(String request){
        Matcher matcher = Global.getMatcher(request, "delete --number ([0-9]+)");
        matcher.find();
        int number = Integer.parseInt(matcher.group(1));
        if(number < 0 || number > chatMessages.size() - 1){
            return new Message(TypeMessage.ERROR, "invalid index");
        }
        chatMessages.remove(number);
        return new Message(TypeMessage.SUCCESSFUL, "message removed successfully");
    }

    public synchronized static Response update(String request, User user) {
        if (request.equals("update")) {
            int secondIndex = 0;
            if (requestSingleRound.size() == 2) {
                System.out.println("amad");
                String nickname = user.getNickname();
                System.out.println(nickname+" "+requestSingleRound.get(0).getNickname());
                System.out.println(nickname+" "+requestSingleRound.get(1).getNickname());
                if (nickname.equals(requestSingleRound.get(0).getNickname())) {
                    System.out.println("amadd");

                    Player player1 = new Player(User.readUser(requestSingleRound.get(0).getUsername()));
                    Player player2 = new Player(User.readUser(requestSingleRound.get(1).getUsername()));
                    System.out.println(player1.getNickname()+" "+player2.getNickname());
                    new DuelMenu(player1, player2);
                    requestSingleRound.remove(0);
                    isGameStarted = true;
                    return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
                } else if (nickname.equals(requestSingleRound.get(1).getNickname())) {
                    Player player1 = new Player(User.readUser(requestSingleRound.get(0).getUsername()));
                    Player player2 = new Player(User.readUser(requestSingleRound.get(1).getUsername()));
                    new DuelMenu(player1, player2);
                    requestSingleRound.remove(1);
                    isGameStarted = true;
                    return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
                }
            } else if (requestSingleRound.size() == 1) {
                if (user.getNickname().equals(requestSingleRound.get(0).getNickname())) {
                    if (isGameStarted) {
                        requestSingleRound.remove(0);
                        isGameStarted = false;
                        return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
                    }
                }
            } else if (requestMatch.size() == 2) {
                String nickname = user.getNickname();
                for (int i = 0; i < requestMatch.size(); i++) {
                    for (int j = i; j < requestMatch.size(); j++) {
                        if (requestMatch.get(i).getScore() / requestMatch.get(j).getScore() < 1.5 && requestMatch.get(i).getScore() / requestMatch.get(j).getScore() > 0.6) {
                            if (nickname.equals(requestMatch.get(i).getNickname())) {
                                Player player1 = new Player(requestMatch.get(i));
                                Player player2 = new Player(requestMatch.get(j));
                                new DuelMenu(player1, player2);
                                requestMatch.remove(i);
                                secondIndex = j;
                                isGameStarted = true;
                                return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
                            } else if (nickname.equals(requestMatch.get(j).getNickname())) {
                                Player player1 = new Player(requestMatch.get(i));
                                Player player2 = new Player(requestMatch.get(j));
                                new DuelMenu(player1, player2);
                                requestMatch.remove(j);
                                secondIndex  = i;
                                isGameStarted = true;
                                return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
                            }
                        }
                    }
                }
            } else if (isGameStarted && user.getNickname().equals(requestMatch.get(secondIndex).getNickname())) {
                requestMatch.remove(secondIndex);
                isGameStarted = false;
                return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Started"), Situation.DUEL);
            }
        }
        return new Response(new Message(TypeMessage.ERROR, ""), Situation.MAIN);
    }


    public static Response addRequest(String request, User user){
        Matcher matcher = Global.getMatcher(request, "--request ([13])");
        matcher.find();
        Integer v = Integer.parseInt(matcher.group(1));
        switch(v)
        {
            case 1:
                for(int i = 0; i < requestSingleRound.size(); i++){
                    if(requestSingleRound.get(i).getUsername().equals(user.getUsername()))
                        return new Response(new Message(TypeMessage.ERROR, "you have requested before"),Situation.MAIN);
                }
                requestSingleRound.add(user);
                return new Response(new Message(TypeMessage.SUCCESSFUL, "you request added"),Situation.MAIN);
            case 3:
                for(int i = 0; i < requestMatch.size(); i++){
                    if(requestMatch.get(i).getUsername().equals(requestMatch.get(i).getUsername())){
                        return new Response(new Message(TypeMessage.ERROR, "you have requested before"),Situation.MAIN);
                    }
                }
                requestMatch.add(user);
                return new Response(new Message(TypeMessage.SUCCESSFUL, "you request added"),Situation.MAIN);
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.MAIN);
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
        ChatMessage v;
        try{
            v = new Gson().fromJson(message, ChatMessage.class);
            chatMessages.add(v);
        }catch(Exception e){

        }
        return new Message(TypeMessage.SUCCESSFUL, "message send successfully");
    }

    public static String getAllMessages(){

        return new Message(TypeMessage.INFO, new Gson().toJson(chatMessages)).getContent();
    }
    public static Response getOnlineMembers(){
        System.out.println("1");
        ArrayList<User> users = new ArrayList<>(TokenManager.getLoggedInUsers().values());
        System.out.println("2");
        return new Response(new Message(TypeMessage.INFO, new Gson().toJson(users)), Situation.MAIN);

    }
}