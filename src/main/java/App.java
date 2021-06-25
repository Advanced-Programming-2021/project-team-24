import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import org.junit.Assert;
import org.junit.Test;

import model.user.User;
import view.LoginMenu;


public class App {
    public static void main(String[] args) throws Exception {
        User.initialize();
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        Deck alireza = new Deck("alireza");
        for(int i = 0; i < Card.getAllCards().size(); i++)
        {
            alireza.addMainCard(Card.getAllCards().get(i));
        }
        Decks decks = new Decks();
        decks.add(alireza);
        decks.setActiveDeck(alireza);
        a.setDecks(decks);
        b.setDecks(decks);
        System.out.println(a.getUsername());
        new LoginMenu().run();
    }
    
    @Test
    public void checkTest()
    {
        Assert.assertEquals("alireza", "alireza");
    }

    

}
