package controller;

import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.duel.Duel;
import model.user.Player;
import model.user.User;
import view.DuelMenu;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DuelControllerTest {
    @Test
    public void nextPhaseTest(){
        User user1 = new User("user1", "aaaaaa", "nickname1");
        User user2 = new User("user2", "aaaaaa", "nickname2");
        Card axeRaider = Card.getCardByName("Axe Raider");
        Card blackPendant = Card.getCardByName("Black Pendant");
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        deck.addMainCard(axeRaider);
        deck.addMainCard(blackPendant);
        decks.setActiveDeck(deck);
        user1.setDecks(decks);
        user2.setDecks(decks);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        Duel duel = new Duel(player1, player2);
        DuelController duelController = new DuelController(duel);
        for (int i = 0; i < 2; i++) duel.nextPhase();
        assertEquals("BATTLE", duelController.nextPhase().getContent());
    }
    @Test
    public void drawTest(){
        //TODO
    }
    @Test
    public void selectTest(){
        //Address address = new Address();
    }
    @Test
    public void runPhaseTest(){
        User user1 = new User("testUser1", "1234", "qwer1");
        User user2 = new User("testUser2", "1234", "qwer1");        
        //Card.intialize();
        
        user1.getCards().addAll(Card.getAllCards());
        user2.getCards().addAll(Card.getAllCards());        
        System.out.println(user1.getCards().size());
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        DuelMenu duelMenu = new DuelMenu(player1, player2);

        DuelController duelController = duelMenu.getDuelController();
        Duel duel = duelController.getDuel();
        


        
        assertEquals(duelController.nextPhase().getContent(), "STANDBY");
        // DRAW TEST
        User.deleteUser("user1");
        User.deleteUser("user2");
    }



}
