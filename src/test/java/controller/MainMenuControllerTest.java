package controller;

import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.user.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MainMenuControllerTest {
    @Test
    public void createDuelTest(){
        
        Deck deck1 = new Deck("deck1");
        Deck deck2 = new Deck("deck2");
        Decks decks1 = new Decks();
        Decks decks2 = new Decks();
        decks1.add(deck1);
        decks2.add(deck2);
        User user1 = new User("user1", "123", "qqq");
        User user2 = new User("user2", "456", "www");
        MainMenuController mainMenuController = new MainMenuController();
        assertEquals("there is no player with this username", mainMenuController.createDuel(user1, null, "3").getContent());
        assertEquals("user1 has no active deck", mainMenuController.createDuel(user1, user2, "3").getContent());
        user1.setDecks(decks1);
        decks1.setActiveDeck(deck1);
        assertEquals("user2 has no active deck", mainMenuController.createDuel(user1, user2, "3").getContent());
        user2.setDecks(decks2);
        decks2.setActiveDeck(deck2);
        assertEquals("user1's deck is invalid", mainMenuController.createDuel(user1, user2,"3").getContent());
        List<Card> cards = Card.getAllCards();
        for (int i = 0; i < 60; i++) deck1.addMainCard(cards.get(i));
        assertEquals("user2's deck is invalid", mainMenuController.createDuel(user1, user2,"3").getContent());
        for (int i = 0; i < 60; i++) deck2.addMainCard(cards.get(i));
        assertEquals("number of rounds is not supported", mainMenuController.createDuel(user1, user2, "0").getContent());
        User.deleteUser("user1");
        User.deleteUser("user2");
    }
}
