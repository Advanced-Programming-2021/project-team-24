package sample;

import controller.DeckController;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.util.List;


public class DecksController {
    @FXML
    private ImageView blackBack;
    @FXML
    private ImageView brownBack;
    @FXML
    private ImageView card;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private DeckControls deckControls;
    @FXML HBox newDeck;
    @FXML GridPane decks;
    @FXML
    Group activeDeck;

    User user;

    public DecksController(User user){
        this.user = user;
        System.out.println(user.getUsername());
    }

    private RotateTransition rotateLeft = new RotateTransition(), rotateRight = new RotateTransition();

    public void disableDivider() {
        splitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));
    }

    private void rotateInit(RotateTransition rotate) {
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(500);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDuration(Duration.seconds(10));
    }

    public void rotateLeft(MouseEvent event) throws IOException {
        rotateInit(rotateLeft);
        rotateLeft.setNode(blackBack);
        rotateLeft.play();
    }

    public void rotateRight(MouseEvent event) throws IOException {
        rotateInit(rotateRight);
        rotateRight.setNode(brownBack);
        rotateRight.play();
    }

    public void pauseRotateLeft(MouseEvent event) {
        rotateLeft.pause();
    }

    public void pauseRotateRight(MouseEvent event) {
        rotateRight.pause();
    }



    public void testMethod(){
        System.out.println("Okab");
    }



    public void back(MouseEvent mouseEvent) throws IOException {
        Common.switchToSceneMainMenu(this.user);
    }

    public void initialize() {
        update();
        DeckController deckController = new DeckController(user.getDecks());
        newDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Input input = new Input();
                PopOver popOver = new PopOver(input);
                popOver.show(newDeck);
                input.getHBox().requestFocus();
                input.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(deckController.create(input.getInput()).getContent());
                        update();
                    }
                });
            }
        });
        disableDivider();
    }

    void update() {
        decks.getChildren().clear();
        activeDeck.getChildren().clear();
        List<Deck> decksList = user.getDecks().getDecks();
        System.out.println(decksList.size());
        int columnCardCount = 3;
        //int rowCardCount = decksList.size()/columnCardCount;
        Deck active = user.getDecks().getActiveDeck();
        Card card;
        if(active==null){
            card = null;
        }
        else {
            if (active.getMainCards().size() == 0)
                card = null;
            else
                card = active.getMainCards().get(0);
            activeDeck.getChildren().add(new sample.Deck(true,active.getName(),Common.handleImage(card),user,this));
        }

        for (int i = 0; i < decksList.size(); i++) {

            if(decksList.get(i).getMainCards().size()==0)
                card = null;
            else
                card = decksList.get(i).getMainCards().get(0);
            sample.Deck deck = new sample.Deck(false,decksList.get(i).getName(),Common.handleImage(card),user,this);
            decks.getChildren().add(deck);
            System.out.println(decks.getChildren().toString());
            GridPane.setColumnIndex(deck, i % columnCardCount);
            GridPane.setRowIndex(deck, i / columnCardCount);
        }
    }
}
