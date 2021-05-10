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

    public int getId()
    {
        return this.id;
    }
    public boolean isEffectAgeEnded()
    {
        if(this.ageRemained <= 0)
            return true;
        else
            return false;        
    }    
    public EffectManager(Effect effect){
        idCounter++;
        this.id = idCounter;
        this.effect = effect;
        //TODO add this to duel effect manager list
    }
    
    public boolean satisfyRequired(){
        //TODO satisfy
        return true;
    }
    
}
