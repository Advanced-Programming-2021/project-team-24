package sample.gatherRoom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextArea;
import controller.Message;
import controller.TypeMessage;
import controller.client.Client;
import controller.client.GsonConverter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.gather.ChatMessage;
import model.user.User;
import sample.Common;
import sample.DuelController;
import server.ChatClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GatherRoomController {

    @FXML
    MFXTextField messageInput;
    @FXML
    GridPane messagesPane, onlinePane;
    @FXML
    JFXTextArea pinnedMessage;
    @FXML
    FontAwesomeIcon refresh,addRequest;
    User user;
    ArrayList<User> onlineMembers = new ArrayList<>();
    private void updateServer(MouseEvent mouseEvent) throws IOException {
        Message message;
        //System.out.println("ghabl " +Client.getResponse("update").getMessage().getContent());
        message = Client.getResponse("update").getMessage();
        //System.out.println("baaad " + Client.getResponse("update").getMessage().getContent());
        System.out.println(message.getContent());
        System.out.println(message.getTypeMessage());
        if(message.getTypeMessage()==TypeMessage.SUCCESSFUL){
            System.out.println("why???");
            System.out.println(Client.getResponse("getOpponent").getMessage().getContent());
            User opponent = (User) GsonConverter.deserialize(Client.getResponse("getOpponent").getMessage().getContent(),User.class);
            opponent = User.readUser(opponent.getUsername());
            System.out.println("why??????????????");
            switchToSceneDuel(mouseEvent,opponent);
        }
    }

    public GatherRoomController(User user){
        this.user = user;
    }
    public void initialize() throws IOException {
        update();
        addRequest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    //System.out.println("why?");
                    Message message = Client.getResponse("--request 1").getMessage();
                    if(message.getTypeMessage()==TypeMessage.ERROR)
                        message = Client.getResponse("--request --ignore 1").getMessage();
                    Common.showMessage(message, addRequest);
                    //System.out.println("okeeyeyeyeyeye");
                    updateServer(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    update();
                    updateServer(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public User getUser() {
        return user;
    }

    public MFXTextField getMessageInput() {
        return messageInput;
    }

    private void handlePin() {
        if(ChatMessage.getPinMessageId()!=-1){
            ChatMessage pinnedChatMessage = ChatMessage.getById(ChatMessage.getPinMessageId());
            pinnedMessage.setText(pinnedChatMessage.getUser().getUsername()+":"+pinnedChatMessage.getMessage());
        }
        else{
            pinnedMessage.setText("No pinned message");
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        //Client.getResponse("menu exit");
        Common.switchToSceneMainMenu(this.user);
    }
    public void sendMessage() throws IOException {
        ChatClient.sendMessage(GsonConverter.serialize(new ChatMessage(user,messageInput.getText(),ChatMessage.replyId))).getContent();
        update();
    }
    public void deleteMessage() throws IOException {

    }

    public void update() throws IOException {
        ChatMessage.replyId = -1;
        handlePin();
        ChatMessage.chatMessages = new Gson().fromJson(Client.getResponse("getAllMessages").getMessage().getContent(),new TypeToken<List<ChatMessage>>(){}.getType());
        messagesPane.getChildren().clear();
        for(int i=0;i<ChatMessage.chatMessages.size();i++){
            RowConstraints rowConstraints = new RowConstraints(100);
            messagesPane.getRowConstraints().add(rowConstraints);
            boolean isSelfSent = ChatMessage.chatMessages.get(i).getUser().getUsername().equals(user.getUsername());
            ChatMessageView messageView = new ChatMessageView(this,ChatMessage.chatMessages.get(i),isSelfSent);
            messagesPane.getChildren().add(messageView);
            GridPane.setRowIndex(messageView, i);
        }
        onlinePane.getChildren().clear();
        onlineMembers = new Gson().fromJson(Client.getResponse("getOnlineMembers").getMessage().getContent(),new TypeToken<List<User>>(){}.getType());
//        System.out.println("3333333333333333");
        //System.out.println(onlineMembers.size());
        for(int i=0;i<onlineMembers.size();i++){
            System.out.println(onlineMembers.get(i).getNickname());
            if(i%3==0) {
                RowConstraints rowConstraints = new RowConstraints(200);
                onlinePane.getRowConstraints().add(rowConstraints);
            }
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../" + onlineMembers.get(i).getImageAddress())));
            imageView.setFitHeight(80);
            imageView.setFitWidth(70);
//            javafx.scene.control.Label label = new Label(onlineMembers.get(i).getUsername());
            Label label = new Label(onlineMembers.get(i).getUsername());
            Font font = new Font("Source Code Pro Medium",12);
            label.setFont(font);
            VBox vBox = new VBox(imageView,label);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPrefWidth(100);
            vBox.setPrefHeight(200);
            vBox.setStyle("-fx-border-width: 1; -fx-border-color: #ebebeb;");
            onlinePane.getChildren().add(vBox);
            //javafx.scene.image.ImageView senderAvatar = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("../" + onlineMembers.get(i).getImageAddress())));

            GridPane.setRowIndex(vBox, i/3);
            GridPane.setColumnIndex(vBox, i%3);
        }

//<!--                              <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" style="-fx-border-width: 1; -fx-border-color: #ebebeb;">-->
//<!--                                 <children>-->
//<!--                                    <ImageView fitHeight="81.0" fitWidth="69.0">-->
//<!--                                       <image>-->
//<!--                                          <Image url="@../images/duel/characters/Chara001.dds1.png" />-->
//<!--                                       </image>-->
//<!--                                    </ImageView>-->
//<!--                                    <Label text="username">-->
//<!--                                       <font>-->
//<!--                                          <Font name="Source Code Pro Medium" size="12.0" />-->
//<!--                                       </font>-->
//<!--                                    </Label>-->
//<!--                                 </children>-->
//<!--                              </VBox>-->
    }




    Stage stage;
    Scene scene;
    public void switchToSceneDuel(MouseEvent event,User opponent) throws IOException {
        //System.out.println(Client.getResponse("menu enter Duel").getMessage().getContent());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/duel.fxml"));
        System.out.println(this.user.getNickname());
        System.out.println(opponent.getNickname());
        DuelController duelController = new DuelController(this.user,opponent);
        loader.setController(duelController);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //decksController.init();
        stage.setResizable(false);
        stage.show();
    }

}
