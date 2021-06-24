package model.effect;

import model.card.Card;
import model.card.CardHolder;
import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EffectManagerTest {
    Effect effect = new Effect("");
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
    EffectManager effectManager = new EffectManager(effect, player, cardHolder.getId());
    @Test
    public void getOwnerTest(){
        assertEquals(player, effectManager.getOwner());
    }
    @Test
    public void getActivatedTest(){
        effectManager.setActivated();
        assertTrue(effectManager.getActivated());
    }
    @Test
    public void getEffectTest(){
        assertEquals(effect, effectManager.getEffect());
    }
    @Test
    public void getOwnerCardHolderId(){
        assertEquals(cardHolder.getId(), effectManager.getOwnerCardHolderId());
    }
    @Test
    public void isConditionSatisfied(){

    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
