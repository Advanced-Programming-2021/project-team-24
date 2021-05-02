package model.user;


import model.card.CardHolder;
import model.duel.zone.DrawDeckZone;
import model.duel.zone.FieldZone;
import model.duel.zone.Graveyard;
import model.duel.zone.MagicCardsZone;
import model.duel.zone.MonsterZone;
import model.duel.zone.Zone;

public class Player {
    private User user;
    private int lifePoint;
    private CardHolder selectedCardHolder;    
    private Graveyard graveyard;
    private MonsterZone monsterZone;
    private MagicCardsZone magicCardsZone;
    private DrawDeckZone drawDeckZone;
    private FieldZone fieldZone;
    public Player(User user)
    {
        this.lifePoint = 8000;
        this.user = user;    
        this.fieldZone = new FieldZone();
        this.drawDeckZone = new DrawDeckZone(user.getActiveDeck());    
        this.graveyard = new Graveyard();
        this.magicCardsZone = new MagicCardsZone();
        this.monsterZone = new MonsterZone();
    }
    
    public void setSelectedCardHolder(CardHolder cardHolder)
    {
        this.selectedCardHolder = cardHolder;
    }


    
    public String getNickname()
    {
        return user.getNickname();
    }    
    public void changeLifePoint(int change)
    {
        this.lifePoint += change;
    }
    public boolean isDead()
    {
        if(lifePoint <= 0)
            return true;
        else
            return false;
    }

}
