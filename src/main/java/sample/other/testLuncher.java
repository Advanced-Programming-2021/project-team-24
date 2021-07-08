package sample.other;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class testLuncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane gamePane = new Pane();
        Pane deckPane = new Pane();
        Pane shopPane = new Pane();
        Pane scorePane = new Pane();
        ArrayList<Pane> panes = new ArrayList<>();
        panes.add(gamePane);
        panes.add(deckPane);
        panes.add(shopPane);
        panes.add(scorePane);
        SplitPane splitPane = new SplitPane(gamePane, deckPane, shopPane, scorePane);
        splitPane.getDividers().get(0).setPosition(0.25);
        splitPane.getDividers().get(1).setPosition(0.5);
        splitPane.getDividers().get(2).setPosition(0.75);
        ArrayList<Double> positions = new ArrayList<Double>();
        positions.add(0.25);
        positions.add(0.5);
        positions.add(0.75);
        int i = 0;
        int panesCount = panes.size();
        double beforeSize = (double) 1 / panesCount;
        double afterSize = beforeSize - (double) beforeSize / (panesCount - 1);
        for (Pane pane : panes) {
            int finalI = i;
            pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double target = 1;
                    double toBeSubtracted = 0.14;
                    for (int j = finalI; j < panesCount - 1; j++) {
                        target = positions.get(j) + toBeSubtracted;
                        toBeSubtracted += 0.03;
                        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(j).positionProperty(), target, Interpolator.EASE_BOTH);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
                        timeline.play();
                    }
                    toBeSubtracted = 0.14;
                    for (int j = finalI - 1; j >= 0; j--) {
                        target = positions.get(j) - toBeSubtracted;
                        toBeSubtracted += 0.03;
                        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(j).positionProperty(), target, Interpolator.EASE_BOTH);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
                        timeline.play();
                    }
                }
            });
            pane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (int j = 0; j < panesCount - 1; j++) {
                        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(j).positionProperty(), positions.get(j), Interpolator.EASE_BOTH);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
                        timeline.play();
                    }
                }
            });
            i++;
        }
        Parent root = FXMLLoader.load(getClass().getResource("scenes/mainMenu.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
