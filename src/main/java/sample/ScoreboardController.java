package sample;

import controller.client.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreboardController {
    @FXML
    VBox list;
    @FXML
    FontAwesomeIcon back;
    User user;
    controller.ScoreboardMenu scoreboardMenu;
    public ScoreboardController(User user){
        this.user = user;
        scoreboardMenu = new controller.ScoreboardMenu(this.user);
    }
    public void initialize() throws IOException {

        //String[] scoreboard = scoreboardMenu.showScoreboard().getContent().split("\n");
        update();
    }

    private void update() throws IOException {
        list.getChildren().clear();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);
        back.setEffect(dropShadow);
        dropShadow.setSpread(0.8);
        Font font = new Font("Source Code Pro Black",16);
        String[] scoreboard = Client.getResponse("scoreboard show").getMessage().getContent().split("\n");
        for(int i=0;i<scoreboard.length;i++){
            Label label = new Label();
            label.setText(scoreboard[i]);
            label.setEffect(dropShadow);
            label.setFont(font);
            if(user.getNickname().equals(scoreboard[i].split(" ")[1])){
                label.setTextFill(Color.DODGERBLUE);
            }
            else
                label.setTextFill(Color.WHITE);
            list.getChildren().add(label);
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
//        System.out.println(Client.getResponse("menu exit").getMessage().getContent());
//        Common.switchToSceneMainMenu(this.user);
        update();
    }
}
