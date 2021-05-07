package model.effect;

import model.user.Player;

import java.util.List;

public class EffectManager {
    private Player owner;
    private int id;
    private static int idCounter = 0;
    private int idCardHolder;
    private boolean isActivated;
    private int ageRemained;
    private int remainedToApplyEffect;
    private Effect effect;
    public EffectManager(Effect effect, Player owner){
        idCounter++;
        this.owner = owner;
        this.id = idCounter;
        this.effect = effect;
        //TODO set age remain by Effect                
    }
    public Integer getId()
    {
        return this.id;
    }
    public void endPhase()
    {
        this.ageRemained--;
    }
    public int getOwnerCardHolderId()
    {
        return this.idCardHolder;
    }
    public boolean satisfyRequired(){
        return true;
    }
    
}
