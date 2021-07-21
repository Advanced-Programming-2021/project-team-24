
package model.card;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import model.card.magic.LimitType;
import model.card.magic.MagicCard;
import model.card.magic.MagicIcon;
import model.card.monster.MonsterCard;
import model.effect.EffectType;



public abstract class Card {
    protected String name;
    protected String description;
    protected Integer price;
    protected LimitType limitType;
    protected CardType cardType;
    public int count;
    public boolean isBanned;
    @SerializedName("effects")
    protected HashMap<Event, String> effects;

    static List <Card> allCards = new ArrayList<>();
    public abstract String toString();
    public static List <Card> getAllCards()
    {
        return allCards;
    }
    public static List <Card> getMonsterCards(){
        List<Card> cards = new ArrayList<>();
        for(Card card : allCards){
            if(!card.isMagic()) cards.add(card);
        }
        return cards;
    }
    static
    {
        HashMap<MagicIcon, EffectType> v = new HashMap<>();
        v.put(MagicIcon.CONTINUOUS, EffectType.CONTINUES);
        v.put(MagicIcon.EQUIP, EffectType.EQUIP);
        v.put(MagicIcon.FIELD, EffectType.FIELD);
        v.put(MagicIcon.NORMAL, EffectType.NORMAL);
        v.put(MagicIcon.RITUAL, EffectType.RITUAL);
        v.put(MagicIcon.QUICK_PLAY, EffectType.QUICK_PLAY);
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
                if(card.getMagicIcon() == MagicIcon.FIELD)
                {
                    int b = 1;
                }
                card.getEffect().setEffectType(v.get(card.getMagicIcon()));
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
    public void updateCard()
    {
        if(this.isMagic())
        {
            try {
                FileWriter v = new FileWriter("Cards/MagicCards/" + getName() + ".json");
                v.write(new Gson().toJson((MagicCard)this));
                v.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            try {
                FileWriter v = new FileWriter("Cards/MonsterCards/" + getName() + ".json");
                v.write(new Gson().toJson((MonsterCard)this));
                v.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public HashMap<Event,String> getEffects()
    {
        if(effects == null)
        {
            effects = new HashMap<Event,String>();
        }
        return this.effects;
    }
}