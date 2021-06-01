package sample;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


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


    private RotateTransition rotateLeft = new RotateTransition(), rotateRight = new RotateTransition();

    public void init() {
        disableDivider();
    }

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

    public void hoverDeck(DeckControls deckControls) {
        deckControls.setOpacity(1);
        deckControls.getParent().getChildrenUnmodifiable().get(0).setOpacity(0.3);
        deckControls.getParent().getChildrenUnmodifiable().get(1).setOpacity(0.3);
    }

    public void testMethod(){
        System.out.println("Okab");
    }

    public void exitHoverDeck(DeckControls deckControls) {
        deckControls.setOpacity(0);
        deckControls.getParent().getChildrenUnmodifiable().get(0).setOpacity(1);
        deckControls.getParent().getChildrenUnmodifiable().get(1).setOpacity(1);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        new Controller().switchToSceneMainMenu(mouseEvent);
    }

}
