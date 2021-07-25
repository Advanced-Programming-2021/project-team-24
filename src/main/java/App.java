import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import org.junit.Assert;
import org.junit.Test;

import model.user.User;
import view.LoginMenu;

import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) throws Exception {
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        List<Card> getAllCards = Card.getAllCards();
        for(int i = 0; i < getAllCards.size(); i++)
        {
            a.getCardNames().add(getAllCards.get(i).getName());
            b.getCardNames().add(getAllCards.get(i).getName());
        }


        Deck alireza = new Deck("alireza");
        for(int i = 0; i < getAllCards.size(); i++)
        {
            alireza.addMainCard(getAllCards.get(i));
            if (i<15){
                alireza.addSideCard(getAllCards.get(i));
            }
        }
        Decks decks = new Decks();
        decks.add(alireza);
        decks.setActiveDeck(alireza);
        a.setDecks(decks);
        b.setDecks(decks);
        new LoginMenu().run();
    }
    
    @Test
    public void checkTest()
    {
        Assert.assertEquals("alireza", "alireza");
    }

    

}
