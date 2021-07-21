package controller.process;

import java.util.List;
import java.util.regex.Matcher;

import controller.DeckController;
import controller.Message;
import controller.TypeMessage;
import controller.server.GsonConverter;
import model.Response;
import model.Situation;
import model.card.Card;
import model.deck.Deck;
import model.user.User;

public class DeckMenu extends Menu {
    DeckController deckController;
    public DeckMenu(User user) {
        super(user);
        this.deckController = new DeckController(user.getDecks());
    }
    public synchronized Response process(String command) {
        if (checkMenuExit(command)) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.MAIN);
        }
        else if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Deck Menu"), Situation.DECK);
        }
        else if(checkEnterMenu(command)) {
            enterMenu("Deck", command );
        }
        else if (command.equals("deck show -all")) {
            String string = "";
            string = "Decks:\n";
            string += "Active deck:\n";
            string += deckController.getDecks().getActiveDeck().getName() + "\n";
            string += "Other decks:\n";
            List<Deck> decks = deckController.getDecks().getDecks();
            for (Deck deck : decks)
                string += deck.getName() + "\n";
            return new Response(new Message(TypeMessage.SUCCESSFUL, string), Situation.DECK);
        }
        else if (command.equals("deck show -cards")) {
            String string = "";
            List<Card> cards = Card.getAllCards();
            for (Card card : cards) {
                string += card.toString() + "\n";
            }
            return new Response(new Message(TypeMessage.SUCCESSFUL, string), Situation.DECK);
        }
        else if (command.equals("getAllCards")){
            return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(Card.getAllCards())), Situation.SHOP);
        }
        else if (command.equals("getUser")){
            return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(user)), Situation.SHOP);
        }
        else {
            Matcher matcher = Global.getMatcher(command, "deck create (?<name>\\w)");
            if (matcher.find()) {
                String deckName = matcher.group("name");
                return new Response(deckController.create(deckName), Situation.DECK);
            }
            matcher = Global.getMatcher(command, "deck delete (?<name>\\w)");
            if (matcher.find()) {
                String deckName = matcher.group("name");
                Message message = deckController.delete(deckName);
                return new Response(message, Situation.DECK);
            }
            matcher = Global.getMatcher(command, "deck set-activate (?<name>\\w)");
            if (matcher.find()) {
                String deckName = matcher.group("name");
                return new Response(deckController.active(deckName), Situation.DECK);
            }
            matcher = Global.getMatcher(command, "deck add-card (?=.*(?:--card (?<cardName>\\w)))(?=.*(?:--deck (?<deckName>\\w)))(?=.*(?<opponent>--side)){0,1}");
            if (matcher.find()) {
                String deckName = matcher.group("deckName");
                String cardName = matcher.group("cardName");
                boolean isMainCard = true;
                if (matcher.group("side") != null) {
                    isMainCard = false;
                }
                Message message = deckController.addCard(cardName, deckName, isMainCard);
                return new Response(message, Situation.DECK);
            }
            matcher = Global.getMatcher(command, "deck rm-card (?=.*(?:--card (?<cardName>\\w)))(?=.*(?:--deck (?<deckName>\\w)))(?=.*(?<opponent>--side)){0,1}");
            if (matcher.find()) {
                String deckName = matcher.group("deckName");
                String cardName = matcher.group("cardName");
                boolean isMainCard = true;
                if (matcher.group("side") != null) {
                    isMainCard = false;
                }
                Message message = deckController.removeCard(cardName, deckName, isMainCard);
                return new Response(message, Situation.DECK);
            }
            matcher = Global.getMatcher(command, "deck show (?=.*(?:--deck (?<deckName>\\w)))(?=.*(?<opponent>--side)){0,1}");
            if (matcher.find()) {
                String deckName = matcher.group("deckName");
                boolean isMainCard = true;
                if (matcher.group("side") != null) {
                    isMainCard = false;
                }
                Message message = deckController.showDeckCards(deckName, isMainCard);
                return new Response(message, Situation.DECK);
            }
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.DECK);
    }
}