package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardType;


public class CardView extends Parent {
    Card card;
    public CardView(CardHolder cardHolder){
        this.card = cardHolder.getCard();
        ImageView cardImage = new ImageView(new Image(getClass().getResourceAsStream(Common.handleImage(this.card))));
        cardImage.setFitWidth(200);
        cardImage.setFitHeight(300);
        Label cardName = new Label();
        cardName.setText(this.card.getName());
        Font font = new Font("Source Code Pro Black",14.0);
        cardName.setFont(font);
        VBox.setMargin(cardName, new Insets(8,0,0,0));
        Label cardProp = new Label();
        if(this.card.getCardType().equals(CardType.MONSTER))
            cardProp.setText("Atk "+ cardHolder.getCardMap().get("attack") +" Def "+cardHolder.getCardMap().get("defence"));
        else cardProp.setText("");
        font = new Font("Source Code Pro Black Italic",11.0);
        cardProp.setFont(font);
        VBox.setMargin(cardName, new Insets(0,0,5,0));
        TextArea cardDescription = new TextArea();
        cardDescription.setPrefHeight(90);
        cardDescription.setPrefWidth(138);
        cardDescription.setText(this.card.getDescription());
        cardDescription.setWrapText(true);
        cardDescription.setEditable(false);
        font = new Font("Source Code Pro Medium",12.0);
        cardDescription.setFont(font);
        VBox vBox = new VBox(cardImage,cardName,cardProp,cardDescription);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().add(vBox);

    }
}
