package view;


import controller.DuelController;
import controller.MainMenuController;
import controller.Message;
import model.card.Card;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.magic.MagicIcon;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.card.monster.MonsterType;
import model.deck.Deck;
import model.duel.Duel;
import model.duel.EffectParser;
import model.duel.Filter;
import model.effect.Effect;
import model.effect.EffectType;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.thoughtworks.qdox.model.expression.Add;

import org.apache.maven.shared.utils.cli.shell.CommandShell;

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
        DuelMenu duelMenu = new DuelMenu(a, b);
        DuelController dControleer = duelMenu.duelController;
        dControleer.select(Address.get(Zone.get("hand", dControleer.getDuel().getCurrentPlayer()), 0));
        dControleer.nextPhase();
        dControleer.nextPhase();
        MagicCard magic = new MagicCard();
        Effect effect = new Effect("");
        effect.setEffectType(EffectType.CONTINUES);
        effect.setReverse("");
        effect.setSpeed(1);
        effect.setEffect("changeLP(own,1000);");
        magic.setEffect(effect);
        magic.setMagicIcon(MagicIcon.CONTINUOUS);
        
        System.out.println(EffectParser.splitByParentheses("alirez(alir)").get(0));
        dControleer.getDuel().getMap().put(Address.get(Zone.get("magic", dControleer.getDuel().getCurrentPlayer()), 0), new MagicCardHolder(dControleer.getDuel().getCurrentPlayer(), magic, CardState.SET_MAGIC));
        dControleer.getDuel().getMap().get(Address.get(Zone.get("magic", dControleer.getDuel().getCurrentPlayer()), 0));
        if(!dControleer.getDuel().getMap().get(dControleer.getSelectedAddress()).getCard().isMagic())
        {
            dControleer.summon();
            dControleer.nextPhase();
            System.out.println(dControleer.directAttack().getContent());
        }
        MonsterCard current = (MonsterCard) Card.getCardByName("Beast King Barbaros");

        String setSummon = "";
        String summon = "";
        try {
            summon = new String(Files.readAllBytes(Paths.get(new File("summon.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");

            setSummon = new String(Files.readAllBytes(Paths.get(new File("set.txt").getPath()))).replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll(" ", "");
        } catch (Exception e) {
            //TODO: handle exception
        }
        if(setSummon.length() > 0)
            current.getEffects().put(Event.SET_OWNER, setSummon);
        if(summon.length() > 0)
            current.getEffects().put(Event.SUMMON_OWNER, summon);
        current.updateCard();
        String command = "message(ridi!!)";

        Duel duel = dControleer.getDuel();
        System.out.println(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5));
        dControleer.getDuel().getMap().put(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5), new MonsterCardHolder(duel.getCurrentPlayer(), current, CardState.HAND));
        dControleer.select(Address.get(Zone.get("hand", duel.getCurrentPlayer()), 5));

        dControleer.summon();
        
        System.out.println(new EffectParser(duelMenu, dControleer, ((MagicCardHolder)dControleer.getDuel().getMap().get(Address.get(Zone.get("magic", dControleer.getDuel().getCurrentPlayer()), 0))).getEffectManager()).getCommandResult(command));
        System.out.println(dControleer.getDuel().getCurrentPlayer().getLifePoint());
        System.out.println(dControleer.getDuel().getOpponent().getLifePoint());

        //duelMenu.run();
        //Summon/Set owner: "q_yn(tribitute three cards and remove opponent monster?)(changeZone(select($my_monster$,3,select_for_tribute), $my_graveyard$);set(this,temp,$my_monster$);changeZone(this,my_monster,SET); set(del($my_monster$,get(this,temp),attack,2900);   )   )else()"
        //duelMenu.duelController.select(Address.get(Zone.get("hand", dControleer.getDuel().getCurrentPlayer()), 1));
        //System.out.println(dControleer.set().getContent());
        
    }
}
