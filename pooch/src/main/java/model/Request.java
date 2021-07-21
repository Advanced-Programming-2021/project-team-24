package model;

public class Request {
    private Situation currentSituation;
    private String token;
    private String input;
    public Request(String input, Situation currentSituation, String token){
        this.input = input;
        this.token = token;
        this.currentSituation = currentSituation;
    }
    public Situation getCurrentSituation(){
        return currentSituation;
    }
    public String getInput(){
        return input;
    }
    public String getToken(){
        return token;
    }
}
