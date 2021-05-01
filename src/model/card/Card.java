package model.card;

public abstract class Card {
    private String name;
    private String description;
    private Integer score;
    private LimitType limitType;    
    public String getDescription;
    public String getName()
    {
        return this.name;
    }
    public LimitType getLimitType()
    {
        return this.limitType;
    }
    public String getDescription()
    {
        return this.description;
    }
    public int getScore()
    {
        return this.score;
    }
    //TODO




}
