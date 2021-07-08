package sample;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Input extends Group {

    HBox hBox;
    JFXButton button;
    MFXTextField input;

    public Input(){
        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setIconName("PLUS_CIRCLE");
        icon.setSelectionFill(Color.BLACK);
        icon.setSize("4em");
        icon.setWrappingWidth(45);
        HBox.setMargin(icon,new Insets(0,10,0,0));
        input = new MFXTextField();
        input.setPromptText("Enter deck name");
        input.setLineColor(Color.web("#4038E6"));
        HBox.setMargin(input,new Insets(0,10,0,0));
        HBox.setMargin(icon,new Insets(0,10,0,0));
        button = new JFXButton();
        button.setStyle("-fx-background-color: #4038E6;");
        button.setText("Create");
        button.setTextFill(Color.WHITE);
        hBox = new HBox(icon,input,button);
        hBox.setPrefWidth(300);
        hBox.setPrefHeight(80);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: white;");
        this.getChildren().add(hBox);
        Common.defineHoverables(button);
    }

    public HBox getHBox(){
        return hBox;
    }

    public JFXButton getButton() {
        return button;
    }

    public String getInput() {
        return input.getText();
    }


}
