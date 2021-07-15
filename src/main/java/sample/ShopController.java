package sample;

import controller.DeckController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.card.Card;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopController implements EventHandler<MouseEvent> {
    @FXML
    GridPane myCards;
    @FXML
    AnchorPane shopCards;
    @FXML
    FontAwesomeIcon left,right,buy;
    @FXML
    SplitPane splitPane;
    @FXML
    HBox menuBar;
    @FXML
    Label cost,coins;
    User user;
    ImageView imageView;
    double windowWidth = 1024;
    double windowHeight = 576;
    double splitWidth = windowWidth / 2;
    double cardRatio = 1.5;
    int cardMargin = 20;
    int columnCardCount = 5;
    int rowCardCount;
    List<Card> targetCards;
    String deckName;
    Image card;
    int cardsCount;

    public ShopController(User user,String deckName,int cardsCount){
        this.user = user;
        this.deckName = deckName;
        this.cardsCount = cardsCount;
    }

    public static void setPrespective(ImageView imageView, double x, double y, double angle) {
        final double w = imageView.getFitWidth();
        final double h = imageView.getFitHeight();
        final double a = (2 * w + 2 * d) / (2 * d + w);
        final double b = 2 - a;
        double f = angle / 90;
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();

        perspectiveTransform.setUly(y + (f * (a - 1)) * h / 2);
        perspectiveTransform.setUry(y + (f * (b - 1)) * h / 2);
        perspectiveTransform.setLly(y + h - (f * (a - 1)) * h / 2);
        perspectiveTransform.setLry(y + h - (f * (b - 1)) * h / 2);

        perspectiveTransform.setUlx(x - (1 - Math.abs(f)) * w / 2);
        perspectiveTransform.setUrx(x + (1 - Math.abs(f)) * w / 2);
        perspectiveTransform.setLlx(x - (1 - Math.abs(f)) * w / 2);
        perspectiveTransform.setLrx(x + (1 - Math.abs(f)) * w / 2);
        imageView.setEffect(perspectiveTransform);
    }
    List<Card> cardsList;
    static int d = 100;
    int cardWidth = 125;
    int selectedCard;
    double curve;
    double size;
    controller.ShopMenu shopMenu;
    DeckController deckController;
    @FXML
    FontAwesomeIcon menuIcon,menuIcon2;
    @FXML
    Label menuText;
    int n;
    ArrayList<ImageView> cards = new ArrayList<ImageView>();
    public void initialize() {
        if(deckName == null) {
            targetCards = user.getCards();
            cardsList = Card.getAllCards();
            coins.setText(String.valueOf(user.getCoin()));
        }
        else {
            targetCards = user.getDecks().getDeckByName(deckName).getMainCards();
            cardsList = user.getCards();
            menuIcon.setIconName("TH");
            menuText.setText("DECK EDITOR");
            buy.setIconName("PLUS_CIRCLE");
            coins.setText(String.valueOf(targetCards.size()));
            menuIcon2.setIconName("SORT_NUMERIC_ASC");
        }
        n = cardsCount / 2;
        selectedCard = n;
        curve = n * 10;
        size = ((double)cardWidth*(2*n+1))/2;
        rowCardCount = targetCards.size()/5;
        shopMenu = new controller.ShopMenu(user);
        deckController = new DeckController(user.getDecks());
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);
        left.setEffect(dropShadow);
        left.setOpacity(0.8);
        right.setEffect(dropShadow);
        right.setOpacity(0.8);
        buy.setOpacity(0);
        cost.setOpacity(0);
        buy.setEffect(dropShadow);
        cost.setEffect(dropShadow);
        for (int i = 1; i <= 2 * n; i++) {
            imageView = new ImageView(new Image(getClass().getResourceAsStream(Common.handleImage(cardsList.get(i-1)))));
            imageView.setFitWidth(cardWidth);
            imageView.setFitHeight(imageView.getFitWidth() * cardRatio);
            imageView.setLayoutX(size * i / (2 * n + 2)-(size-windowWidth/2)/2);
            setPrespective(imageView, 0, 0, (curve / n) * i - curve * (((float) n + 1) / (float) n));
            shopCards.getChildren().add(imageView);
            cards.add(imageView);
            if(i>n+1){
                shopCards.getChildren().get(i+1).toBack();
            }
            if(i==n+1){
                shopCards.getChildren().get(0).toFront();
                shopCards.getChildren().get(0).toFront();
            }
        }
        AnchorPane.setTopAnchor(myCards, (double) cardMargin + menuBar.getPrefHeight());
        AnchorPane.setLeftAnchor(myCards, (double) cardMargin);
        AnchorPane.setRightAnchor(myCards, (double) cardMargin);
        for (int i = 0; i < columnCardCount; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints((splitWidth - 2 * cardMargin) / columnCardCount);
            myCards.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowCardCount; i++) {
            RowConstraints rowConstraints = new RowConstraints(((splitWidth - 2 * cardMargin) / columnCardCount) * cardRatio);
            myCards.getRowConstraints().add(rowConstraints);
        }
        update();
        left.toFront();
        right.toFront();
        buy.toFront();
        cost.toFront();
        handleMouse();
        handleCardHover();
    }

    private void update() {
        myCards.getChildren().clear();
        for (int i = 0; i < targetCards.size(); i++) {
            imageView = new ImageView(new Image(getClass().getResourceAsStream(Common.handleImage(targetCards.get(i)))));
            imageView.setFitWidth(splitWidth / columnCardCount - cardMargin);
            imageView.setFitHeight(imageView.getFitWidth() * cardRatio);
            myCards.getChildren().add(imageView);
            GridPane.setColumnIndex(imageView, i % columnCardCount);
            GridPane.setRowIndex(imageView, i / columnCardCount);
        }
        if(deckName==null)
            coins.setText(String.valueOf(user.getCoin()));
        else
            coins.setText(String.valueOf(targetCards.size()));
    }


    public void left(MouseEvent mouseEvent){
        cards.get(selectedCard).setOpacity(1);
        cards.get(selectedCard).setOnMouseExited(null);
        cards.get(selectedCard).setOnMouseEntered(null);
        buy.setOpacity(0);
        cost.setOpacity(0);
        if(selectedCard==2*n) return;
        selectedCard++;
        handleCardHover();
        cards.get(selectedCard).toFront();
        left.toFront();
        right.toFront();
        buy.toFront();
        cost.toFront();
        for (int i = 1; i < shopCards.getChildren().size() - 3; i++) {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(cards.get(i-1));
            translateTransition.setByX(-1*size / (2 * n + 2));
            translateTransition.setDuration(Duration.millis(800));
            translateTransition.setCycleCount(1);
            translateTransition.play();
            Prespective prespective = new Prespective(cards.get(i-1), (curve / n) * (i+k) - curve * (((float) n + 1) / (float) n), (curve / n) * (i+k-1) - curve * (((float) n + 1) / (float) n));
            prespective.play();
        }
        k--;
    }

    int k = 0;
    public void right(MouseEvent mouseEvent) throws IOException {
        cards.get(selectedCard).setOpacity(1);
        cards.get(selectedCard).setOnMouseExited(null);
        cards.get(selectedCard).setOnMouseEntered(null);
        buy.setOpacity(0);
        cost.setOpacity(0);
        if(selectedCard == 0) return;
        selectedCard--;
        handleCardHover();
        cards.get(selectedCard).toFront();
        left.toFront();
        right.toFront();
        buy.toFront();
        cost.toFront();
        for (int i = 1; i < shopCards.getChildren().size() - 3; i++) {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(cards.get(i-1));
            translateTransition.setByX(size / (2 * n + 2));
            translateTransition.setDuration(Duration.millis(800));
            translateTransition.setCycleCount(1);
            translateTransition.play();
            Prespective prespective = new Prespective(cards.get(i-1), (curve / n) * (i+k) - curve * (((float) n + 1) / (float) n), (curve / n) * (i+k+1) - curve * (((float) n + 1) / (float) n));
            prespective.play();
        }
        k++;
    }

    private void handleMouse() {
        buy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cards.get(selectedCard).setOpacity(0.7);
                cost.setOpacity(1);
                buy.setOpacity(1);
            }
        });
        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println(monsterCards.get(selectedCard).getName());
                String cardName = cardsList.get(selectedCard).getName();
                if(deckName==null) {
                    System.out.println(shopMenu.buyCard(cardName).getContent());
                }
                else System.out.println(deckController.addCard(cardName, deckName, true).getContent());
                update();
            }
        });
    }

    private void handleCardHover(){
        cards.get(selectedCard).setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cards.get(selectedCard).setOpacity(0.7);
                cost.setOpacity(1);
                buy.setOpacity(0.8);
            }
        });
        cards.get(selectedCard).setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cards.get(selectedCard).setOpacity(1);
                cost.setOpacity(0);
                buy.setOpacity(0);
            }
        });
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if(deckName==null)
            Common.switchToSceneMainMenu(this.user);
        else
            switchToSceneDecks(mouseEvent);
    }

    @Override
    public void handle(MouseEvent mouseEvent){
        Common.disableDivider(splitPane);
    }

    Scene scene;
    Stage stage;

    public void switchToSceneDecks(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/decks.fxml"));
        DecksController decksController = new DecksController(this.user);
        loader.setController(decksController);
        Parent root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //decksController.init();
        stage.setResizable(false);
        stage.show();
    }
}
