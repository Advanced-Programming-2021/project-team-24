package model.card.magic;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.*;
public class MagicCardHolderTest {
    MagicCard magicCard = (MagicCard) Card.getCardByName("Dark Hole");
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    MagicCardHolder magicCardHolder = new MagicCardHolder(player, magicCard, CardState.SET_MAGIC);
    @Test
    public void getCardTest() {
        assertEquals(magicCard, magicCardHolder.getCard());
    }
    @Test
    public void flipTest(){
        magicCardHolder.flip();
        CardHolder cardHolder = (CardHolder) magicCardHolder;
        assertEquals(cardHolder.getCardState(), CardState.VISIBLE_MAGIC);
    }
    @Test
    public void toStringTest(){
        assertEquals("Name :Dark Hole\n" +
                "Spell\n" +
                "Type: UNLIMITED\n" +
                "Description:Destroy all monsters on the field.\n", magicCardHolder.toString());
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
