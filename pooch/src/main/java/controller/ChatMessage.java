package controller;
import model.user.User;

public class ChatMessage {
    public User user;
    public String message;
    private int id;
    public int inReplyToId;
    private static int lastId;
    public ChatMessage(User user,String message,int inReplyToId){
        this.user = user;
        this.message = message;
        this.inReplyToId = inReplyToId;
        id = ++lastId;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public int getInReplyToId() {
        return inReplyToId;
    }
}