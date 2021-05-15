package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import controller.DuelController;
import controller.Message;
import model.card.CardHolder;
import model.duel.Duel;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;

import java.util.List;
import java.util.regex.Matcher;

public class DuelMenu {

    DuelController duelController;

    public DuelMenu(User user, User opponent, String rounds) {
        this.duelController = new DuelController(new Duel(user, opponent, rounds));
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
                duelController.summon();
            } else if (command.equals("flip-summon")) {
                duelController.flipSummon();
            } else if (command.equals("set")) {
                duelController.set();
            } else if (command.equals("attack direct")) {
                duelController.directAttack();
            } else if (command.equals("activate effect")) {
                //TODO activate effect
            }
            else if (command.equals("next phase")){
                duelController.runPhase();
            }
            else if (command.equals("show graveyard")) {
                List<CardHolder> graveyard = getZoneCards("graveyard",false);
                if (graveyard.isEmpty()) System.out.println("graveyard empty");
                else showCardList(graveyard);
            } else if (command.equals("card show --selected")) {

                Message message = duelController.showSelectedCard();
                System.out.println(message.getContent());

            } else if (command.equals("surrender")) {
                //TODO surrender
            } else {
                //<select>
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
                        Address address = new Address(new Zone(zoneName, duelController.getDuel().getCurrentPlayer()), place);
                        Message message = duelController.select(address);
                        System.out.println(message.getContent());
                    } else System.out.println("invalid selection");
                    continue;
                }

                matcher = Global.getMatcher(command, "set --position (?<position>attack|defense)");
                if (matcher.find()) {
                    duelController.changePosition();
                    continue;
                }

                matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
                if (matcher.find()) {
                    duelController.directAttack();
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


    public Boolean BooleanQYN(String question) {
        System.out.println(question);
        String out = Global.nextLine();
        if (out.toLowerCase().equals("y"))
            return true;
        else if (out.toLowerCase().equals("n"))
            return false;
        else {
            System.out.println("please enter valid answer");
            return BooleanQYN(question);
        }
    }

    public Integer Dice() {

        Integer ans = Global.random.nextInt(6) + 1;
        return ans;
        //TODO in gui    
    }

    public List<Integer> randomSelection(List<Integer> cardHolderId, int count, String message) {
        if (message != null && message.length() > 0)
            System.out.println(message);
        //Random then show selected by address
        return null;
    }

    public void changeLP(Player player, int amount) {

    }

    public List<Integer> selective(List<Integer> cardHolderId, int count, String message) {
        if (message != null && message.length() > 0)
            System.out.println(message);
        List<Integer> ans = new ArrayList<Integer>();
            //TODO
        //How to implement??
        //maybe implement by address
        return ans;
    }

    public List<Integer> selective(List<Integer> cardHolderId, int count, String message, String condition)
    {
        if(message != null && message.length() > 0)
        {
            System.out.println(message);
        }        
        List<Integer> ans = new ArrayList<Integer>();
        while(true)
        {
            if(true)//condition is satisfied
                break;
        }
        //two case 
        return ans;
    }
    public Integer coin()
    {
        Integer ans = Global.random.nextInt(2);
        return ans;
    }

    public List<CardHolder> getZoneCards(String zoneName, Boolean isOpponent) {
        Player player = getPlayer(isOpponent);
        return duelController.getDuel().getZone(new Zone(zoneName, player));
    }

    public Player getPlayer(Boolean isOpponent){
        if (isOpponent) return duelController.getDuel().getOtherPlayer();
        else return duelController.getDuel().getCurrentPlayer();
    }

    public void showBoard() {
        System.out.println(duelController.getDuel().getOtherPlayer().toString());

        for(int i=0;i<getZoneCards("hand",true).size();i++) System.out.print("\tc");
        System.out.println();

        System.out.println(getZoneCards("mainDeck",true).size());

        System.out.format("|%-4s|",getCard("magic",true,4));
        System.out.format("|%-4s|",getCard("magic",true,2));
        System.out.format("|%-4s|",getCard("magic",true,1));
        System.out.format("|%-4s|",getCard("magic",true,3));
        System.out.format("|%-4s|",getCard("magic",true,5));
        System.out.println();

        System.out.format("|%-4s|",getCard("monster",true,4));
        System.out.format("|%-4s|",getCard("monster",true,2));
        System.out.format("|%-4s|",getCard("monster",true,1));
        System.out.format("|%-4s|",getCard("monster",true,3));
        System.out.format("|%-4s|",getCard("monster",true,5));
        System.out.println();

        System.out.format("|%-31d|",getZoneCards("graveyard",true).size());
        System.out.println(getZoneCards("field",true).size());

        System.out.println("--------------------------");

        System.out.format("|%-31d|",getZoneCards("field",false).size());
        System.out.println(getZoneCards("graveyard",false).size());

        System.out.format("|%-4s|",getCard("monster",false,5));
        System.out.format("|%-4s|",getCard("monster",false,3));
        System.out.format("|%-4s|",getCard("monster",false,1));
        System.out.format("|%-4s|",getCard("monster",false,2));
        System.out.format("|%-4s|",getCard("monster",false,4));
        System.out.println();

        System.out.format("|%-4s|",getCard("magic",false,5));
        System.out.format("|%-4s|",getCard("magic",false,3));
        System.out.format("|%-4s|",getCard("magic",false,1));
        System.out.format("|%-4s|",getCard("magic",false,2));
        System.out.format("|%-4s|",getCard("magic",false,4));
        System.out.println();

        System.out.format("|%32d|",getZoneCards("mainDeck",false).size());

        for(int i=0;i<getZoneCards("hand",false).size();i++) System.out.print("c\t");
        System.out.println();

        System.out.println(duelController.getDuel().getCurrentPlayer().toString());
    }

    public String getCard(String zoneName,Boolean isOpponent,int place){
        return duelController.getDuel().getMap().get(new Address(new Zone(zoneName,getPlayer(isOpponent)),place)).getCardState().toString();
    }

}
