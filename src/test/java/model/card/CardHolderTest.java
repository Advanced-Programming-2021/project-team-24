package model.card;

import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
public class CardHolderTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    CardHolder cardHolder = new CardHolder(player) {
        @Override
        public String toString() {
            return null;
        }

        @Override
        public void flip() {

        }

        @Override
        public Card getCard() {
            return null;
        }
    };
    @Test
    public void getOwnerNameTest(){
        assertEquals(cardHolder.getOwnerName(), "sdfsdf");
        assertEquals(player, cardHolder.getOwner());
        User.deleteUser("salam");
    }
    @Test
    public void getCardTypeTest(){
        cardHolder.setCardType(CardType.MAGIC);
        assertEquals(cardHolder.getCardType(),CardType.MAGIC);
    }
    @Test
    public void getMapTest(){
        HashMap<String, String> cardMap = new HashMap<>();
        cardMap.put("0", "1");
        cardHolder.setMapValue("0", "1", 2);
        assertEquals(cardMap, cardHolder.getCardMap());
    }
    @Test
    public void getValueTest(){
        cardHolder.setMapValue("0", "1", 2);
        assertEquals(cardHolder.getValue("0"), "1");
        assertEquals(cardHolder.getValue("1"), "");
    }
    @Test
    public void getBoolMapValue(){
        assertTrue(cardHolder.getBoolMapValue("can 123"));
        assertFalse(cardHolder.getBoolMapValue("123"));
        cardHolder.setMapValue("0", "1", 2);
        assertFalse(cardHolder.getBoolMapValue("0"));
    }
    @Test
    public void isEmptyTest(){
        assertTrue(cardHolder.isEmpty());
    }
    @Test
    public void getCardStateTest(){
        assertNull(cardHolder.getCardState());
    }
    @Test
    public void haveEffectWithIdTest(){
        Effect effect = new Effect("");
        cardHolder.haveEffectWithId(12);
        EffectManager effectManager = new EffectManager(effect, player, 2);
        cardHolder.addEffect(effectManager.getId());
        assertTrue(cardHolder.haveEffectWithId(effectManager.getId()));
        cardHolder.removeEffect(effectManager.getId()-1);
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
