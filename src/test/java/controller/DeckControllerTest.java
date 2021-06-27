package controller;

import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DeckControllerTest {
    @Test
    public void getDeckByNameTest(){
        Deck deck = new Deck("new Deck");
        Decks decks = new Decks();
        decks.add(deck);
        assertEquals(deck, new DeckController(decks).getDeckByName("new Deck"));
        assertNull(new DeckController(decks).getDeckByName("abcd"));
    }
    @Test
    public void createTest(){
        Deck deck1 = new Deck("deck1");
        Decks decks = new Decks();
        decks.add(deck1);
        assertEquals("deck created successfully", new DeckController(decks).create("deck2").getContent());
        assertEquals("deck with name deck1 already exists", new DeckController(decks).create("deck1").getContent());
    }
    @Test
    public void deleteTest(){
        Deck deck1 = new Deck("deck1");
        Decks decks = new Decks();
        decks.add(deck1);
        assertEquals("deck deleted successfully", new DeckController(decks).delete("deck1").getContent());
        assertEquals("deck with name deck2 does not exist", new DeckController(decks).delete("deck2").getContent());
    }
    @Test
    public void activeTest(){
        Deck deck1 = new Deck("deck1");
        Decks decks = new Decks();
        decks.add(deck1);
        assertEquals("deck activated successfully", new DeckController(decks).active("deck1").getContent());
        assertEquals("deck with name deck2 does not exist", new DeckController(decks).active("deck2").getContent());
    }
    @Test
    public void addCardTest(){
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        Card axeRaider = Card.getCardByName("Axe Raider");
        for (int i = 0; i < 2; i++) deck.addMainCard(axeRaider);
        DeckController deckController = new DeckController(decks);
        assertEquals("card with name qqqqq does not exist", deckController.addCard("qqqqq",null,true).getContent());
        assertEquals("deck with name deck2 does not exist", deckController.addCard("Axe Raider", "deck2", true).getContent());
        assertEquals("card added to deck successfully", deckController.addCard("Axe Raider", "deck", true).getContent());
        assertEquals("there are already three cards with name Axe Raider in deck deck", deckController.addCard("Axe Raider", "deck", true).getContent());
        for (int i = 0; i < 61; i++) deck.addMainCard(Card.getAllCards().get(i));
        assertEquals("main deck is full", deckController.addCard("Axe Raider", "deck", true).getContent());
        for (int i = 0; i < 16; i++) deck.addSideCard(Card.getAllCards().get(i));
        assertEquals("side deck is full", deckController.addCard("Axe Raider", "deck", false).getContent());
    }
    @Test
    public void removeCardTest(){
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        DeckController deckController = new DeckController(decks);
        assertEquals("deck with name deck2 does not exist", deckController.removeCard("Axe Raider", "deck2", true).getContent());
        assertEquals("card with name Axe Raider does not exist in main deck", deckController.removeCard("Axe Raider", "deck", true).getContent());
        assertEquals("card with name Axe Raider does not exist in side deck", deckController.removeCard("Axe Raider", "deck", false).getContent());
        Card axeRaider = Card.getCardByName("Axe Raider");
        deck.addMainCard(axeRaider);
        assertEquals("card removed from deck successfully", deckController.removeCard("Axe Raider", "deck", true).getContent());
    }
    @Test
    public void showDeckCardsTest(){
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        Card axeRaider = Card.getCardByName("Axe Raider");
        Card blackPendant = Card.getCardByName("Black Pendant");
        deck.addMainCard(axeRaider);
        deck.addMainCard(blackPendant);
        DeckController deckController = new DeckController(decks);
        assertEquals("deck with name deck2 does not exist", deckController.showDeckCards("deck2", true).getContent());
        assertEquals("Deck deck\n" +
                        "Main deck:\n" +
                        "Monsters:\n" +
                        "Name :Axe Raider\n" +
                        "Monster\n" +
                        "Type: WARRIOR\n" +
                        "ATK :1700\n" +
                        "DEF :1150\n" +
                        "Description: An axe-wielding monster of tremendous strength and agility.\n" +
                        "Spell and Traps:\n" +
                        "Name :Black Pendant\n" +
                        "Spell\n" +
                        "Type: UNLIMITED\n" +
                        "Description:The equipped monster gains 500 ATK. When this card is sent from the field to the Graveyard: Inflict 500 damage to your opponent.\n"
                , deckController.showDeckCards("deck", true).getContent());
    }
    @Test
    public void getDecksTest(){
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        assertEquals(decks, new DeckController(decks).getDecks());
    }
}
