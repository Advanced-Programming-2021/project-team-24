package model.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.card.magic.LimitType;

public class CardTest {
    @Test
    public void getAllCardsTest(){
        assertEquals("Name :Marshmallon\n" +
                "Monster\n" +
                "Type: FAIRY\n" +
                "ATK :300\n" +
                "DEF :500\n" +
                "Description: \"Cannot be destroyed by battle. After damage calculation, if this card was attacked, " +
                "and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.\"\n", Card.getAllCards().get(0).toString());
    }
    @Test
    public void getCardByNameTest(){
        assertNotNull(Card.getCardByName("Dark Hole"));
        assertNull(Card.getCardByName("sdjflskdfjs"));
    }
    @Test
    public void getNameTest(){
        Card card = Card.getCardByName("Dark Hole");
        assertEquals(card.getName(), "Dark Hole");
    }
    @Test
    public void getPriceTest(){
        Card card = Card.getCardByName("Dark Hole");
        assertEquals(card.getPrice(), 2500);
    }
    @Test
    public void getLimitTypeTest(){
        Card card = Card.getCardByName("Dark Hole");
        assertEquals(card.getLimitType(), LimitType.UNLIMITED);
    }
    @Test
    public void getDescriptionTest(){
        Card card = Card.getCardByName("Dark Hole");
        assertEquals(card.getDescription(), "Destroy all monsters on the field.");
    }
    @Test
    public void isMagicTest(){
        Card card1 = Card.getCardByName("Dark Hole");
        Card card2 = Card.getCardByName("Axe Raider");
        assertTrue(card1.isMagic());
        assertFalse(card2.isMagic());
    }
    @Test
    public void toEnumFormatStringTest(){
        String string = "aaa-bbb-ccc ddd eee";
        assertEquals(Card.toEnumsFormatString(string), "AAA_BBB_CCC_DDD_EEE");
    }
    @Test
    public void updateCardTest(){
        Card card = Card.getCardByName("Axe Raider");
        card.updateCard();
        assertEquals(card.getCardType(), CardType.MONSTER);
        card = Card.getCardByName("Dark Hole");
        card.updateCard();
        assertEquals(card.getCardType(), CardType.MAGIC);
    }
}
