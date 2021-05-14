package model.card;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        v.attack = 1000;
        v.defence = 1000;
        v.level = 4;
        v.price = 2100;
        v.limitType = LimitType.LIMITED;
        v.cardType = CardType.MONSTER;
        v.name = "Command Knight";
        v.description = "All Warrior-Type monsters you control gain 400 ATK. If you control another monster, monsters your opponent controls cannot target this card for an attack.";
        v.monsterEffectType = MonsterEffectType.EFFECT;
        v.monsterType = MonsterType.WARRIOR;
        v.monsterAttribute = MonsterAttribute.FIRE;
        // v.effects = new HashMap<Event, String>();
        // v.effects.put(Event.ATTACK , " nothing");
        System.out.println( new Gson().toJson(v));
        MonsterCard card = new Gson().fromJson(new Gson().toJson(v), MonsterCard.class);
        File file = new File("Cards/MonsterCards/" + v.name + ".json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(new Gson().toJson(v));
        fileWriter.close();
    }
}