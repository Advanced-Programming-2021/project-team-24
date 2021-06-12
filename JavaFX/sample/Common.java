package sample;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Common {
    public static Stage stage;

    public static void defineHoverables(Node... nodes){
        for(Node node : nodes){
            node.getStyleClass().add("hoverable");
        }
    }
    public static void disableDivider(SplitPane splitPane) {
        splitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));
    }

    public static void back() throws IOException {
        new Controller().switchToSceneMainMenu();
    }

    public static void setStage(Stage stage){
        Common.stage = stage;
    }
}
