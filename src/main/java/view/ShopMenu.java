package view;

import controller.TypeMessage;
import model.user.User;

import java.util.regex.Matcher;

public class ShopMenu extends Menu {
    private controller.ShopMenu shopMenu = new controller.ShopMenu(user);
    private controller.MainMenu mainMenu = new controller.MainMenu(user);
    public ShopMenu(User user){
        super(user);
    }
    public void run(){
        while(true) {
            String command = Global.nextLine();
            if (Global.regexFind(command, "shop buy .*")) {
                Matcher matcher = Global.getMatcher(command, "(?<=shop buy ).*");
                if (matcher.find()) {
                    String cardName = matcher.group();
                    if(shopMenu.buyCard(cardName).getTypeMessage() != TypeMessage.SUCCESSFUL){
                        System.out.println(shopMenu.buyCard(cardName).getContent());
                    }
                }
            }
            else if (command.compareToIgnoreCase("shop show --all") == 0) System.out.println(shopMenu.getInfo().getContent());
            else if (command.equals("menu show-current")) {
                System.out.println("Shop Menu");
            }
            else if (checkMenuExit(command)) {
                exitMenu("Shop");
                return;
            }
        }
    }
}