package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreboardController {
    @FXML
    Label p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20;
    @FXML
    FontAwesomeIcon back;
    User user;
    controller.ScoreboardMenu scoreboardMenu;
    public ScoreboardController(User user){
        this.user = user;
        scoreboardMenu = new controller.ScoreboardMenu(this.user);
    }
    public void initialize(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);
        back.setEffect(dropShadow);
        dropShadow.setSpread(0.8);
        List<Label> players = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20);
        String[] scoreboard = scoreboardMenu.showScoreboard().getContent().split("\n");
        for(int i=0;i<scoreboard.length;i++){
            players.get(i).setText(scoreboard[i]);
            if(user.getNickname().equals(scoreboard[i].split(" ")[1])){
                players.get(i).setTextFill(Color.DODGERBLUE);
            }
        }
        for(Label label : players){
            label.setEffect(dropShadow);
        }
    }
    public void back(MouseEvent mouseEvent) throws IOException {
        Common.switchToSceneMainMenu(this.user);
    }
}
