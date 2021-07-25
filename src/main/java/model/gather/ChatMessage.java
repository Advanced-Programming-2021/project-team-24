package model.gather;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatMessage {
    private User user;
    private String message;
    private int id;
    private int inReplyToId;
    public static ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private static int pinMessageId = -1;
    public static int replyId = -1;
    //public static HashMap<FontAwesomeIcon,ChatMessage> iconMap;
    public ChatMessage(User user,String message,int inReplyToId){
        this.user = user;
        this.message = message;
        this.inReplyToId = inReplyToId;
        chatMessages.add(this);
        this.id = chatMessages.size()-1;
    }

    public static void setPinMessageId(int pinMessageId) {
        ChatMessage.pinMessageId = pinMessageId;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public static int getPinMessageId() {
        return pinMessageId;
    }

    public int getInReplyToId() {
        return inReplyToId;
    }

    public int getId() {
        return id;
    }

    public static ChatMessage getById(int id){
        for(ChatMessage chatMessage : chatMessages){
            if(chatMessage.getId()==id) return chatMessage;
        }
        return null;
    }
}
