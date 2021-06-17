package view;


import controller.DuelController;
import controller.MainMenuController;
import controller.Message;
import model.card.Card;
import model.deck.Deck;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;

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
            if (checkMenuExit(command)) {
                exitMenu("Main");
                return;
            } else if (command.equals("menu show-current")) {
                System.out.println("Main Menu");
            }
            else if(command.equals("user logout")){
                System.out.println("user logged out successfully!");
                exitMenu("Main");
                return;
            }
            else if(checkEnterMenu(command)) {
                enterMenu("Main", command );
            }
            else {
                Matcher matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--second-player (?<opponentUsername>\\w)))(?=.*(?:--rounds (?<rounds>\\d)))");
                if (matcher.find()) {
                    String opponentUsername = matcher.group("opponentUsername");
                    String rounds = matcher.group("rounds");
                    Message message = mainMenuController.createDuel(user, User.readUser(opponentUsername), rounds);
                    System.out.println(message.getContent());
                    continue;
                }

                matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--ai))(?=.*(?:--rounds))(?=.*(?:--rounds (?<rounds>\\d)))");
                if (matcher.find()) {
                    String rounds = matcher.group("rounds");
                    //TODO ai
                    continue;
                }
                System.out.println("invalid command");
            }
        }
    }
    public static void main(String[] args) {
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        Deck alireza = new Deck("alireza");
        for(int i = 0; i < Card.getAllCards().size(); i++)
        {
            alireza.addMainCard(Card.getAllCards().get(i));
        }
        a.getDecks().add(alireza);
        a.getDecks().setActiveDeck(a.getDecks().getDeckByName("alireza"));
        b.getDecks().add(alireza);
        b.getDecks().setActiveDeck(b.getDecks().getDeckByName("alireza"));
        DuelMenu duelMenu = new DuelMenu(a, b, "1");
        DuelController dControleer = duelMenu.duelController;
        duelMenu.run();
        duelMenu.duelController.select(Address.get(Zone.get("hand", dControleer.getDuel().getCurrentPlayer()), 1));
        System.out.println(dControleer.set().getContent());

    }
}