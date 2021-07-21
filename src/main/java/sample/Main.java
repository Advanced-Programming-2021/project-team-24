package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.user.User;

import static controller.client.Client.initializeNetwork;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        initializeNetwork();
        User.initialize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/scenes/signin.fxml"));
//        ShopController shopController = new ShopController();
//        loader.setController(shopController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
//        scene.setOnMouseClicked(shopController);
//        scene.setOnMouseDragged(shopController);
//        scene.setOnMouseEntered(shopController);
//        scene.setOnMouseExited(shopController);
//        scene.setOnMouseMoved(shopController);
//        scene.setOnMousePressed(shopController);
//        scene.setOnMouseReleased(shopController);
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        Common.setStage(stage);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
