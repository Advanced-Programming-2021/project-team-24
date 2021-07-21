package controller.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import controller.DuelController;
import controller.Message;
import controller.TypeMessage;
import controller.server.GsonConverter;
import model.Response;
import model.Situation;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.deck.Deck;
import model.duel.Duel;
import model.duel.EffectParser;
import model.duel.Duel.Phase;
import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;

public class DuelMenu {

    DuelController duelController;

    public DuelMenu(Player user, Player opponent) {
        this.duelController = new DuelController(new Duel(user, opponent));
        duelController.setDuelMenu(this);
    }

    private static HashMap<CardState, String> stringOfCardState = new HashMap<CardState, String>();

    static {
        stringOfCardState.put(CardState.ATTACK_MONSTER, "OO");
        stringOfCardState.put(CardState.SET_DEFENCE, "DH");
        stringOfCardState.put(CardState.VISIBLE_MAGIC, "O");
        stringOfCardState.put(CardState.DEFENCE_MONSTER, "DO");
        stringOfCardState.put(CardState.ACTIVE_MAGIC, "O");
        stringOfCardState.put(CardState.SET_MAGIC, "H");
        stringOfCardState.put(CardState.HAND, "c");
    }

    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";


    public synchronized Response process(String command) {
        if (duelController.isRoundFinished()) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "End"), Situation.DUEL);
        } else {
            if(checkPhase()) return new Response(new Message(TypeMessage.ERROR, ""), Situation.DUEL);
             if (command.equals("menu show-current")) {
                 return new Response(new Message(TypeMessage.SUCCESSFUL, "Duel Menu"), Situation.DUEL);
            } else if (command.equals("surrender")) {
                duelController.surrender();
                 return new Response(new Message(TypeMessage.SUCCESSFUL, "End"), Situation.DUEL);
            } else if (command.equals("select -d")) {
                Message message = duelController.deselect();
                 return new Response(message, Situation.DUEL);
             } else if (command.equals("summon")) {
                 return new Response(duelController.summon(), Situation.DUEL);
            } else if (command.equals("flip-summon")) {
                 return new Response(duelController.flipSummon(), Situation.DUEL);
            } else if (command.equals("set")) {
                 return new Response(duelController.set(), Situation.DUEL);
            } else if (command.equals("command")) {
                command = Global.nextLine();
                EffectManager effectManager = new EffectManager(new Effect(""), duelController.getDuel().getCurrentPlayer(), duelController.getDuel().getCurrentPlayer().getMap().getId());
                new EffectParser(this, duelController, effectManager).getCommandResult(command);
            } else if (command.equals("attack direct")) {
                 return new Response(duelController.directAttack(), Situation.DUEL);
            } else if (command.equals("activate effect")) {
                 return new Response(duelController.activeMagic(), Situation.DUEL);
            }
//             else if (command.equals("show board")) {
//                return showBoard();
//            }
             else if (command.equals("next phase")) {
                 String string = "";
                 string += duelController.nextPhase().getContent() + "\n";
                 if (duelController.getDuel().getCurrentPhase() == Phase.END) {
                     string += duelController.nextPhase().getContent() + "\n";
                 }
                 return new Response(new Message(TypeMessage.SUCCESSFUL, string), Situation.DUEL);
                    //duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get("monster", duelController.getDuel().getOpponent()), 1);
            }
            else if (command.equals("getPhaseName")){
                return new Response(new Message(TypeMessage.INFO, duelController.getDuel().getCurrentPhase().toString()), Situation.DUEL);
             }
            else if (command.equals("getMap")){

                return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(duelController.getDuel().getMap())), Situation.DUEL);
             }
            else if (command.equals("isRoundFinished")){
                 return new Response(new Message(TypeMessage.INFO, duelController.isRoundFinished().toString()), Situation.DUEL);
             }
            else if (command.equals("getCurrentPlayer")){
                 return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(duelController.getDuel().getCurrentPlayer())), Situation.DUEL);
            }
             else if (command.equals("getOpponentPlayer")){
                 return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(duelController.getDuel().getOpponent())), Situation.DUEL);
             }
//             else if (command.equals("show graveyard")) {
//                List<CardHolder> graveyard = getZoneCards("graveyard", false);
//                if (graveyard.isEmpty()) System.out.println("graveyard empty");
//                else showCardList(graveyard);
//                continue;
//            }
             else if (command.equals("card show --selected")) {
                Message message = duelController.showSelectedCard();
                 return new Response(message, Situation.DUEL);
            } else {
                //<select>
                Matcher matcher = Global.getMatcher(command, "select (?<zone>(?:--\\w+\\s*\\d*){1,2})");
                if (matcher.find()) {
                    Address selectionAddress = getAddress(command);
                    if (selectionAddress != null) {
                        Message message = duelController.select(selectionAddress);
                        return new Response(message, Situation.DUEL);
                    } else return new Response(new Message(TypeMessage.ERROR, "invalid selection"), Situation.DUEL);
                }
                matcher = Global.getMatcher(command, "set --position (?<position>attack|defence)");
                if (matcher.find()) {
                    return new Response(duelController.changePosition(), Situation.DUEL);
                }
                matcher = Global.getMatcher(command, "attack (?<place>[0-4])");
                if (matcher.find()) {
                    return new Response(duelController.attack(duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get("monster", duelController.getDuel().getOpponent()), Integer.parseInt(matcher.group("place")))), Situation.DUEL);
                }
                matcher = Global.getMatcher(command, "attack direct");
                if (matcher.find()) {
                    if (duelController.getZone(duelController.getDuel().duelZones.get("monster", duelController.getDuel().getOpponent())).size() > 0) {
                        return new Response(new Message(TypeMessage.ERROR, "you can't perform direct attack"), Situation.DUEL);
                    } else {
                        return new Response(duelController.directAttack(), Situation.DUEL);
                    }
                }
            }
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.DUEL);
    }

    public boolean checkPhase() {
        if (duelController.getDuel().getCurrentPhase() == Phase.DRAW) {
            System.out.println(duelController.draw().getContent());
            System.out.println(duelController.nextPhase().getContent());
            return true;
        } else if (duelController.getDuel().getCurrentPhase() == Phase.STANDBY) {
            duelController.updateAutomaticEffect();
            System.out.println(duelController.nextPhase().getContent());
            return true;
        }
        return false;
    }

    public DuelController getDuelController() {
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

    private Address getAddress(String addressString) {
        Matcher matcher = Global.getMatcher(addressString, "select (?<zone>(?:--\\w+\\s*\\d*){1,2})");
        if (matcher.find()) {
            matcher = Global.getMatcher(matcher.group("zone"), "(?=.*(?<name>--(?:monster|magic|field|hand|graveyard)))(?=.*(?<place>\\d))(?=.*(?<opponent>--opponent)){0,1}");
            if (matcher.find()) {
                int place = Integer.parseInt(matcher.group("place"));

                String zoneName = matcher.group("name").replaceAll("-", "");
                Boolean opponent = false;
                if (matcher.group("opponent") != null) {
                    opponent = true;
                }
                Address address;
                if (!opponent)
                    address = duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get(zoneName, duelController.getDuel().getCurrentPlayer()), place);
                else
                    address = duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get(zoneName, duelController.getDuel().getOpponent()), place);
                return address;
            } else
                return null;
        } else
            return null;
    }

    public void changeLP(Player player, int amount) {

    }

    private boolean checkCardHolderInList(List<Integer> cardHolderId, CardHolder cardHolder) {
        for (int i = 0; i < cardHolderId.size(); i++)
            if (cardHolderId.get(i) == cardHolder.getId())
                return true;
        return false;
    }

    public List<Integer> selective(List<Integer> cardHolderId, int count, String messageSelection) {
        if (messageSelection != null && messageSelection.length() > 0)
            System.out.println(messageSelection);

        List<Integer> ans = new ArrayList<Integer>();
        while (true) {
            for (int i = 0; i < count; i++) {
                String command = Global.nextLine();
                Address address = getAddress(command);
                if (address != null &&
                        getDuelController().getDuel().getMap().get(address) != null &&
                        checkCardHolderInList(cardHolderId, getDuelController().getDuel().getMap().get(address))) {
                    ans.add(getDuelController().getDuel().getMap().get(address).getId());
                } else {
                    i--;
                    //getDuelController().getDuel().getZone(duelController.getDuel().duelZones.get("monster", duelController.getDuel().getOpponent()));
                    System.out.println("invalid selection");
                }
            }
            int flag = 0;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    if (i != j && ans.get((i)) == ans.get(j)) {
                        flag = 1;
                    }
                }
            }
            if (flag == 0)
                break;
            else
                System.out.println("You have same number in your input");
        }
        return ans;
    }

    public List<Integer> selective(List<Integer> cardHolderId, int count, String message, String condition) {
        if (message != null && message.length() > 0)
            System.out.println(message);

        List<Integer> ans = new ArrayList<Integer>();
        while (true) {
            count = 10;
            int sizeSelection = 0;
            for (int i = 0; i < count; i++) {
                String command = Global.nextLine();
                Address address = getAddress(command);
                if (address != null &&
                        getDuelController().getDuel().getMap().get(address) != null &&
                        checkCardHolderInList(cardHolderId, getDuelController().getDuel().getMap().get(address))) {
                    ans.add(getDuelController().getDuel().getMap().get(address).getId());
                } else {
                    i--;
                    System.out.println("invalid selection");
                }
                EffectManager x = new EffectManager(new Effect(""), duelController.getDuel().getCurrentPlayer(), duelController.getDuel().getCurrentPlayer().getMap().getId());
                List<EffectManager> temp = new ArrayList<EffectManager>();
                temp.add(x);
                duelController.getDuel().getCurrentPlayer().getMap().getEffects().put(Event.REVERSE, temp);
                sizeSelection++;
                EffectParser effectParser = new EffectParser(this, duelController, x);
                effectParser.getCommandResult(command);
                if (Boolean.parseBoolean(effectParser.getAns()) == true) {
                    break;
                }
            }
            int flag = 0;
            for (int i = 0; i < sizeSelection; i++) {
                for (int j = 0; j < sizeSelection; j++) {
                    if (i != j && ans.get((i)) == ans.get(j)) {
                        flag = 1;
                    }
                }
            }
            if (flag == 0)
                break;
            else
                System.out.println("You have same number in your input");
        }
        return ans;
    }

    public Integer coin() {
        Integer ans = Global.random.nextInt(2);
        return ans;
    }

    public List<CardHolder> getZoneCards(String zoneName, Boolean isOpponent) {
        Player player = getPlayer(isOpponent);
        return duelController.getDuel().getZone(duelController.getDuel().duelZones.get(zoneName, player));
    }

    public Player getPlayer(Boolean isOpponent) {
        if (isOpponent) return duelController.getDuel().getOpponent();
        else return duelController.getDuel().getCurrentPlayer();
    }

    public String getFieldZone(String zoneName, Boolean isOpponent) {
        if (getZoneCards(zoneName, isOpponent).size() == 0) return "E";
        return "O";
    }

    public void showBoard() {
        System.out.println(getPlayer(true).getUser().getNickname() + ":" + getPlayer(true).getLifePoint());
        for (int i = 0; i < getZoneCards("hand", true).size(); i++) System.out.print("c   ");
        System.out.println();

        System.out.println(getZoneCards("deck", true).size());

        System.out.format("|%-4s|", getCard("magic", true, 3));
        System.out.format("|%-4s|", getCard("magic", true, 1));
        System.out.format("|%-4s|", getCard("magic", true, 0));
        System.out.format("|%-4s|", getCard("magic", true, 2));
        System.out.format("|%-4s|", getCard("magic", true, 4));
        System.out.println();

        System.out.format("|%-4s|", getCard("monster", true, 3));
        System.out.format("|%-4s|", getCard("monster", true, 1));
        System.out.format("|%-4s|", getCard("monster", true, 0));
        System.out.format("|%-4s|", getCard("monster", true, 2));
        System.out.format("|%-4s|", getCard("monster", true, 4));
        System.out.println();

        System.out.format("|%-31d|", getZoneCards("graveyard", true).size());
        System.out.println(getFieldZone("field", true));

        System.out.println("--------------------------");

        System.out.format("|%-31s|", getFieldZone("field", false));
        System.out.println(getZoneCards("graveyard", false).size());

        System.out.format("|%-4s|", getCard("monster", false, 4));
        System.out.format("|%-4s|", getCard("monster", false, 2));
        System.out.format("|%-4s|", getCard("monster", false, 0));
        System.out.format("|%-4s|", getCard("monster", false, 1));
        System.out.format("|%-4s|", getCard("monster", false, 3));
        System.out.println();

        System.out.format("|%-4s|", getCard("magic", false, 4));
        System.out.format("|%-4s|", getCard("magic", false, 2));
        System.out.format("|%-4s|", getCard("magic", false, 0));
        System.out.format("|%-4s|", getCard("magic", false, 1));
        System.out.format("|%-4s|", getCard("magic", false, 3));
        System.out.println();

        System.out.format("|%d|", getZoneCards("deck", false).size());

        for (int i = 0; i < getZoneCards("hand", false).size(); i++) System.out.print("c    ");
        System.out.println();
        System.out.println(getPlayer(false).getUser().getNickname() + ":" + getPlayer(false).getLifePoint());

    }

    public String getCard(String zoneName, Boolean isOpponent, int place) {
        if (duelController.getDuel().getMap().get(duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get(zoneName, getPlayer(isOpponent)), place)) == null) {
            return "E";
        }
        return stringOfCardState.get(duelController.getDuel().getMap().get(duelController.getDuel().duelAddresses.get(duelController.getDuel().duelZones.get(zoneName, getPlayer(isOpponent)), place)).getCardState());
    }

    public static void main(String[] args) {
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        Deck alireza = new Deck("alireza");
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            alireza.addMainCard(Card.getAllCards().get(i));
        }
        a.getDecks().add(alireza);
        a.getDecks().setActiveDeck(a.getDecks().getDeckByName("alireza"));
        b.getDecks().add(alireza);
        b.getDecks().setActiveDeck(b.getDecks().getDeckByName("alireza"));
        DuelMenu duelMenu = new DuelMenu(new Player(a), new Player(b));
//        duelMenu.addMagicCard("Mystical space typhoon");
//        duelMenu.addMonsterCardOpp("Axe Raider");
        duelMenu.process(" ");
    }

    public void addMonsterCardOpp(String cardName) {
        MonsterCard v = (MonsterCard) Card.getCardByName(cardName);
        Address hand = duelController.getDuel().duelAddresses.get(
                duelController.getDuel().duelZones.get(
                        "hand", duelController.getDuel().getOpponent())
                ,
                this.getDuelController().getZone(
                        duelController.getDuel().duelZones.get("hand", this.getDuelController().getDuel().getOpponent())).size());
        this.getDuelController().getDuel().getMap().put(hand,
                new MonsterCardHolder(this.getDuelController().getDuel().getOpponent(), v, CardState.NONE));
        duelController.select(hand);
        duelController.summon();
    }

    public void addMonsterCard(String cardName) {
        MonsterCard v = (MonsterCard) Card.getCardByName(cardName);
        Address hand = duelController.getDuel().duelAddresses.get(
                duelController.getDuel().duelZones.get(
                        "hand", duelController.getDuel().getCurrentPlayer())
                ,
                this.getDuelController().getZone(
                        duelController.getDuel().duelZones.get("hand", this.getDuelController().getDuel().getCurrentPlayer())).size());
        this.getDuelController().getDuel().getMap().put(hand,
                new MonsterCardHolder(this.getDuelController().getDuel().getCurrentPlayer(), v, CardState.NONE));
        duelController.select(hand);
        duelController.summon();
    }


    public void addMagicCard(String cardName) {
        MagicCard v = (MagicCard) Card.getCardByName(cardName);
        Address hand = duelController.getDuel().duelAddresses.get(
                duelController.getDuel().duelZones.get(
                        "hand", duelController.getDuel().getCurrentPlayer())
                ,
                this.getDuelController().getZone(
                        duelController.getDuel().duelZones.get("hand", this.getDuelController().getDuel().getCurrentPlayer())).size());
        this.getDuelController().getDuel().getMap().put(hand,
                new MagicCardHolder(this.getDuelController().getDuel().getCurrentPlayer(), v, CardState.NONE));
        duelController.select(hand);
        duelController.summon();
    }

}