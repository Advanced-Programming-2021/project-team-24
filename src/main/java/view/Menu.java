package view;


import model.card.CardState;
import model.duel.Filter;
import model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

public class Menu {
    protected User user;
    public Menu(User user){
        this.user = user;
    }
    public static void main(String[] args) {
        Filter s = new Filter();
        List<CardState> j = new ArrayList<CardState>();
        j.add(CardState.ATTACK_MONSTER);
        s.setCardStates(j);
        System.out.println(new Gson().toJson(s));
    }
    public boolean checkMenuExit(String command){
        if(command.compareToIgnoreCase("menu exit") == 0) return true;
        return false;
    }
    public void exitMenu(String currentMenu) {
        if (currentMenu.equals("Duel") || currentMenu.equals("Deck") ||
                currentMenu.equals("Scoreboard") || currentMenu.equals("Profile") ||
                currentMenu.equals("Shop") || currentMenu.equals("Import/Export")) {
            new MainMenu(user).run();
        } else if (currentMenu.equals("Main")) {
            new LoginMenu().run();
        }
    }
    public boolean checkEnterMenu(String command){
        Matcher matcher = Global.getMatcher(command, "(?<=menu enter )\\w+");
        if(matcher.find()){
            String menu = matcher.group();
            if (menu.equals("Duel") || menu.equals("Deck") ||
                    menu.equals("Scoreboard") || menu.equals("Profile") ||
                    menu.equals("Shop") || menu.equals("ImportExport")||
                    menu.equals("Main") || menu.equals("Login")){
                return true;
            }
        }
        return false;
    }
    public void enterMenu(String currentMenu, String command){
        if (currentMenu.equals("Duel") || currentMenu.equals("Deck") ||
                currentMenu.equals("Scoreboard") || currentMenu.equals("Profile") ||
                currentMenu.equals("Shop") || currentMenu.equals("Import/Export")){
            System.out.println("menu navigation is not possible");
        }
        else if (currentMenu.equals("Main")){
            Matcher matcher = Global.getMatcher(command, "(?<=menu enter )\\w+");
            if (matcher.find()){
                String menu = matcher.group();
                switch (menu){
                    case "Deck":
                        new DeckMenu(user).run();
                    case "Scoreboard":
                        new ScoreboardMenu(user).run();
                    case "Profile":
                        new ProfileMenu(user).run();
                    case "Shop":
                        new ShopMenu(user).run();
                    case "ImportExport":
                        new ImportExportMenu(user).run();
                }
            }
        }
    }
}