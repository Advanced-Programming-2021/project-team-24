import model.card.Card;
import model.user.User;
import view.LoginMenu;

public class App {
    public static void main(String[] args) throws Exception {
        Card.intialize();
        User.intialize();
        new LoginMenu().run();
    }
}
