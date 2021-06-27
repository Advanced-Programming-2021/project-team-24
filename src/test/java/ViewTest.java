import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.card.Card;
import model.user.User;
import view.Global;
public class ViewTest {
    User user1 = new User("user1", "12345", "nick1");
    User user2 = new User("user2", "12345", "nick2");
    Card card1 = Card.getCardByName("Axe Raider");
    Card card2 = Card.getCardByName("Dark Hole");
    @Test
    public void regexFindTest(){
        assertTrue(Global.regexFind("salam", "ala"));
    }
    @Test
    public void checkMenuExitTest(){
    }
}
