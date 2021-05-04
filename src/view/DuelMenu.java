package view;


import static Global.regexFind;

import java.util.regex.Matcher;


import model.zones.Address;

public class DuelMenu {

    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";

    public void run() {
        while (true) {
            String command = Global.nextLine();
            if (command.equals("menu exit")) {
                return;
            }
            else if (command.equals("menu show-current")) {
                System.out.println("Duel Menu");
            }
            else if(command.equals("select -d")){
                controller.DuelMenu.deselect();
            }
            else if(command.equals("summon")){
                //TODO summon
            }
            else if(command.equals("flip-summon")){
                //TODO flip
            }
            else if(command.equals("set")){
                //TODO set
            }
            else if(command.equals("attack direct")){
                //TODO direct-attack
            }
            else if(command.equals("activate effect")){
                //TODO activate effect
            }
            else if(command.equals("show graveyard")){
                //TODO show graveyard
            }
            else if(command.equals("card show --selected")){
                controller.DuelMenu.showSelectedCard();
            }
            else if(command.equals("surrender")){
                //TODO surrender
            }

            Matcher matcher = Global.getMatcher(command, "select (?<zone>--\\w+\\s+){1,2}(?<place>\\d)");
            if (matcher.find()) {
                String zone = matcher.group("zone");
                matcher = Global.getMatcher(zone, "(?=.*(?<name>--(?:monster|spell|field|hand)))(?=.*(?<opponent>--opponent)){0,1}");
                if (matcher.find()) {
                    int place = Integer.parseInt(matcher.group("place"));
                    String zoneName = matcher.group("name");
                    Boolean opponent = false;
                    if (matcher.group("opponent") != null) {
                        opponent = true;
                    }
                    Address address = new Address(zoneName, opponent, place);
                    controller.DuelMenu.select(address);
                } else System.out.println("invalid selection");
                continue;
            }

            matcher = Global.getMatcher(command, "set --position (?<position>attack|defense)");
            if(matcher.find()){
                //TODO change position
                continue;
            }

            matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
            if(matcher.find()){
                //TODO attack
                continue;
            }

            matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
            if(matcher.find()){
                //TODO attack
                continue;
            }

            System.out.println("invalid command");
        }
    }

    public static void main(String[] args) {
        new DuelMenu().run();
    }
}
