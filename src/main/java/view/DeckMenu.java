package view;

import java.util.List;
import java.util.regex.Matcher;

import controller.DeckController;
import controller.Message;
import model.card.Card;
import model.deck.Deck;
import model.user.User;

public class DeckMenu extends Menu {
    //TODO toStrings

    DeckController deckController;

    public DeckMenu(User user) {
        super(user);
        this.deckController = new DeckController(user.getDecks());
    }

    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";

    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (checkMenuExit(command)) {
                exitMenu("Deck");
                return;
            }
            else if (command.equals("menu show-current")) {
                System.out.println("Deck Menu");
            }
            else if(checkEnterMenu(command)) {
                enterMenu("Deck", command );
            }
            else if (command.equals("deck show -all")) {

                System.out.println("Decks:");
                System.out.println("Active deck:");
                System.out.println(deckController.getDecks().getActiveDeck().toString());
                System.out.println("Other decks:");
                List<Deck> decks = deckController.getDecks().getDecks();
                for (Deck deck : decks)
                    System.out.println(decks.toString());

            }
            else if (command.equals("deck show -cards")) {
                List<Card> cards = Card.getAllCards();
                for (Card card : cards) {
                    System.out.println(card.toString());
                }
            }
            else {
                Matcher matcher = Global.getMatcher(command, "deck create (?<name>\\w)");
                if (matcher.find()) {
                    String deckName = matcher.group("name");
                    Message message = deckController.create(deckName);
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "deck delete (?<name>\\w)");
                if (matcher.find()) {
                    String deckName = matcher.group("name");
                    Message message = deckController.delete(deckName);
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "deck set-activate (?<name>\\w)");
                if (matcher.find()) {
                    String deckName = matcher.group("name");
                    Message message = deckController.active(deckName);
                    System.out.println(message.getContent());
                    continue;
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
                    System.out.println(message.getContent());
                    continue;
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
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "deck change-cards (?=.*(?:--main card (?<mainCardName>\\w)))(?=.*(?:--side card (?<sideCardName>\\w)))");
                if (matcher.find()) {
                    String mainCardName = matcher.group("mainCardName");
                    String sideCardName = matcher.group("sideCardName");
                    Message message = deckController.change(mainCardName, sideCardName);
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "deck show (?=.*(?:--deck (?<deckName>\\w)))(?=.*(?<opponent>--side)){0,1}");
                if (matcher.find()) {
                    String deckName = matcher.group("deckName");
                    boolean isMainCard = true;
                    if (matcher.group("side") != null) {
                        isMainCard = false;
                    }
                    Message message = deckController.showDeckCards(deckName, isMainCard);
                    System.out.println(message.getContent());
                    continue;
                }
                System.out.println("invalid command");
            }
        }
    }

}