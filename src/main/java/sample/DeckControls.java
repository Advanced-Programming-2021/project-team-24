package sample;

import controller.DeckController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;

public class DeckControls extends Group {

    private double borderPaneLayoutX = 36;

    private double borderPaneLayoutY = 13;

    private double borderPanePrefHeight = 136;

    private double borderPanePrefWidth = 96;

    public double getBorderPaneLayoutY() {
        return borderPaneLayoutY;
    }

    public void setBorderPaneLayoutY(double borderPaneLayoutY) {
        borderPane.setLayoutY(borderPaneLayoutY);
        this.borderPaneLayoutY = borderPaneLayoutY;
    }

    public double getBorderPaneLayoutX() {
        return borderPaneLayoutX;
    }

    public void setBorderPaneLayoutX(double borderPaneLayoutX) {
        borderPane.setLayoutX(borderPaneLayoutY);
        this.borderPaneLayoutX = borderPaneLayoutX;
    }

    public double getBorderPanePrefWidth() {
        return borderPanePrefWidth;
    }

    public void setBorderPanePrefWidth(double borderPanePrefWidth) {
        borderPane.setPrefWidth(borderPanePrefWidth);
        this.borderPanePrefWidth = borderPanePrefWidth;
    }

    public double getBorderPanePrefHeight() {
        return borderPanePrefHeight;
    }

    public void setBorderPanePrefHeight(double borderPanePrefHeight) {
        borderPane.setPrefHeight(borderPanePrefHeight);
        this.borderPanePrefHeight = borderPanePrefHeight;
    }

    @FXML
    private boolean isActive;

    String name;
    private Stage stage;
    private Scene scene;
    BorderPane borderPane = new BorderPane();
    String deckName;

    public void setActive(boolean active) {
        isActive = active;
    }


    public DeckControls(boolean isActive, String name, User user, DecksController deckController,String deckName) {
        borderPane.setLayoutX(borderPaneLayoutX);
        borderPane.setLayoutY(borderPaneLayoutY);
        borderPane.setPrefHeight(borderPanePrefHeight);
        borderPane.setPrefWidth(borderPanePrefWidth);
        Label labelActivate = new Label();
        this.deckName= deckName;
        if (isActive)
            labelActivate.setText("DEACTIVATE");
        else
            labelActivate.setText("ACTIVATE");
        labelActivate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(new DeckController(user.getDecks()).active(name).getContent());
                deckController.update();
            }
        });
        labelActivate.setAlignment(Pos.CENTER);
        Font font = new Font("Source Sans Pro Black", 16);
        labelActivate.setFont(font);
        labelActivate.setTextFill(Color.WHITE);
        borderPane.setTop(labelActivate);
        BorderPane.setAlignment(labelActivate, Pos.CENTER);
        FontAwesomeIcon eye = new FontAwesomeIcon();
        eye.setFill(Color.WHITE);
        eye.setSize("4em");
        eye.setWrappingWidth(51.2);
        eye.setIconName("EYE");
        borderPane.setCenter(eye);
        FontAwesomeIcon trash = new FontAwesomeIcon();
        trash.setFill(Color.WHITE);
        trash.setSize("2em");
        trash.setWrappingWidth(20.0);
        trash.setIconName("TRASH");
        trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Question message = new Question();
                PopOver popOver = new PopOver(message);
                popOver.show(trash);
                message.getLeftButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        popOver.hide();
                    }
                });
                message.getRightButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(new DeckController(user.getDecks()).delete(deckName).getContent());
                        deckController.update();
                    }
                });
            }
        });
        FontAwesomeIcon edit = new FontAwesomeIcon();
        edit.setFill(Color.WHITE);
        edit.setSize("2em");
        edit.setWrappingWidth(20.0);
        edit.setIconName("EDIT");
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/shop.fxml"));
                twoPaneCards twoPaneCards = new twoPaneCards(user, deckName, user.getCards().size());
                loader.setController(twoPaneCards);
                Parent root = null;
                try {
                    root = loader.load();
                    scene = new Scene(root);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        HBox hBox = new HBox(edit, trash);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefHeight(51.0);
        hBox.setPrefWidth(118.0);
        hBox.setSpacing(30);
        borderPane.setBottom(hBox);
        this.getChildren().add(borderPane);
        this.setOpacity(0);
        DeckControls deckControls = this;
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverDeck();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitHoverDeck();
            }
        });

        Common.defineHoverables(eye, trash, edit, labelActivate);
    }

    public void hoverDeck() {
        this.setOpacity(1);
        this.getParent().getChildrenUnmodifiable().get(0).setOpacity(0.3);
        this.getParent().getChildrenUnmodifiable().get(1).setOpacity(0.3);
    }

    public void exitHoverDeck() {
        this.setOpacity(0);
        this.getParent().getChildrenUnmodifiable().get(0).setOpacity(1);
        this.getParent().getChildrenUnmodifiable().get(1).setOpacity(1);
    }

}
