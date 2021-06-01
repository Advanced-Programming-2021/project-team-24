package sample;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.PopOver;

public class Test extends Group {
    @FXML private int idv4;

    public int getIdv4() {
        return idv4;
    }

    public void setIdv4(int idv4) {
        this.idv4 = idv4;
    }

    public Test(){
        SplitPane split = new SplitPane();
        VBox left = new VBox(new Label("left"));
        left.setStyle("-fx-background-color: cadetblue");
        VBox right = new VBox(new Label("right"));
        right.setStyle("-fx-background-color: darkorange");
        VBox center = new VBox(new Label("center"));
        center.setStyle("-fx-background-color: darkgreen");
        split.getItems().addAll(left, center, right);

        split.setDividerPosition(0,1/(double)3);
        split.setDividerPosition(1,2/(double)3);


    }


}
