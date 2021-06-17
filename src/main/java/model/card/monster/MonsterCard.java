package model.card.monster;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import model.card.Card;
import model.card.CardType;
import model.card.Event;
import model.card.magic.LimitType;

import java.io.*;
import java.util.HashMap;

public class MonsterCard extends Card {
    @SerializedName("attack")
    private int attack;
    @SerializedName("defence")
    private int defence; 
    @SerializedName("level")
    private int level;
    @SerializedName("monsterEffectType")
    private MonsterEffectType monsterEffectType;
    @SerializedName("monsterType")
    private MonsterType monsterType;
    @SerializedName("effects")
    private HashMap<Event, String> effects;
    @SerializedName("monsterAttribute:")
    private MonsterAttribute monsterAttribute;
    public int getLevel()
    {
        return level;
    }  
    public int getAttack()
    {
        return this.attack;
    }        
    public int getDefence()
    {
        return this.defence;
    }
    public static void main(String[] args) throws IOException {
        MonsterCard v = new MonsterCard();
        BufferedReader csvReader = v.getCsvReader("Monster.csv");
        String row;
        csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",(?! )");
            v.name = data[0].replace("\"", "");
            v.level = Integer.parseInt(data[1]);
            v.monsterAttribute = MonsterAttribute.valueOf(data[2]);
            v.monsterType = MonsterType.valueOf(toEnumsFormatString(data[3]));
            v.monsterEffectType = MonsterEffectType.valueOf(toEnumsFormatString(data[4]));
            v.attack = Integer.parseInt(data[5]);
            v.defence = Integer.parseInt(data[6]);
            v.description = data[7];
            v.price = Integer.parseInt(data[8]);
            v.limitType = LimitType.UNLIMITED;
            v.cardType = CardType.MONSTER;
            // v.effects = new HashMap<Event, String>();
            // v.effects.put(Event.ATTACK , " nothing");
            v.writeJson("Cards/MonsterCards/");
            MonsterCard card = new Gson().fromJson(new Gson().toJson(v), MonsterCard.class);
        }
        csvReader.close();
    }
}