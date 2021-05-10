package model.card;

public class MonsterCard extends Card {
    private int attack;
    private int defence; 
    private int level;
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
}