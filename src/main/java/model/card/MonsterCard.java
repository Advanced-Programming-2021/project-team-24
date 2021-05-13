package model.card;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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
    public static void main(String[] args) {
        MonsterCard v = new MonsterCard();
        v.attack = 100;
        v.defence = 100;
        v.level = 10;
        v.price = 10;
        v.limitType = LimitType.LIMITED;
        v.cardType = CardType.MONSTER;
        v.name = "alireza";
        v.description = " alireza";        
        v.monsterEffectType = MonsterEffectType.EFFECT;
        v.monsterType = MonsterType.AQUA;
        v.description = "alireza raft khoneh";
        v.monsterAttribute = MonsterAttribute.DARK;
        //v.effects = new HashMap<Event, String>();
        //v.effects.put(Event.ATTACK , " nothing");
        System.out.println( new Gson().toJson(v));
        MonsterCard card = new Gson().fromJson(new Gson().toJson(v), MonsterCard.class);
    }
}