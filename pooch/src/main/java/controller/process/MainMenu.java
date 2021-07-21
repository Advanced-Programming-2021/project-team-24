package controller.process;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import controller.DuelController;
import controller.MainMenuController;
import controller.Message;
import controller.TypeMessage;
import model.Response;
import model.Situation;
import model.card.Card;
import model.card.Event;
import model.card.magic.MagicCard;
import model.deck.Deck;
import model.duel.Duel;
import model.duel.EffectParser;
import model.duel.Filter;
import model.effect.Effect;
import model.user.Player;
import model.user.User;

public class MainMenu extends Menu {
    controller.MainMenuController mainMenuController;

    public MainMenu(User user) {
        super(user);
        this.mainMenuController = new MainMenuController();
    }

    public synchronized Response process(String command) {
        Respone respone = ChatServer.handle(command);
        if (respone != null) return respone;
        if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Main Menu"), Situation.MAIN);
        }
        else if(command.equals("user logout")){
            return new Response(new Message(TypeMessage.SUCCESSFUL, "user logged out successfully!"), Situation.LOGIN);
        }
        else if(checkEnterMenu(command)) {
            return enterMenu("Main", command );
        }
        else {
            Matcher matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--second-player (?<opponentUsername>\\w+)))(?=.*(?:--rounds (?<rounds>\\d)))");
            if (matcher.find()) {
                String opponentUsername = matcher.group("opponentUsername");
                String rounds = matcher.group("rounds");
                Message message = mainMenuController.createDuel(user, User.readUser(opponentUsername), rounds);
                return new Response(message, Situation.LOGIN);
            }
            matcher = Global.getMatcher(command, "duel (?=.*(?:--new))(?=.*(?:--ai))(?=.*(?:--rounds))(?=.*(?:--rounds (?<rounds>\\d)))");
            if (matcher.find()) {
                String rounds = matcher.group("rounds");
                //TODO ai
            }
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.MAIN);
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
        DuelController dControleer = duelMenu.duelController;
        dControleer.select(duelMenu.duelController.getDuel().duelAddresses.get(duelMenu.duelController.getDuel().duelZones.get("hand", dControleer.getDuel().getCurrentPlayer()), 0));
        dControleer.nextPhase();
        dControleer.nextPhase();
        MagicCard current = (MagicCard) Card.getCardByName("Time Seal");
        
        System.out.println(EffectParser.splitByParentheses("alirez(alir)").get(0));
       
        dControleer.nextPhase();
        dControleer.nextPhase();
        
        
        Effect effect = new Effect("");
        
        String mainEffect = "";
        String reverse = "";
        String required = "";
        String setSummon = "";
        String summon = "";        
        String onFlipOwner = "";
        Effect checkForDeath = new Effect("");
        String any = "";

        
        System.out.println();
        //u.add(Event.SUMMON);
        //effect.setRequiredEvent(u);
        //effect.setAskForActivation(false);
        try {
            //any = new String(Files.readAllBytes(Paths.get(new File("effect.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
            //summon = new String(Files.readAllBytes(Paths.get(new File("summon6.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
            mainEffect = new String(Files.readAllBytes(Paths.get(new File("effect.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
            //required = new String(Files.readAllBytes(Paths.get(new File("required.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
            //reverse = new String(Files.readAllBytes(Paths.get(new File("reverse.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");

            //onFlipOwner = new String(Files.readAllBytes(Paths.get(new File("flip.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
            //setSummon = new String(Files.readAllBytes(Paths.get(new File("set6.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
        } catch (Exception e) {
            //TODO: handle exception
        }
        //current.getEffects().put(Event.ANY, any);
        //current.getEffects().put(Event.SUMMON_OWNER, summon);
        //current.getEffects().put(Event.SET_OWNER, setSummon);
        effect.setEffect(mainEffect);
        effect.setReverse(reverse);
        effect.setRequirementString(required);
        List<Event> req = new ArrayList<>();
        //req.add(Event.ANY);
        effect.setRequiredEvent(req);
        current.setEffect(effect);
        //current.getEffects().put(Event.ANY, any);
        //current.getEffects().put(Event.ACTIVE_SPELL, any);
        //current.getEffects().put(Event.ANY, any);
        if(setSummon.length() > 0)
            current.getEffects().put(Event.SET_OWNER, setSummon);
        if(summon.length() > 0)
            current.getEffects().put(Event.SUMMON_OWNER, summon);        
        if(onFlipOwner.length() > 0)
        {
            //current.getEffects().put(Event.FLIP_OWNER, onFlipOwner);
        }

        //String eff = "if(#get(this,activated)#>##)(if(#Norm(Filter(Id:get(this,equipped)))#<#1#)(changeZone(this,my_graveryard))())()";
        //current.getEffects().put(Event.ANY, eff);
        current.updateCard();        
        String command = "message(ridi!!)";
        String comman = "filter(\"idCardHolder\":$hand$)";
        Duel duel = dControleer.getDuel();
        dControleer.setDuelMenu(duelMenu);
        //dControleer.getDuel().getMap().put(Address.get(Zone.get("monster",dControleer.getDuel().getCurrentPlayer()), 1), new MonsterCardHolder(dControleer.getDuel().getCurrentPlayer(), current, CardState.ATTACK_MONSTER));
        //dControleer.getDuel().getMap().put(Address.get(Zone.get("monster",dControleer.getDuel().getCurrentPlayer()), 2), new MonsterCardHolder(dControleer.getDuel().getCurrentPlayer(), current, CardState.ATTACK_MONSTER));
        duelMenu.process("");
        /*System.out.println(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5));

        dControleer.getDuel().getMap().put(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5), new MonsterCardHolder(duel.getCurrentPlayer(), current, CardState.HAND));
        dControleer.getDuel().getMap().put(Address.get(Zone.get("monster", duel.getCurrentPlayer()), 3), new MonsterCardHolder(duel.getCurrentPlayer(), current, CardState.SET_DEFENCE));
        dControleer.getDuel().getMap().put(Address.get(Zone.get("monster", duel.getCurrentPlayer()), 4), new MonsterCardHolder(duel.getCurrentPlayer(), current, CardState.SET_DEFENCE));
        dControleer.getDuel().getMap().put(Address.get(Zone.get("monster", duel.getCurrentPlayer()), 2), new MonsterCardHolder(duel.getCurrentPlayer(), current, CardState.SET_DEFENCE));
        dControleer.select(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5));

        dControleer.set();
        dControleer.select(Address.get(Zone.get("monster", duel.getCurrentPlayer()), 4));
        dControleer.flipSummon();
        */
        System.out.println(dControleer.getDuel().getCurrentPlayer().getLifePoint());
        Filter uu = new Filter();
        List<String> x = new ArrayList<>();
        x.add("1");
        uu.setIdCardHolder(x);
        System.out.println(new Gson().toJson(uu));
        System.out.println(dControleer.getDuel().getOpponent().getLifePoint());        
        //duelMenu.run();
        
        //Summon/Set owner: "q_yn(tribitute three cards and remove opponent monster?)(changeZone(select($my_monster$,3,select_for_tribute), $my_graveyard$);set(this,temp,$my_monster$);changeZone(this,my_monster,SET); set(del($my_monster$,get(this,temp),attack,2900);   )   )else()"
        //duelMenu.duelController.select(Address.get(Zone.get("hand", dControleer.getDuel().getCurrentPlayer()), 1));
        //System.out.println(dControleer.set().getContent());
        
    }
}
