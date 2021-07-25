package sample.gatherRoom;

import com.jfoenix.controls.JFXTextArea;
import controller.client.Client;
import controller.client.GsonConverter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import model.gather.ChatMessage;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.util.HashMap;

public class ChatMessageView extends Parent {
    ChatMessage message;
    public ChatMessageView(GatherRoomController grc,ChatMessage message, boolean isSelfSent) {
        this.message = message;
        Label senderUsername = new Label(message.getUser().getUsername());
        HBox.setMargin(senderUsername, new Insets(0, 80, 0, 0));
        FontAwesomeIcon trash = handleIcon("TRASH");
        FontAwesomeIcon pin = handleIcon("THUMB_TACK");
        FontAwesomeIcon edit = handleIcon("EDIT");
        FontAwesomeIcon reply = handleIcon("REPLY");
        HBox header = new HBox(senderUsername,trash,pin,edit,reply);
        Font font = new Font("System Bold", 12);
        senderUsername.setFont(font);
        JFXTextArea messageText = new JFXTextArea(message.getMessage());
        messageText.setEditable(false);
        messageText.setPrefHeight(60);
        messageText.setPrefWidth(170);
        messageText.setStyle("-fx-background-color: #ffffff; -fx-padding: 5");
        messageText.setUnFocusColor(Color.BLACK);
        VBox vBox;
        if (message.getInReplyToId() != -1) {
            Line line1 = new Line();
            line1.setEndX(100);
            line1.setStartX(-100);
            line1.setStroke(Color.web("#0084ff"));
            Line line2 = new Line();
            line2.setEndX(100);
            line2.setStartX(-100);
            line2.setStroke(Color.web("#0084ff"));
            ChatMessage replyChatMessage = ChatMessage.getById(message.getInReplyToId());
            JFXTextArea replyMessage = new JFXTextArea("in reply to " + replyChatMessage.getUser().getUsername() + ":\n" + replyChatMessage.getMessage());
            replyMessage.setEditable(false);
            replyMessage.setPrefHeight(35);
            replyMessage.setPrefWidth(170);
            replyMessage.setStyle("-fx-background-color: #e4eaf0; -fx-padding:  3 3 3 10");
            replyMessage.setUnFocusColor(Color.BLACK);
            vBox = new VBox(header, line1, replyMessage, line2, messageText);
        } else {
            vBox = new VBox(header, messageText);
        }
        vBox.setPrefHeight(80);
        vBox.setPrefWidth(170);
        ImageView senderAvatar = new ImageView(new Image(getClass().getResourceAsStream("../" + message.getUser().getImageAddress())));
        senderAvatar.setFitWidth(45);
        senderAvatar.setFitHeight(55);
        HBox.setMargin(senderAvatar, new Insets(0, 10, 0, 10));
        HBox hBox;
        if (isSelfSent) {
            hBox = new HBox(vBox, senderAvatar);
            hBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            hBox = new HBox(senderAvatar, vBox);
            hBox.setAlignment(Pos.CENTER_LEFT);
        }
        hBox.setPrefHeight(80);
        hBox.setPrefWidth(512);
        this.getChildren().add(hBox);

        pin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println(ChatMessageView.this.message.getId());
                    //System.out.println(ChatMessageView.this.message.getMessage());
                    System.out.println(Client.getResponse("pin --number "+ChatMessageView.this.message.getId()).getMessage().getContent());
                    ChatMessage.setPinMessageId(ChatMessageView.this.message.getId());
                    grc.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    System.out.println(ChatMessageView.this.message.getId());
                    ChatMessage.replyId = ChatMessageView.this.message.getId();
                    //grc.update();
            }
        });

        trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    //System.out.println(ChatMessageView.this.message.getMessage());
                    System.out.println(Client.getResponse("delete --number "+ChatMessageView.this.message.getId()).getMessage().getContent());
//                    ChatMessage.setPinMessageId(ChatMessageView.this.message.getId());
                    grc.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    //System.out.println(ChatMessageView.this.message.getMessage());
                    System.out.println(Client.getResponse("edit --number "+ChatMessageView.this.message.getId()+" --message "+ GsonConverter.serialize(new ChatMessage(message.getUser(),grc.getMessageInput().getText(),message.getInReplyToId()))).getMessage().getContent());
//                    ChatMessage.setPinMessageId(ChatMessageView.this.message.getId());
                    grc.update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        senderAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User user = ChatMessageView.this.message.getUser();
                Label username = new Label("USERNAME: "+user.getUsername());
                Label nickname = new Label("NICKNAME: "+user.getNickname());
                VBox vBox1 = new VBox(username,nickname);
                vBox1.setAlignment(Pos.CENTER);
                VBox.setMargin(nickname,new Insets(10,0,0,0));
                vBox1.setStyle("-fx-padding: 10");
                PopOver popOver = new PopOver(vBox1);
                popOver.show(senderAvatar);
            }
        });

    }

    public FontAwesomeIcon handleIcon(String trash2) {
        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setIconName(trash2);
        HBox.setMargin(icon, new Insets(0, 5, 0, 0));
        //ChatMessage.iconMap.put(icon,this.message);
        return icon;
    }
}
