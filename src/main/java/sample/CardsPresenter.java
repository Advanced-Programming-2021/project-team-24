package sample;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.card.Card;

import java.util.List;

public class CardsPresenter extends Parent {
    GridPane myCards = new GridPane();
    int columnCardCount = 5;
    int rowCardCount;
    double windowWidth = 1024;
    double windowHeight = 576;
    double splitWidth = windowWidth / 2;
    double cardRatio = 1.5;
    int cardMargin = 20;
    public CardsPresenter(List<Card> targetCards) {
        System.out.println(targetCards.size());
        rowCardCount = targetCards.size() / 5;
        for (int i = 0; i < columnCardCount; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints((splitWidth - 2 * cardMargin) / columnCardCount);
            myCards.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowCardCount; i++) {
            RowConstraints rowConstraints = new RowConstraints(((splitWidth - 2 * cardMargin) / columnCardCount) * cardRatio);
            myCards.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < targetCards.size(); i++){
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(Common.handleImage(targetCards.get(i)))));
            imageView.setFitWidth(splitWidth / columnCardCount - cardMargin);
            imageView.setFitHeight(imageView.getFitWidth() * cardRatio);
            myCards.getChildren().add(imageView);
            GridPane.setColumnIndex(imageView, i % columnCardCount);
            GridPane.setRowIndex(imageView, i / columnCardCount);
        }
        this.getChildren().add(myCards);
    }
}
