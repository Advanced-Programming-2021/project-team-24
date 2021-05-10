<<<<<<< HEAD
=======
<<<<<<<< HEAD:src/main/java/model/effect/EffectManager.java
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
    public int getOwnerCardHolderId()
    {
        return this.idCardHolder;
    }
    public boolean satisfyRequired(){
        return true;
    }
    
}
========
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
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
<<<<<<< HEAD
=======
>>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f:src/model/effect/EffectManager.java
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
