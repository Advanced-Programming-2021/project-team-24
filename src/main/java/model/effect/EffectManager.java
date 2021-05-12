package model.effect;

import model.user.Player;

import java.util.List;

public class EffectManager {
    private Player owner;
    private int id;
    private static int idCounter = 0;
    private int idCardHolder;
    private Boolean isActivated;
    private int ageRemained;
    private int remainedToApplyEffect;
    private Effect effect;
    public EffectManager(Effect effect, Player owner){
        idCounter++;
        isActivated = false;
        this.owner = owner;
        this.id = idCounter;
        this.effect = effect;
        //TODO set age remain by Effect                
    }
    public void setActivated()
    {
        this.isActivated = true;
    }
    
    public Boolean getActivated()
    {
        return this.isActivated;
    }
    
    public Effect getEffect()
    {
        return this.effect;
    }
    public Integer getId()
    {
        return this.id;
    }
    public int getOwnerCardHolderId()
    {
        return this.idCardHolder;
    }
    
}