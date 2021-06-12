package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class ShopController {
    @FXML
    GridPane myCards;
    ImageView imageView;
    double windowWidth = 1024;
    double windowHeight = 576;
    double splitWidth=windowWidth/2;
    double cardRatio = 1.5;
    int cardMargin = 20;
    int columnCardCount=5;
    int rowCardCount=3;
    Image card = new Image(getClass().getResourceAsStream("../resources/images/cards/Monsters/AlexandriteDragon.jpg"));
    public void initialize() {
        AnchorPane.setTopAnchor(myCards,(double)cardMargin);
        AnchorPane.setLeftAnchor(myCards,(double)cardMargin);
        AnchorPane.setRightAnchor(myCards,(double)cardMargin);
        for(int i=0;i<columnCardCount;i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints((splitWidth - 2 * cardMargin) / columnCardCount);
            myCards.getColumnConstraints().add(columnConstraints);
        }
        for(int i=0;i<rowCardCount;i++) {
            RowConstraints rowConstraints = new RowConstraints(((splitWidth - 2 * cardMargin) / columnCardCount) * cardRatio);
            myCards.getRowConstraints().add(rowConstraints);
        }
        for(int i=0;i<columnCardCount*rowCardCount;i++){
            imageView = new ImageView(card);
            imageView.setFitWidth(splitWidth/columnCardCount-cardMargin);
            imageView.setFitHeight(imageView.getFitWidth()*cardRatio);
            myCards.getChildren().add(imageView);
            GridPane.setColumnIndex(imageView,i%columnCardCount);
            GridPane.setRowIndex(imageView,i/columnCardCount);
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Common.back();
    }
}
