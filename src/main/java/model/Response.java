package model;

import controller.Message;

public class Response {
    private Situation currentSituation;
    private String token;
    private Message message;
    public Response(Message message, Situation currentSituation){
        this.message = message;
        this.currentSituation = currentSituation;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
    public Situation getCurrentSituation(){
        return currentSituation;
    }
    public Message getMessage(){
        return message;
    }
}
