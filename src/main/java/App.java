import model.card.Card;
import model.user.User;
import view.LoginMenu;

import org.junit.Assert;
import org.junit.Test;


public class App {
    public static void main(String[] args) throws Exception {
        Card.intialize();
        User.initialize();
        new LoginMenu().run();
    }
    
    @Test
    public void checkTest()
    {
        Assert.assertEquals("alireza", "alireza");
    }

    

}
