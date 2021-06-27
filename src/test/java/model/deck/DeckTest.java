package model.deck;

import static org.junit.Assert.*;

import model.card.Card;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
public class DeckTest {
    Deck deck = new Deck("deck");
    Card card1 = Card.getCardByName("Axe Raider");
    Card card2 = Card.getCardByName("Dark Hole");
    @Test
    public void getName(){
        assertEquals("deck", deck.getName());
    }
    @Test
    public void getMainCardsTest(){
        deck.addMainCard(card1);
        assertEquals(card1, deck.getMainCards().get(0));
    }
    @Test
    public void getSideCardsTest(){
        deck.addSideCard(card1);
        assertEquals(card1, deck.getSideCards().get(0));
    }
    @Test
    public void getCountOfCard(){
        for (int i = 0; i < 2; i++) deck.addMainCard(card1);
        for (int i = 0; i < 3; i++) deck.addSideCard(card1);
        assertEquals(5, deck.getCountOfCard("Axe Raider"));
    }
    @Test
    public void removeMainCard(){
        for (int i = 0; i < 2; i++) deck.addMainCard(card1);
        deck.removeMainCard(card1);
        assertEquals(1, deck.getCountOfCard("Axe Raider"));
    }
    @Test
    public void removeSideCard(){
        for (int i = 0; i < 2; i++) deck.addSideCard(card1);
        deck.removeSideCard(card1);
        assertEquals(1, deck.getCountOfCard("Axe Raider"));
    }
    @Test
    public void isValidTest(){
        assertFalse(deck.isValid());
        for (int i = 0; i < 50; i++) deck.addMainCard(card1);
        assertTrue(deck.isValid());
    }
    @Test
    public void toStringCardsTest(){
        deck.addMainCard(card1);
        assertEquals("Deck deck\n" +
                "Main deck:\n" +
                "Monsters:\n" +
                "Name :Axe Raider\n" +
                "Monster\n" +
                "Type: WARRIOR\n" +
                "ATK :1700\n" +
                "DEF :1150\n" +
                "Description: An axe-wielding monster of tremendous strength and agility.\n" +
                "Spell and Traps:\n", deck.toStringCards(true));
        deck.addSideCard(card2);
        assertEquals("Deck deck\n" +
                "Side deck:\n" +
                "Monsters:\n" +
                "Spell and Traps:\n" +
                "Name :Dark Hole\n" +
                "Spell\n" +
                "Type: UNLIMITED\n" +
                "Description:Destroy all monsters on the field.\n",deck.toStringCards(false));
    }

}
