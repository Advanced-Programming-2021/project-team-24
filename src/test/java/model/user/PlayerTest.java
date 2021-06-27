package model.user;

import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.zone.Address;
import model.zone.Zone;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlayerTest {
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    @Test
    public void getSelectedAddress(){
        Address address = new Address(new Zone("hand", player), 2);
        player.selectAddress(address);
        assertEquals(address, player.getSelectedAddress());
    }
    @Test
    public void getNicknameTest(){
        assertEquals(player.getNickname(), "sdfsdf");
    }
    @Test
    public void changeLifePointTest(){
        int lifePoint = player.getLifePoint();
        player.changeLifePoint(10);
        assertEquals(player.getLifePoint(), lifePoint + 10);
    }
    @Test
    public void isDeadTest(){
        assertFalse(player.isDead());
        player.changeLifePoint(-10000);
        assertTrue(player.isDead());
    }
    @Test
    public void getMaxLifePointTest(){
        player.setMaxLifePoint();
        assertEquals(8000, player.getMaxLifePoint());
    }
    @Test
    public void getIsDeadRoundsTest(){
        player.setIsDeadRounds(2);
        assertEquals(2, player.getIsDeadRounds());
    }
    @Test
    public void canChangeCardsTest(){
        User user = new User("salam", "aaaaaa", "sdfsdf");
        Card card1 = Card.getCardByName("Axe Raider");
        Card card2 = Card.getCardByName("Battle OX");
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        deck.addMainCard(card1);
        deck.addSideCard(card2);
        decks.add(deck);
        decks.setActiveDeck(deck);
        user.setDecks(decks);
        Player player = new Player(user);
        assertFalse(player.canChangeCards(card1, card2));
    }
    @Test
    public void resetPlayerForNextRoundTest(){
        player.changeLifePoint(-1000);
        player.resetPlayerForNextRound();
        assertEquals(8000, player.getLifePoint());
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
