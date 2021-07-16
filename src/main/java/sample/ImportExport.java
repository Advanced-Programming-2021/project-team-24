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
import model.user.User;

import java.io.IOException;

public class ImportExport {
    @FXML
    MFXTextField importCard,exportCard;
    @FXML
    JFXButton importButton,exportButton;

    private Stage stage;
    private Scene scene;
    User user;
    controller.ImportExportController importExportController;

    public ImportExport(User user){
        this.user = user;
        this.importExportController = new controller.ImportExportController(user);
    }

    public void doImport(MouseEvent mouseEvent) {
        Message message = importExportController.importCard(importCard.getText());
        System.out.println(message.getContent());
        Common.showMessage(message,importButton);
    }

    public void doExport(MouseEvent mouseEvent) {
        Message message = importExportController.exportCard(importCard.getText());
        System.out.println(message.getContent());
        Common.showMessage(message,exportButton);
    }

    public void switchToSceneSignin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/signin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
            Common.switchToSceneMainMenu(this.user);
    }
}
