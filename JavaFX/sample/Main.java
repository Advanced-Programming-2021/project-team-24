package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shop.fxml"));
        loader.setController(new ShopController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
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
