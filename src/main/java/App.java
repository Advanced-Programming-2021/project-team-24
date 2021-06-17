import org.junit.Assert;
import org.junit.Test;

import model.user.User;
import view.LoginMenu;


public class App {
    public static void main(String[] args) throws Exception {
        User.initialize();
        new LoginMenu().run();
    }
    
    @Test
    public void checkTest()
    {
        Assert.assertEquals("alireza", "alireza");
    }

    

}
