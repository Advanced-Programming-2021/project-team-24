package model.duel;

import controller.DuelController;
import model.card.Card;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.deck.Deck;
import model.deck.Decks;
import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import org.junit.Test;
import view.DuelMenu;

import static org.junit.Assert.*;
public class EffectChainerTest {
    User user1 = new User("behzad", "behzad", "behzad");
    User user2 = new User("ali", "ali", "ali");
    Player player1 = new Player(user1);
    Player player2 = new Player(user2);
    Deck deck = new Deck("deck");
    Decks decks = new Decks();
    Duel duel;
    DuelController duelController;
    DuelMenu duelMenu;
    Effect effect;
    EffectManager effectManager;
    EffectParser effectParser;
    public void set(){
        for (int i = 0; i < 60; i++) {
            deck.addMainCard(Card.getAllCards().get(i));
        }
        decks.add(deck);
        decks.setActiveDeck(deck);
        user1.setDecks(decks);
        user2.setDecks(decks);
        duel = new Duel(player1, player2);
        duelController = new DuelController(duel);
        duelMenu = new DuelMenu(player1, player2);
        effect = new Effect("");
        effectManager = new EffectManager(effect, player1, 1);
        effectParser = new EffectParser(duelMenu, duelController, effectManager);
    }
    @Test
    public void test(){
        set();
        new EffectChainer(Event.ANY, player2, duelController);
        MagicCard card = (MagicCard) Card.getCardByName("Dark Hole");
        MagicCardHolder magicCardHolder = new MagicCardHolder(player1, card, CardState.ACTIVE_MAGIC);
        EffectChainer effectChainer = new EffectChainer(Event.ANY, magicCardHolder, player2, duelController);
    }
}
