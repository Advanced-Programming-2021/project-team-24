package sample;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.user.User;


public class Profile extends Parent {
    HBox hBox2;
    JFXButton changePassword,changeNickname;
    MFXTextField password,nickname;
    User user;
    public Profile(User user){
        this.user = user;
        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setIconName("USER");
        icon.setSelectionFill(Color.BLACK);
        icon.setSize("4em");
        icon.setWrappingWidth(45);
        Label username = new Label();
        Font font1 = new Font("Quicksand-Bold",16.0);
        Font font2 = new Font("Quicksand-Bold",12.0);
        username.setText(user.getUsername());
        username.setFont(font1);

        nickname = new MFXTextField();
        nickname.setPromptText("Wanna change nickname "+user.getNickname()+"?");
        nickname.setPrefWidth(430);
        nickname.setFont(font2);
        changeNickname = new JFXButton();
        changeNickname.setPrefWidth(140);
        changeNickname.setStyle("-fx-background-color: #4038E6;");
        changeNickname.setTextFill(Color.WHITE);
        changeNickname.setText("ChangeNickname");
        changeNickname.setFont(font2);
        HBox hBox1 = new HBox(nickname,changeNickname);
        hBox1.setPrefWidth(320);
        HBox.setMargin(nickname,new Insets(0,20,0,0));

        password = new MFXTextField();
        password.setPromptText("Enter old&new password with a space between to change password");
        password.setPrefWidth(430);
        password.setFont(font2);
        changePassword = new JFXButton();
        changePassword.setPrefWidth(140);
        changePassword.setStyle("-fx-background-color: #4038E6;");
        changePassword.setTextFill(Color.WHITE);
        changePassword.setText("ChangePassword");
        changePassword.setFont(font2);
        HBox hBox3 = new HBox(password,changePassword);
        hBox3.setPrefWidth(320);
        HBox.setMargin(password,new Insets(0,20,0,0));

        VBox vBox = new VBox(username,hBox1,hBox3);
        VBox.setMargin(hBox1,new Insets(0,0,10,0));
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(120);
        vBox.setPrefWidth(600);
        hBox2 = new HBox(icon,vBox);
        HBox.setMargin(icon,new Insets(20,10,0,0));
        hBox2.setPrefWidth(700);
        hBox2.setPrefHeight(120);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setStyle("-fx-background-color: white;");
        this.getChildren().add(hBox2);
    }
    public HBox getHBox(){
        return hBox2;
    }
    public JFXButton getButton1(){
        return changeNickname;
    }
    public String getInput1(){
        return nickname.getText();
    }
    public JFXButton getButton2(){
        return changePassword;
    }
    public String getInput2(){
        return password.getText();
    }
    public void update(){
        nickname.setPromptText("Wanna change nickname "+user.getNickname()+"?");
    }

}
