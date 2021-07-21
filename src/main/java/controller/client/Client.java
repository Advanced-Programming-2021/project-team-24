package controller.client;

import model.Request;
import model.Response;
import model.Situation;
import model.user.Player;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String token = "";
    private static Situation currentSituation = Situation.LOGIN;
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7777);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    public static void setAfterResponse(Response response){
        if (response.getToken() != null) token = response.getToken();
        currentSituation = response.getCurrentSituation();
    }
    public static void runApp() {
        initializeNetwork();
    }

    public static Response getResponse(String command) throws IOException {
        Request request = new Request(command, currentSituation, token);
        if (command.equals("menu exit") && currentSituation == Situation.LOGIN) System.exit(0);
        dataOutputStream.writeUTF(GsonConverter.serialize(request));
        dataOutputStream.flush();
        String result = dataInputStream.readUTF();
        Response response = (Response) GsonConverter.deserialize(result, Response.class);
        setAfterResponse(response);
        return response;
    }
}
