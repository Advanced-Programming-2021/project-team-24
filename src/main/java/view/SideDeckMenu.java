package view;

import controller.DeckController;
import controller.Message;
import controller.SideDeckController;
import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;
import model.user.User;

import java.util.List;
import java.util.regex.Matcher;

public class SideDeckMenu extends Menu{
    SideDeckController sideDeckController;

    public SideDeckMenu(User user) {
        super(user);
        this.sideDeckController = new SideDeckController(user.getDecks());
    }
    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                return;
            }
            Matcher matcher = Global.getMatcher(command, "deck change-cards (?=.*(?:--main card (?<mainCardName>\\w+)))(?=.*(?:--side card (?<sideCardName>\\w+)))");
            if (matcher.find()) {
                String mainCardName = matcher.group("mainCardName");
                String sideCardName = matcher.group("sideCardName");
                Message message = sideDeckController.change(mainCardName, sideCardName);
                System.out.println(message.getContent());
                continue;
            }
            System.out.println("invalid command");
        }
    }
}
