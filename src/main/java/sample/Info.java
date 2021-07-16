package sample;

import com.jfoenix.controls.JFXButton;
import controller.Message;
import controller.TypeMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Info extends Group {
    private final JFXButton button = new JFXButton();

    public JFXButton getButton() {
        return button;
    }

    public Info(Message message){
        button.setStyle("-fx-background-color: #4038E6;");
        button.setText("OK");
        Font font1 = new Font("Quicksand-Bold",14.0);
        button.setFont(font1);
        button.setTextFill(Color.WHITE);
        //
        HBox hBox1 = new HBox(button);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPrefHeight(50);
        hBox1.setPrefWidth(381);
        Label label = new Label();
        label.setText(message.getContent());
        Font font2 = new Font("Quicksand-Regular",20.0);
        label.setFont(font2);
        VBox vBox = new VBox(label,hBox1);
        vBox.setAlignment(Pos.CENTER);
        FontAwesomeIcon fontAwesomeIcon = new FontAwesomeIcon();
        if(message.getTypeMessage() == TypeMessage.INFO)
            fontAwesomeIcon.setIconName("INFO_CIRCLE");
        else if(message.getTypeMessage() == TypeMessage.ERROR)
            fontAwesomeIcon.setIconName("TIMES_CIRCLE");
        else if(message.getTypeMessage() == TypeMessage.SUCCESSFUL)
            fontAwesomeIcon.setIconName("CHECK_CIRCLE");
        fontAwesomeIcon.setSize("4em");
        fontAwesomeIcon.setWrappingWidth(45);
        //
        HBox hBox2 = new HBox(fontAwesomeIcon,vBox);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPrefWidth(500);
        hBox2.setPrefHeight(90);
        hBox2.setStyle("-fx-background-color: white;");
        this.getChildren().add(hBox2);
        Common.defineHoverables(button);
    }
}
