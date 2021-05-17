import model.card.Card;
import model.user.User;
import view.LoginMenu;

import org.junit.Assert;
import org.junit.Test;


public class App {
    public static void main(String[] args) throws Exception {
        
        User.initialize();
        new LoginMenu().run();
    }
    
    @Test
    public void checkTaest()
    {
        Assert.assertEquals("alireza", "alireza");
    }

    

}
