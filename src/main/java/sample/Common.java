package sample;

import controller.Message;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.card.Card;
import model.card.CardType;
import model.user.User;
import org.controlsfx.control.PopOver;

import java.io.IOException;

public class Common {
    public static Stage stage;
    private static Scene scene;

    public static void defineHoverables(Node... nodes){
        for(Node node : nodes){
            node.getStyleClass().add("hoverable");
        }
    }
    public static void disableDivider(SplitPane splitPane) {
        splitPane.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));
    }

    public static void back() throws IOException {
//        new Controller().switchToSceneMainMenu();
    }

    public static void setStage(Stage stage){
        Common.stage = stage;
    }

    public static void switchToSceneMainMenu(User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(Common.class.getResource("scenes/mainMenu.fxml"));
        MenuController menuController = new MenuController(user);
        loader.setController(menuController);
        Parent root = loader.load();
        scene = new Scene(root);
        Stage stage = Common.stage;
        stage.setScene(scene);
//        menuController.init();
        stage.setResizable(false);
        stage.show();
    }

    public static String handleImage(Card card){
        if(card == null){
            return "images/cards/Monsters/Unknown.jpg";
        }
        else if (card.getCardType().equals(CardType.MONSTER)) {
            return "images/cards/Monsters/" + card.getName().replaceAll(" ","") +".jpg";
        } else {
            return "images/cards/SpellTrap/" + card.getName().replaceAll(" ","")+".jpg";
        }
    }

    public static void handleOkButton(Info info, PopOver popOver){
        info.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popOver.hide();
            }
        });
    }

    public static void showMessage(Message message,Node node) {
        Info info = new Info(message);
        PopOver popOver = new PopOver(info);
        popOver.show(node);
        Common.handleOkButton(info,popOver);
    }

}
