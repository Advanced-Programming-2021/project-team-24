package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import controller.DuelController;
import controller.Message;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.deck.Deck;
import model.duel.Duel;
import model.duel.Duel.Phase;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;

import java.util.List;
import java.util.regex.Matcher;

public class DuelMenu {

    DuelController duelController;

    public DuelMenu(Player user, Player opponent) {
        this.duelController = new DuelController(new Duel(user, opponent));
        duelController.setDuelMenu(this);
    }
    private static HashMap<CardState, String> stringOfCardState = new HashMap<CardState, String>();    
    static
    {
        stringOfCardState.put(CardState.ATTACK_MONSTER, "OO");
        stringOfCardState.put(CardState.SET_DEFENCE, "DH");
        stringOfCardState.put(CardState.DEFENCE_MONSTER, "DO");
        stringOfCardState.put(CardState.ACTIVE_MAGIC, "O");
        stringOfCardState.put(CardState.SET_MAGIC, "H");
        stringOfCardState.put(CardState.HAND, "c");        
    }

    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";


    public void run() {
        while (true) {
            if (duelController.isRoundFinished()) return;
            else{
                if(duelController.getDuel().getCurrentPhase() == Phase.DRAW)
                {
                    System.out.println(duelController.draw().getContent());
                    System.out.println(duelController.nextPhase().getContent());
                    continue;
                }
                else
                if(duelController.getDuel().getCurrentPhase() == Phase.STANDBY)
                {
                    duelController.updateAutomaticEffect();
                    System.out.println(duelController.nextPhase().getContent());
                    continue;
                }
                String command = Global.nextLine();
                if (command.equals("menu exit")) {
                    return;
                } else if (command.equals("menu show-current")) {
                    System.out.println("Duel Menu");
                    continue;
                }
                else if (command.equals("surrender")){
                    duelController.surrender(true);//change argument later
                    return;
                }
                else if (command.equals("select -d")) {
                    Message message = duelController.deselect(); 
                    System.out.println(message.getContent()); continue;
                } else if (command.equals("summon")) {
                    System.out.println(duelController.summon().getContent());continue;
                } else if (command.equals("flip-summon")) {
                    System.out.println(duelController.flipSummon().getContent());continue;
                } else if (command.equals("set")) {
                    System.out.println(duelController.set().getContent());continue;
                } else if (command.equals("attack direct")) {
                    System.out.println(duelController.directAttack().getContent());continue;
                } else if (command.equals("activate effect")) {                    
                    System.out.println(duelController.activeMagic().getContent());continue;
                }else if(command.equals("show board")){
                    showBoard();continue;
                }
                else if (command.equals("next phase")){
                    System.out.println(duelController.nextPhase().getContent());
                    if(duelController.getDuel().getCurrentPhase() == Phase.END)
                    {
                        System.out.println(duelController.nextPhase().getContent());
                        continue;
                    }
                    continue;
                }
                else if (command.equals("show graveyard")) {
                    List<CardHolder> graveyard = getZoneCards("graveyard",false);
                    if (graveyard.isEmpty()) System.out.println("graveyard empty");
                    else showCardList(graveyard);
                    continue;
                } else if (command.equals("card show --selected")) {
                    Message message = duelController.showSelectedCard();
                    System.out.println(message.getContent());continue;                    
                } else {
                    //<select>
                    Matcher matcher = Global.getMatcher(command, "select (?<zone>(?:--\\w+\\s*\\d*){1,2})");
                    if (matcher.find()) {                   
                        Address selectionAddress = getAddress(command);
                        if (selectionAddress != null) {
                            Message message = duelController.select(selectionAddress);
                            System.out.println(message.getContent());
                        } else System.out.println("invalid selection");
                        continue;
                    }

                    matcher = Global.getMatcher(command, "set --position (?<position>attack|defense)");
                    if (matcher.find()) {
                        System.out.println(duelController.changePosition());
                        continue;
                    }

                    matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
                    if (matcher.find()) {
                        System.out.println(duelController.attack(Address.get(Zone.get("monster", duelController.getDuel().getOpponent()),Integer.parseInt(matcher.group("place")))).getContent());
                        continue;
                    }
                    matcher = Global.getMatcher(command, "attack direct");
                    if(matcher.find())
                    {
                        if(duelController.getZone(Zone.get("monster", duelController.getDuel().getOpponent())).size() > 0)
                        {
                            System.out.println("you can't perform direct attack");
                        }
                        else
                        {
                            System.out.println(duelController.directAttack().getContent());
                        }
                        continue;
                    }                    
                }
                System.out.println("invalid command");
            }
        }
    }
    public DuelController getDuelController()
    {
        return this.duelController;
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
    private Address getAddress(String addressString)
    {
        Matcher matcher = Global.getMatcher(addressString, "select (?<zone>(?:--\\w+\\s*\\d*){1,2})");
        if(matcher.find())
        {
            matcher = Global.getMatcher(matcher.group("zone"), "(?=.*(?<name>--(?:monster|magic|field|hand)))(?=.*(?<place>\\d))(?=.*(?<opponent>--opponent)){0,1}");
            if(matcher.find())
            {
                int place = Integer.parseInt(matcher.group("place"));

                String zoneName = matcher.group("name").replaceAll("-", "");
                Boolean opponent = false;
                if (matcher.group("opponent") != null) {
                    opponent = true;
                }
                Address address;
                if(!opponent)
                    address = Address.get(Zone.get(zoneName, duelController.getDuel().getCurrentPlayer()), place);
                else
                    address = Address.get(Zone.get(zoneName, duelController.getDuel().getOpponent()), place);
                return address;       
            }
            else
                return null;
        }
        else
            return null;
    }
    public void changeLP(Player player, int amount) {

    }
    private boolean checkCardHolderInList(List<Integer> cardHolderId, CardHolder cardHolder)
    {
        for(int i = 0; i < cardHolderId.size(); i++)
            if(cardHolderId.get(i) == cardHolder.getId())
                return true;
        return false;
    }
    public List<Integer> selective(List<Integer> cardHolderId, int count, String messageSelection) {
        if (messageSelection != null && messageSelection.length() > 0)
            System.out.println(messageSelection);
        
        List<Integer> ans = new ArrayList<Integer>();
        while(true)
        {
            for(int i = 0; i < count; i++)
            {
                String command = Global.scanner.nextLine();                
                Address address = getAddress(command);
                if(address != null &&
                 getDuelController().getDuel().getMap().get(address) != null &&
                 checkCardHolderInList(cardHolderId, getDuelController().getDuel().getMap().get(address)))
                {                    
                    ans.add(getDuelController().getDuel().getMap().get(address).getId());
                }
                else
                {
                    i--;
                    System.out.println("invalid selection");
                }
            }
            int flag = 0;
            for(int i = 0; i < count; i++)
            {
                for(int j = 0; j < count; j++)
                {
                    if(i != j && ans.get((i)) == ans.get(j))
                    {
                        flag = 1;
                    }
                }
            }
            if(flag == 0)
                break;                
            else
                System.out.println("You have same number in your input");
        }
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
        return duelController.getDuel().getZone(Zone.get(zoneName, player));
    }

    public Player getPlayer(Boolean isOpponent){
        if (isOpponent) return duelController.getDuel().getOpponent();
        else return duelController.getDuel().getCurrentPlayer();
    }

    public void showBoard() {

        for(int i=0;i<getZoneCards("hand",true).size();i++) System.out.print("c   ");
        System.out.println();

        System.out.println(getZoneCards("deck",true).size());

        System.out.format("|%-4s|",getCard("magic",true,3));
        System.out.format("|%-4s|",getCard("magic",true,1));
        System.out.format("|%-4s|",getCard("magic",true,0));
        System.out.format("|%-4s|",getCard("magic",true,2));
        System.out.format("|%-4s|",getCard("magic",true,4));
        System.out.println();

        System.out.format("|%-4s|",getCard("monster",true,3));
        System.out.format("|%-4s|",getCard("monster",true,1));
        System.out.format("|%-4s|",getCard("monster",true,0));
        System.out.format("|%-4s|",getCard("monster",true,2));
        System.out.format("|%-4s|",getCard("monster",true,4));
        System.out.println();

        System.out.format("|%-31d|",getZoneCards("graveyard",true).size());
        System.out.println(getZoneCards("field",true).size());

        System.out.println("--------------------------");

        System.out.format("|%-31d|",getZoneCards("field",false).size());
        System.out.println(getZoneCards("graveyard",false).size());

        System.out.format("|%-4s|",getCard("monster",false,4));
        System.out.format("|%-4s|",getCard("monster",false,2));
        System.out.format("|%-4s|",getCard("monster",false,0));
        System.out.format("|%-4s|",getCard("monster",false,1));
        System.out.format("|%-4s|",getCard("monster",false,3));
        System.out.println();

        System.out.format("|%-4s|",getCard("magic",false,4));
        System.out.format("|%-4s|",getCard("magic",false,2));
        System.out.format("|%-4s|",getCard("magic",false,0));
        System.out.format("|%-4s|",getCard("magic",false,1));
        System.out.format("|%-4s|",getCard("magic",false,3));
        System.out.println();

        System.out.format("|%d|",getZoneCards("deck",false).size());

        for(int i=0;i<getZoneCards("hand",false).size();i++) System.out.print("c    ");
        System.out.println();

    }

    public String getCard(String zoneName,Boolean isOpponent,int place){
        if(duelController.getDuel().getMap().get(Address.get(Zone.get(zoneName,getPlayer(isOpponent)),place))== null)
        {
            return "E";
        }
        return stringOfCardState.get(duelController.getDuel().getMap().get(Address.get(Zone.get(zoneName,getPlayer(isOpponent)),place)).getCardState());
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
        DuelMenu duelMenu = new DuelMenu(new Player(a), new Player(b));
        duelMenu.run();
    }

}