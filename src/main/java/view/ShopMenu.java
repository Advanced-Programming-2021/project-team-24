package view;

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
                    System.out.printf(shopMenu.buyCard(cardName).getContent());
                }
            }
            else if (command.compareToIgnoreCase("shop show --all") == 0) System.out.printf(shopMenu.getInfo().getContent());
            checkShowCurrentMenu(command);
            if(checkMenuExit(command)) {
                mainMenu.menuExit();
                return;
            }
        }
    }
}