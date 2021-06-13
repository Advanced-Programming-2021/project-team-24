
package model.card;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.card.magic.LimitType;
import model.card.magic.MagicCard;
import model.card.monster.MonsterCard;
import model.effect.Effect;
import model.user.User;
//import org.checkerframework.checker.units.qual.C;


public abstract class Card {
    protected String name;
    protected String description;
    protected Integer price;
    protected LimitType limitType;
    protected CardType cardType;
    protected Effect onDeath;


    static List <Card> allCards = new ArrayList<>();
    public static List <Card> getAllCards()
    {
        return allCards;
    }
    public static void intialize()
    {
        allCards = new ArrayList<>();
        try {
            //"" means project directory
            File directoryPathMonsterCard = new File("Cards/MonsterCards");
            File[] filesListMonsterCard = directoryPathMonsterCard.listFiles();
            File directoryPathMagicCard = new File("Cards/MagicCards");
            File[] filesListMagicCard = directoryPathMagicCard.listFiles();
            assert filesListMonsterCard != null;
            assert filesListMagicCard != null;
            for(File file : filesListMonsterCard) {
                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                MonsterCard card = new Gson().fromJson(json,MonsterCard.class);
                allCards.add(card);
            }
            for(File file : filesListMagicCard) {
                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                MagicCard card = new Gson().fromJson(json,MagicCard.class);
                allCards.add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Card getCardByName(String cardName)
    {
        for(int i = 0; i < allCards.size(); i++)
        {
            if(allCards.get(i).getName().compareTo(cardName) == 0)
                return allCards.get(i);
        }
        return null;
    }
    public CardType getCardType()
    {
        return this.cardType;
    }
    public String getName()
    {
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
    public LimitType getLimitType()
    {
        return this.limitType;
    }
    public String getDescription()
    {
        return this.description;
    }
    public boolean isMagic()
    {
        if(this.cardType == CardType.MONSTER) return false;
        else
            return true;
    }
    public static String toEnumsFormatString(String string){
        return string.toUpperCase().replace('-', '_').replace(' ', '_');
    }
    public BufferedReader getCsvReader(String pathToCsv) throws FileNotFoundException {
        return new BufferedReader(new FileReader(pathToCsv));
    }
    public void writeJson (String path) throws IOException {
        File file = new File(path + this.name + ".json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(new Gson().toJson(this));
        fileWriter.close();
    }
    //TODO
}