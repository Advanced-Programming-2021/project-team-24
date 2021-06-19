import controller.DuelController;
import controller.Menu;
import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.duel.Duel;
import model.user.User;
import org.junit.Test;
import view.DuelMenu;
import view.Global;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
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
