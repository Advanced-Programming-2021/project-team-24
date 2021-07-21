package controller.server;

import controller.Message;
import controller.TypeMessage;
import controller.process.*;
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
import java.util.regex.Matcher;

public class Server {
    private static User user;
    public static void runApp() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                Socket socket = serverSocket.accept();
                startNewThread(serverSocket, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startNewThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                getInputAndProcess(dataInputStream, dataOutputStream);
                dataInputStream.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private static void setUser(Request request){
        user = TokenManager.getUser(request.getToken());
    }
    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while (true) {
            String input = dataInputStream.readUTF();
            Request request = (Request) GsonConverter.deserialize(input, Request.class);
            setUser(request);
            Response response = getResponse(request);
            dataOutputStream.writeUTF(GsonConverter.serialize(response));
            dataOutputStream.flush();
        }
    }
    private static Response getResponse(Request request){
        Situation situation = request.getCurrentSituation();
        if(Global.regexFind(request.getInput(), "updateUser (.+)")){
            Matcher matcher = Global.getMatcher(request.getInput(), "updateUser (.+)");
            matcher.find();
            User temp = (User) GsonConverter.deserialize(matcher.group(1), User.class);
            temp.addUser();
        }
        if(Global.regexFind(request.getInput(), "readUser (.+)")){
            Matcher matcher = Global.getMatcher(request.getInput(), "readUser (.+)");
            matcher.find();
            User temp = User.readUser(matcher.group(1));
            return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(temp)), situation);
        }
        Response response = null;
        switch (situation){
            case DECK:
                response = new DeckMenu(user).process(request.getInput());
                break;
            case DUEL:
                response = DuelMenu.getDuelMenuByUser(user).process(request.getInput());
                break;
            case SCOREBOARD:
                response = new ScoreboardMenu(user).process(request.getInput());
                break;
            case SHOP:
                response = new ShopMenu(user).process(request.getInput());
                break;
            case IMPORTEXPORT:
                response = new ImportExportMenu(user).process(request.getInput());
                break;
            case MAIN:
                response = new MainMenu(user).process(request);
                break;
            case LOGIN:
                response = new LoginMenu().process(request.getInput());
                break;
            case PROFILE:
                response = new ProfileMenu(user).process(request.getInput());
                break;
            case SIDEDECK:
                response = new SideDeckMenu(user).process(request.getInput());
                break;
        }
        return response;
    }
}
