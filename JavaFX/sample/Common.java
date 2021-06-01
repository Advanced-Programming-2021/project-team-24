package sample;

import javafx.scene.Node;

public class Common {
    public static void defineHoverables(Node... nodes){
        for(Node node : nodes){
            node.getStyleClass().add("hoverable");
        }
    }
}
