package sample;

import controller.MainMenuController;
import controller.Message;
import controller.TypeMessage;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.card.Card;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuController {
    @FXML
    AnchorPane menu;
    @FXML
    SplitPane splitPane;
    @FXML
    Label username;
    @FXML
    ImageView profilePhoto;
    User user;
    private Stage stage;
    private Scene scene;

    public MenuController(User user) {
        this.user = user;
    }


    public void initialize() {
        profilePhoto.setImage(new Image(getClass().getResourceAsStream(user.getImageAddress())));
        profilePhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Profile profile = new Profile(user);
                PopOver popOver = new PopOver(profile);
                popOver.show(profilePhoto);
                profile.getHBox().requestFocus();
                controller.ProfileMenu profileMenu = new controller.ProfileMenu(user);
                profile.getButton2().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (profile.getInput2().split(" ").length != 2) System.out.println("failed");
                        else
                            System.out.println(profileMenu.changePassword(profile.getInput2().split(" ")[0], profile.getInput2().split(" ")[1]).getContent());
                    }
                });
                profile.getButton1().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(profileMenu.changeNickname(profile.getInput1()).getContent());
                        profile.update();
                    }
                });
            }
        });
        username.setText(user.getUsername());
        Pane gamePane = new Pane();
        Pane deckPane = new Pane();
        Pane shopPane = new Pane();
        Pane scorePane = new Pane();
        gamePane.getStyleClass().add("duelBG");
        gamePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseEvent originalMouseEvent = mouseEvent;
                Input input = new Input("Enter opponent username");
                PopOver popOver = new PopOver(input);
                popOver.show(gamePane);
                input.getHBox().requestFocus();
                input.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Message message = new MainMenuController().createDuel(user, User.readUser(input.getInput()), "1");
                        System.out.println(message.getContent());
                        Common.showMessage(message, gamePane);
                        if (message.getTypeMessage() == TypeMessage.SUCCESSFUL) {

                                Coin coin = new Coin();
                                PopOver popOver = new PopOver(coin);
                                popOver.show(profilePhoto);
                                coin.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    try {
                                        switchToSceneDuel(originalMouseEvent, User.readUser(input.getInput()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    }
                                });

                        }
                    }
                });
            }
        });
        deckPane.getStyleClass().add("decksBG");
        shopPane.getStyleClass().add("shopBG");
        shopPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    switchToSceneShop(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        scorePane.getStyleClass().add("scoreboardBG");
        scorePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    switchToSceneScoreboard(mouseEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        deckPane.getStyleClass().add("handCursor");
        deckPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    switchToSceneDecks(mouseEvent);
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
        setTransition(scorePane, Arrays.asList(0.1, 0.27, 0.51));
        setTransition(deckPane, Arrays.asList(0.18, 0.67, 0.85));
        setTransition(shopPane, Arrays.asList(0.15, 0.33, 0.82));
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

    private void setTransition(Pane pane, List<Double> positions) {
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

    public void switchToSceneDecks(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/decks.fxml"));
        DecksController decksController = new DecksController(this.user);
        loader.setController(decksController);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //decksController.init();
        stage.setResizable(false);
        stage.show();
    }

    public void switchToSceneShop(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/shop.fxml"));
        twoPaneCards twoPaneCards = new twoPaneCards(this.user, null, Card.getAllCards().size());
        loader.setController(twoPaneCards);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //decksController.init();
        stage.setResizable(false);
        stage.show();
    }

    public void switchToSceneDuel(MouseEvent event,User opponent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/duel.fxml"));
        DuelController duelController = new DuelController(this.user,opponent);
        loader.setController(duelController);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //decksController.init();
        stage.setResizable(false);
        stage.show();
    }

    public void switchToSceneScoreboard(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/scoreboard.fxml"));
        ScoreboardController scoreboardController = new ScoreboardController(this.user);
        loader.setController(scoreboardController);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToSceneSignin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/signin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneImportExport(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/importExport.fxml"));
        ImportExport importExport = new ImportExport(this.user);
        loader.setController(importExport);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
