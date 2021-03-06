package sample;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import controller.LoginController;
import controller.Message;
import controller.TypeMessage;
import controller.client.Client;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;

public class SignInController {
    @FXML
    MFXTextField username,password;
    @FXML
    JFXButton submit;

    private Stage stage;
    private Scene scene;


    public void submit(MouseEvent mouseEvent) throws IOException {
        LoginController loginController = new LoginController();
        //Message message = (Message) loginController.login(username.getText(),password.getText()).get(0);
        //System.out.println("user login --username "+username.getText()+" --password "+password.getText());
        Message message = Client.getResponse("user login --username "+username.getText()+" --password "+password.getText()).getMessage();
        System.out.println(message.getContent());
        if(message.getTypeMessage() == TypeMessage.SUCCESSFUL){
            //Common.switchToSceneMainMenu((User) loginController.login(username.getText(),password.getText()).get(1));
            //Common.switchToSceneMainMenu( new Gson().fromJson(Client.getResponse("getUser").getMessage().getContent(), User.class));
            Common.switchToSceneMainMenu(User.readUser(username.getText()));
        }
        else
            Common.showMessage(message,submit);
    }



    public void switchToSceneSignup(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
