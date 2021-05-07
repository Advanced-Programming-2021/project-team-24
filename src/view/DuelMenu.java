package view;

import java.util.List;
import java.util.regex.Matcher;

import model.card.Card;
import model.card.CardHolder;
import model.user.User;


import controller.DuelController;
import controller.Message;
import model.Duel;
import model.zone.Address;
import model.zone.Zone;

public class DuelMenu {

    DuelController duelController;

    public DuelMenu(User user, String opponentUsername) {
        this.duelController = new DuelController(new Duel(user, User.readUser(opponentUsername)));
    }
    private void showGraveyard()
    {

    }
    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";

    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                return;
            } else if (command.equals("menu show-current")) {
                System.out.println("Duel Menu");
            } else if (command.equals("select -d")) {
                Message message = duelController.deselect();
                System.out.println(message.getContent());
            } else if (command.equals("summon")) {
                //TODO summon
            } else if (command.equals("flip-summon")) {
                //TODO flip
            } else if (command.equals("set")) {
                //TODO set
            } else if (command.equals("attack direct")) {
                //TODO direct-attack
            } else if (command.equals("activate effect")) {
                //TODO activate effect
            } else if (command.equals("show graveyard")) {
                List<CardHolder> graveyard = duelController.getZone(new Zone("graveyard", false));
                if (graveyard.isEmpty()) System.out.println("graveyard empty");
                else showCardList(graveyard);
            } else if (command.equals("card show --selected")) {

                Message message = duelController.showSelectedCard();
                System.out.println(message.getContent());
                
            } else if (command.equals("surrender")) {
                //TODO surrender
            } else {
                Matcher matcher = Global.getMatcher(command, "select (?<address>(?:--\\w+\\s*\\d*){1,2})");
                if (matcher.find()) {
                    String zone = matcher.group("zone");
                    matcher = Global.getMatcher(zone, "(?=.*(?<name>--(?:monster|spell|field|hand)))(?=.*(?<place>\\d))(?=.*(?<opponent>--opponent)){0,1}");
                    if (matcher.find()) {
                        int place = Integer.parseInt(matcher.group("place"));
                        String zoneName = matcher.group("name");
                        Boolean opponent = false;
                        if (matcher.group("opponent") != null) {
                            opponent = true;
                        }
                        Address address = new Address(new Zone(zoneName, opponent), place);
                        Message message = duelController.select(address);
                        System.out.println(message.getContent());
                    } else System.out.println("invalid selection");
                    continue;
                }

                matcher = Global.getMatcher(command, "set --position (?<position>attack|defense)");
                if (matcher.find()) {
                    //TODO change position
                    continue;
                }

                matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
                if (matcher.find()) {
                    //TODO attack
                    continue;
                }
            }
            System.out.println("invalid command");
        }
    }

    private void showCardList(List<CardHolder> cardHolders) {
        int i = 0;
        for (CardHolder cardHolder : cardHolders) {
            i++;
            System.out.println(i + ". " + cardHolder.toString());
        }
    }

}
