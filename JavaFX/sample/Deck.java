package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Deck extends Group {

    @FXML
    private boolean isActive;

    private DeckControls deckControls;
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private AnchorPane anchorPane;
    private Label label = new Label();
    private VBox vBox;
    private Font font;

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
        if(this.isActive){
            deckControls = new DeckControls(true);
            font = new Font("Source Sans Pro Bold Italic", 19.0);
        }
        else{
            deckControls = new DeckControls(false);
            font = new Font("Source Sans Pro light", 21.0);
        }
        anchorPane = new AnchorPane(imageView1, imageView2, deckControls);
        anchorPane.setPrefHeight(153);
        anchorPane.setPrefWidth(170);
        label.setText("DeckName");
        label.setFont(font);
        vBox = new VBox(anchorPane, label);
        vBox.setAlignment(Pos.CENTER);
        vBox.prefHeight(200);
        vBox.prefWidth(100);
        if(this.isActive) {
            vBox.setScaleX(1.4);
            vBox.setScaleY(1.4);
        }
        this.getChildren().add(vBox);
    }

    public Deck() {
        Image cardBack = new Image(getClass().getResourceAsStream("/resources/images/cardsBack.png"));
        Image representativeCard = new Image(getClass().getResourceAsStream("/resources/images/cards/Monsters/CommandKnight.jpg"));
        //
        imageView1.setImage(cardBack);
        imageView1.setFitHeight(137);
        imageView1.setFitWidth(97);
        imageView1.setLayoutX(37.0);
        imageView1.setLayoutY(14);
        //
        imageView2.setImage(representativeCard);
        imageView2.setFitHeight(119);
        imageView2.setFitWidth(81);
        imageView2.setLayoutX(37.0);
        imageView2.setLayoutY(14);
        //
        label.setTextFill(Color.WHITE);
    };
}
