package controller.process;

import controller.Message;
import controller.TypeMessage;
import controller.server.GsonConverter;
import model.Response;
import model.Situation;
import model.card.Card;
import model.user.User;

import java.util.regex.Matcher;

public class ShopMenu extends Menu {
    private controller.ShopMenu shopMenu = new controller.ShopMenu(user);
    public ShopMenu(User user){
        super(user);
    }
    public synchronized Response process(String command){
        if (Global.regexFind(command, "shop buy .*")) {
            Matcher matcher = Global.getMatcher(command, "(?<=shop buy ).*");
            if (matcher.find()) {
                String cardName = matcher.group();
                Message message = shopMenu.buyCard(cardName);
                if(message.getTypeMessage() == TypeMessage.SUCCESSFUL){
                    return new Response(message, Situation.SHOP);
                }
            }
        }
        else if (command.compareToIgnoreCase("shop show --all") == 0) return new Response(shopMenu.getInfo(), Situation.SHOP);
        else if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "Shop Menu"), Situation.SHOP);
        }
        else if (command.equals("getAllCards")){
            return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(Card.getAllCards())), Situation.SHOP);
        }
        else if (command.equals("getUser")){
            return new Response(new Message(TypeMessage.INFO, GsonConverter.serialize(user)), Situation.SHOP);
        }
        else if (checkMenuExit(command)) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.MAIN);
        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.SHOP);
    }
}