package sample;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Question extends Group {
    private final JFXButton leftButton = new JFXButton(), rightButton = new JFXButton();

    public JFXButton getLeftButton() {
        return leftButton;
    }

    public Question(){
        leftButton.setStyle("-fx-background-color: #4038E6;");
        leftButton.setText("Cancel");
        Font font1 = new Font("Quicksand-Bold",14.0);
        leftButton.setFont(font1);
        leftButton.setTextFill(Color.WHITE);
        rightButton.setStyle("-fx-background-color: black;");
        rightButton.setText("Delete");
        rightButton.setTextFill(Color.WHITE);
        rightButton.setFont(font1);
        //
        HBox hBox1 = new HBox(leftButton,rightButton);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPrefHeight(50);
        hBox1.setPrefWidth(381);
        HBox.setMargin(leftButton,new Insets(0,15,0,0));
        Label label = new Label();
        label.setText("Are you sure you want to delete deck?");
        Font font2 = new Font("Quicksand-Regular",20.0);
        label.setFont(font2);
        VBox vBox = new VBox(label,hBox1);
        vBox.setAlignment(Pos.CENTER);
        FontAwesomeIcon fontAwesomeIcon = new FontAwesomeIcon();
        fontAwesomeIcon.setIconName("TIMES_CIRCLE");
        fontAwesomeIcon.setSize("4em");
        fontAwesomeIcon.setWrappingWidth(45);
        //
        HBox hBox2 = new HBox(fontAwesomeIcon,vBox);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPrefWidth(500);
        hBox2.setPrefHeight(90);
        hBox2.setStyle("-fx-background-color: white;");
        this.getChildren().add(hBox2);
        Common.defineHoverables(leftButton,rightButton);
    }


}
