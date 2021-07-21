import controller.server.Server;
import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import org.junit.Assert;
import org.junit.Test;

import model.user.User;
import controller.process.LoginMenu;


public class App {
    public static void main(String[] args) throws Exception {
        User.initialize();
//        User a = new User("alireza", "alireza", "alireza");
//        User b = new User("alir", "alir", "alir");
//        for(int i = 0; i < Card.getAllCards().size(); i++)
//        {
//            a.getCardNames().add(Card.getAllCards().get(i).getName());
//            b.getCardNames().add(Card.getAllCards().get(i).getName());
//        }
//
//
//        Deck alireza = new Deck("alireza");
//        for(int i = 0; i < Card.getAllCards().size(); i++)
//        {
//            alireza.addMainCard(Card.getAllCards().get(i));
//            if (i<15){
//                alireza.addSideCard(Card.getAllCards().get(i));
//            }
//        }
//        Decks decks = new Decks();
//        decks.add(alireza);
//        decks.setActiveDeck(alireza);
//        a.setDecks(decks);
//        b.setDecks(decks);
//        new LoginMenu().run();
        Server.runApp();
    }
}
