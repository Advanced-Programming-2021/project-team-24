package sample;

import com.jfoenix.controls.JFXButton;
import controller.LoginController;
import controller.Message;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CardCreator {
    @FXML
    MFXTextField username,nickname,password;
    @FXML
    JFXButton submit;

    private Stage stage;
    private Scene scene;

    public void submit(MouseEvent mouseEvent) {
        LoginController loginController = new LoginController();
        Message message = loginController.register(username.getText(),password.getText(),nickname.getText());
        System.out.println(message.getContent());
        Common.showMessage(message,submit);
    }

    public void switchToSceneSignin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/signin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
