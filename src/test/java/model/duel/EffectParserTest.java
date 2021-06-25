package model.duel;

import controller.DuelController;
import model.card.Card;
import model.card.CardHolder;
import model.deck.Deck;
import model.deck.Decks;
import model.effect.Effect;
import model.effect.EffectManager;
import model.effect.EffectType;
import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import view.DuelMenu;

import static org.junit.Assert.*;
public class EffectParserTest {
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
    public void drawTest(){
        set();
        assertEquals("hey", effectParser.draw("hey"));
        assertEquals("", effectParser.draw("draw()"));
    }
    @Test
    public void getAnsTest(){
        set();
        effectParser.setAns("");
        assertEquals("", effectParser.getAns());
    }
    @Test
    public void getOwnerTest(){
        set();
        assertEquals(player1, effectParser.getOwner());
    }
    @Test
    public void changeZoneTest(){
        set();
        assertEquals("", effectParser.changeZone(""));
        //assertEquals("", effectParser.changeZone("changeZone(e)"));
    }
    @Test
    public void changeLPTest(){
        set();
        assertEquals("", effectParser.changeLP(""));
        assertEquals("", effectParser.changeLP("changeLP(own,1)"));
        assertEquals("", effectParser.changeLP("changeLP(opponent,1)"));
    }
    @Test
    public void handleMessageTest(){
        set();
        assertEquals(effectParser.handleMessage(""), "");
        assertEquals(effectParser.handleMessage("message(s)"), "");
    }
    @Test
    public void correctStateOfCardTest(){
        set();
        assertEquals(-1, effectParser.correctStateOfChar("(", '('));
        assertEquals(-1, effectParser.correctStateOfChar(")", ')'));
        assertEquals(0, effectParser.correctStateOfChar("a", 'a'));
    }
    @Test
    public void getCommandResultTest(){
        set();
        assertEquals("true", effectParser.getCommandResult("return_t"));
        effectParser.setAns(null);
        assertEquals("false", effectParser.getCommandResult("return_f"));
        effectParser.setAns(null);
        assertEquals(effectParser.selective("select"), effectParser.getCommandResult("select"));
        effectParser.setAns(null);
        assertEquals(effectParser.randomSelection("random_selection"), effectParser.getCommandResult("random_selection"));
        effectParser.setAns(null);
        assertEquals(effectParser.changeZone("changeZone"), effectParser.getCommandResult("changeZone"));
        effectParser.setAns(null);
        assertEquals(effectParser.getListByFilter("filter"), effectParser.getCommandResult("filter"));
        effectParser.setAns(null);
        assertEquals(effectParser.handleConditional("if"), effectParser.getCommandResult("if"));
        effectParser.setAns(null);
        assertEquals(effectParser.q_yn("q_yn"), effectParser.getCommandResult("q_yn"));
        effectParser.setAns(null);
        assertEquals(effectParser.deleteListFromList("del"), effectParser.getCommandResult("del"));
        effectParser.setAns(null);
        assertEquals(effectParser.getSumOverField("sum"), effectParser.getCommandResult("sum"));
        effectParser.setAns(null);
        assertEquals("behzad", effectParser.getCommandResult("behzad"));
        assertEquals(";behzad", effectParser.getCommandResult(";behzad"));
    }
    @Test
    public void handleChangeLPTest(){
        set();
        assertEquals("behzad", effectParser.handleChangeLPCommand("behzad"));
        assertEquals(effectParser.changeLP("changeLP"), effectParser.handleChangeLPCommand("changeLP"));
    }
    @Test
    public void handleNormCommandTest(){
        set();
        assertEquals("behzad", effectParser.handleNormCommand("behzad"));
        System.out.println(effectParser.handleNormCommand("Norm(2)"));

    }
}
