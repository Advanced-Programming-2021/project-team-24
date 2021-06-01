package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuController {
    @FXML
    AnchorPane menu;
    SplitPane splitPane;

    public void init() {
        Pane gamePane = new Pane();
        Pane deckPane = new Pane();
        Pane shopPane = new Pane();
        Pane scorePane = new Pane();
        gamePane.getStyleClass().add("duelBG");
        deckPane.getStyleClass().add("decksBG");
        shopPane.getStyleClass().add("shopBG");
        scorePane.getStyleClass().add("scoreboardBG");
        deckPane.getStyleClass().add("handCursor");
        deckPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new Controller().switchToSceneDecks(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ArrayList<Pane> panes = new ArrayList<>();
        panes.add(gamePane);
        panes.add(deckPane);
        panes.add(shopPane);
        panes.add(scorePane);
        splitPane = new SplitPane(gamePane, deckPane, shopPane, scorePane);
        splitPane.getDividers().get(0).setPosition(0.25);
        splitPane.getDividers().get(1).setPosition(0.5);
        splitPane.getDividers().get(2).setPosition(0.75);
        ArrayList<Double> positions = new ArrayList<Double>();
        positions.add(0.25);
        positions.add(0.5);
        positions.add(0.75);
        setTransition(gamePane, Arrays.asList(0.49, 0.73, 0.9));
        setTransition(scorePane, Arrays.asList(0.1, 0.27,0.51));
        setTransition(deckPane, Arrays.asList(0.18, 0.67,0.85));
        setTransition(shopPane, Arrays.asList(0.15, 0.33,0.82));
        int i = 0;
        int panesCount = panes.size();
        for (Pane pane : panes) {
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
        menu.getChildren().add(splitPane);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        splitPane.toBack();
    }
        private void setTransition (Pane pane, List < Double > positions){
            pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (int i = 0; i < positions.size(); i++) {
                        double target = positions.get(i);
                        KeyValue keyValue = new KeyValue(splitPane.getDividers().get(i).positionProperty(), target, Interpolator.EASE_BOTH);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), keyValue));
                        timeline.play();
                    }
                }
            });
        }

}
