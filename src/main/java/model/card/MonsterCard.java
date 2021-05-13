package model.card;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MonsterCard extends Card {
    @SerializedName("attack")
    private int attack;
    @SerializedName("defence")
    private int defence; 
    @SerializedName("level")
    private int level;
    private MonsterEffectType monsterEffectType;
    private MonsterType monsterType;
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
        
        System.out.println( new Gson().toJson(v));

    }
}