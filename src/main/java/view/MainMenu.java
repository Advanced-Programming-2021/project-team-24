package view;


import controller.DeckController;
import controller.MainMenuController;
import controller.Message;
import model.card.Card;
import model.deck.Deck;
import model.user.User;

import java.util.List;
import java.util.regex.Matcher;

public class MainMenu extends Menu {
    controller.MainMenuController mainMenuController;

    public MainMenu(User user) {
        super(user);
        this.mainMenuController = new MainMenuController();
    }

    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                return;
            } else if (command.equals("menu show-current")) {
                System.out.println("Main Menu");
            } else {
                Matcher matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--second-player (?<opponentUsername>\\w)))(?=.*(?:--rounds (?<rounds>\\d)))");
                if (matcher.find()) {
                    String opponentUsername = matcher.group("opponentUsername");
                    String rounds = matcher.group("rounds");
                    Message message = mainMenuController.createDuel(user,opponentUsername,rounds);
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--ai))(?=.*(?:--rounds))(?=.*(?:--rounds (?<rounds>\\d)))");
                if (matcher.find()) {
                    String rounds = matcher.group("rounds");
                    //TODO ai
                    continue;
                }
            }
            System.out.println("invalid command");
        }
    }
}