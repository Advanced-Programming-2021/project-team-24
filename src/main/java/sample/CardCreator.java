package sample;

import com.jfoenix.controls.JFXButton;
import controller.LoginController;
import controller.Message;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.user.User;

import java.io.IOException;

public class CardCreator {
    @FXML
    MFXTextField name,attack,defence,effects,cards;
    @FXML
    JFXButton createButton;
    @FXML
    Label coins;

    private Stage stage;
    private Scene scene;

    User user;

    public CardCreator(User user){
        this.user = user;
    }

    public void initialize() {
        update();
    }

    public void createCard(MouseEvent mouseEvent) {
        model.card.CardCreator cardCreator = new model.card.CardCreator();
        cardCreator.setAttack(Integer.parseInt(attack.getText()));
        cardCreator.setDefence(Integer.parseInt(defence.getText()));
        cardCreator.setEffectHashMap(effects.getText());
        cardCreator.loadCombineEffect(cards.getText());
        cardCreator.generateCard();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Common.switchToSceneMainMenu(user);
    }

    private void update(){
        coins.setText(String.valueOf(user.getCoin()));
    }
}
