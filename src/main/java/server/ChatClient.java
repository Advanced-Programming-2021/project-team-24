package server;

import java.io.IOException;

import controller.Message;
import controller.client.Client;

public class ChatClient {
    

    public static Message sendMessage(String message) throws IOException{
        return (Client.getResponse("send --message " + message).getMessage());        
    }
    public static Message sendSingleRequest() throws IOException
    {
        return Client.getResponse("--request 1").getMessage();        
    }
    public static Message sendMatchRequest() throws IOException{
        return Client.getResponse("--request 3").getMessage();
    }
    public static Message ignoreSingleRequest() throws IOException{
        return Client.getResponse("--request --ignore 1").getMessage();        
    }
    public static Message ignoreMatchRequest() throws IOException{
        return Client.getResponse("--request --ignore 3").getMessage();
    }    
    public static Message getAllMessage() throws IOException{
        return Client.getResponse("getAllMessages").getMessage();
    }
}
