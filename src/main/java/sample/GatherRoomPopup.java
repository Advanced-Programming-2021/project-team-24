package sample;

import com.jfoenix.controls.JFXTextArea;
import controller.TypeMessage;
import controller.client.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.user.User;
import server.ChatClient;

import java.io.IOException;

public class GatherRoomPopup extends Parent {
    User user;
    JFXTextArea textArea;
    MFXTextField textField;
    public GatherRoomPopup(User user) {
        this.user = user;
        textArea = new JFXTextArea();
        textArea.setPrefHeight(250);
        textArea.setPrefWidth(250);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        VBox.setMargin(textArea,new Insets(0,0,10,0));
        textField = new MFXTextField();
        textField.setAlignment(Pos.CENTER);
        textField.setPromptText("Enter Message");
        HBox.setMargin(textField,new Insets(0,10,0,0));
        FontAwesomeIcon sendIcon = new FontAwesomeIcon();
        sendIcon.setIconName("SHARE_SQUARE");
        sendIcon.setSize("2em");
        FontAwesomeIcon refresh = new FontAwesomeIcon();
        refresh.setIconName("REFRESH");
        refresh.setSize("2em");
        HBox sendMessage = new HBox(textField,sendIcon,refresh);
        sendMessage.setAlignment(Pos.CENTER);
        sendMessage.setPrefWidth(30);
        sendMessage.setPrefHeight(160);
        Label label = new Label();
        Font font = new Font(15);
        label.setText("Request Duel");
        VBox.setMargin(label,new Insets(10,0,0,0));
        MFXButton button1 = new MFXButton();
        button1.setPrefHeight(25);
        button1.setPrefWidth(100);
        button1.setStyle("-fx-background-color: #0051ff");
        button1.setText("3Round");
        button1.setTextFill(Color.WHITE);
        HBox.setMargin(button1,new Insets(0,10,0,0));
        MFXButton button2 = new MFXButton();
        button2.setPrefHeight(25);
        button2.setPrefWidth(100);
        button2.setStyle("-fx-background-color: #0051ff");
        button2.setText("1Round");
        button2.setTextFill(Color.WHITE);
        label.setFont(font);
        button1.setFont(font);
        button2.setFont(font);
        HBox duelButtons = new HBox(button1,button2);
        VBox.setMargin(duelButtons,new Insets(5,0,0,0));
        VBox vBox = new VBox(textArea,sendMessage,label,duelButtons);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(160);
        vBox.setPrefHeight(200);
        this.getChildren().add(vBox);
        sendIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //server.Chat.sendMessage("send --message "+ textField.getText(),user);
                try {
                    ChatClient.sendMessage(textField.getText());
                    //Client.getResponse("send --message "+ textField.getText());
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    ChatClient.sendSingleRequest();
                    //System.out.println(Client.getResponse("--request 1").getMessage().getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if(Client.getResponse("update").getMessage().getTypeMessage()== TypeMessage.SUCCESSFUL){
                           //switchToSceneDuel(mouseEvent,Client.getResponse("getOpponent").getMessage().getContent());
                    }
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Stage stage;
    Scene scene;
    public void switchToSceneDuel(MouseEvent event,User opponent) throws IOException {
        System.out.println(Client.getResponse("menu enter Duel").getMessage().getContent());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/duel.fxml"));
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
    private void update() throws IOException {
        //textArea.setText(Client.getResponse("getAllMessages").getMessage().getContent());
        textArea.setText(ChatClient.getAllMessage().getContent());
        textField.setText("");
    }
}
