package sample;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Coin extends Parent {
    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("images/coin/1.png")));
    JFXButton button;

    public JFXButton getButton() {
        return button;
    }

    public Coin(){
        Label label = new Label();
        button = new JFXButton();
        button.setStyle("-fx-background-color: #4038E6;");
        button.setText("OK");
        Font font1 = new Font("Quicksand-Bold",14.0);
        button.setFont(font1);
        button.setTextFill(Color.WHITE);
        label.setFont(font1);
        VBox vBox = new VBox(imageView,label,button);
        vBox.setPrefWidth(600);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().add(vBox);
        CoinAnimation coinAnimation = new CoinAnimation(imageView,label);
        coinAnimation.play();
    }
}
