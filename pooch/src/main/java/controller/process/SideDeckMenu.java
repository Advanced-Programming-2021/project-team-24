package controller.process;

import controller.Message;
import controller.SideDeckController;
import controller.TypeMessage;
import model.Response;
import model.Situation;
import model.user.User;

import java.util.regex.Matcher;

public class SideDeckMenu extends Menu{
    SideDeckController sideDeckController;

    public SideDeckMenu(User user) {
        super(user);
        this.sideDeckController = new SideDeckController(user.getDecks());
    }
    public synchronized Response process(String command) {
        if (command.equals("menu exit")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.DUEL);

        }
        Matcher matcher = Global.getMatcher(command, "deck change-cards (?=.*(?:--main card (?<mainCardName>\\w+)))(?=.*(?:--side card (?<sideCardName>\\w+)))");
        if (matcher.find()) {
            String mainCardName = matcher.group("mainCardName");
            String sideCardName = matcher.group("sideCardName");
            Message message = sideDeckController.change(mainCardName, sideCardName);
            return new Response(message, Situation.SIDEDECK);
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.SIDEDECK);

    }
}

