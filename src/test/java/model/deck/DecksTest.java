package model.deck;

import model.card.Card;
import org.junit.Test;
import static org.junit.Assert.*;
public class DecksTest {
    Decks decks = new Decks();
    Deck deck = new Deck("deck");
    Card card1 = Card.getCardByName("Axe Raider");
    Card card2 = Card.getCardByName("Dark Hole");
    @Test
    public void getDeckByName(){
        assertNull(decks.getDeckByName("aaa"));
        decks.add(deck);
        assertEquals(deck, decks.getDeckByName("deck"));
    }
    @Test
    public void getCardByNameTest(){
        decks.add(deck);
        decks.addCard(card1, "deck", true);
        assertEquals(decks.getCardByName("Axe Raider", "deck", true), card1);
        decks.addCard(card2, "deck", false);
        assertEquals(decks.getCardByName("Dark Hole", "deck", false), card2);
        assertNull(decks.getCardByName("aaa", "deck", false));
    }
    @Test
    public void getCountTest(){
        decks.add(deck);
        decks.addCard(card1, "deck", true);
        decks.addCard(card2, "deck", true);
        assertEquals(decks.getCount("deck", true), 2);
        assertEquals(decks.getCount("deck", false), 0);
    }
    @Test
    public void removeCardTest(){
        decks.add(deck);
        decks.addCard(card1, "deck", true);
        decks.addCard(card2, "deck", true);
        decks.removeCard(card1, "deck", true);
        decks.removeCard(card1, "deck", false);
        assertEquals(decks.getCount("deck", true), 1);
    }
    @Test
    public void removeDeck(){
        decks.add(deck);
        decks.remove("deck");
        assertEquals(decks.getDecks().size(), 0);
    }
}
