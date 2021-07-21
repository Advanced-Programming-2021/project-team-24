package server;

import java.io.IOException;
import java.util.Timer;

import controller.Message;
import controller.client.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.time.LocalDateTime;
import javafx.util.Duration;

public class ChatClient {
    static boolean isRequestSingle = false;
    static boolean isRequestMatch = false;
    Timeline time = new Timeline();
    static{
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), events->{
            java.time.LocalTime.now().getSecond()
            if(java.time.LocalTime.now().getSecond() < 30 && java.time.LocalTime.now().getSecond() > 20){
                Client.getres
            }
        });
    }

    public static Message sendMessage(String message) throws IOException{
        return (Client.getResponse("send --message " + message).getMessage());        
    }
    public static Message sendSingleRequest() throws IOException
    {
        isRequestSingle = true;
        return Client.getResponse("--request 1").getMessage();        
        
    }
    public static Message sendMatchRequest() throws IOException{
        isRequestMatch = true;
        return Client.getResponse("--request 3").getMessage();
    }
    public static Message ignoreSingleRequest() throws IOException{
        isRequestSingle = false;
        return Client.getResponse("--request --ignore 1").getMessage();        
    }
    public static Message ignoreMatchRequest() throws IOException{
        isRequestMatch = false;
        return Client.getResponse("--request --ignore 3").getMessage();
    }    
    public static Message getAllMessage() throws IOException{
        return Client.getResponse("getAllMessages").getMessage();
    }
}
