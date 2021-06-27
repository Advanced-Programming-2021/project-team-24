package model.duel;

import controller.DuelController;
import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import org.junit.Test;
import view.DuelMenu;

import static org.junit.Assert.*;
public class DuelRoundManagerTest {
    User user1 = new User("behzad", "behzad", "behzad");
    User user2 = new User("ali", "ali", "ali");
    Player player1 = new Player(user1);
    Player player2 = new Player(user2);
    Deck deck = new Deck("deck");
    Decks decks = new Decks();
    DuelRoundManager duelRoundManager;
    public void set(){
        for (int i = 0; i < 60; i++) {
            deck.addMainCard(Card.getAllCards().get(i));
        }
        decks.add(deck);
        decks.setActiveDeck(deck);
        user1.setDecks(decks);
        user2.setDecks(decks);
        duelRoundManager = new DuelRoundManager(user1, user2, 3);
    }
    @Test
    public void isLoserTest(){
        set();
        assertFalse(duelRoundManager.isLoser(player1));
    }
    @Test
    public void calculateCoinTest(){
        set();
        int coin1 = user1.getCoin();
        int coin2 = user2.getCoin();
        duelRoundManager.calculateCoin(player1);
        assertEquals(coin1 + 3000, user1.getCoin());
        player2.setIsDeadRounds(3);
        duelRoundManager.calculateCoin(player2);
        assertEquals(coin2 + 300, user2.getCoin());
    }
}
