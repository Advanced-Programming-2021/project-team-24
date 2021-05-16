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
    public Player getOwner()
    {
        return this.owner;
    }
    public HashMap<String,String> getExtraKeyWords()
    {
        return this.extraKeyWords;
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

    public Boolean isConditionSatified(EffectParser effectParser)
    {
        if(this.effect.getRequirEvents() == null || this.effect.getRequirEvents().size() == 0)
        {
            if(effectParser.getDuelController().getDuel().getCardHolderById(this.idCardHolder).getBoolMapValue("can_active"))
            {
                boolean flag = false;
                for(int i = 0; i < this.effect.getRequirEvents().size(); i++)
                {
                    if(effect.getRequirEvents().get(i) == Event.ANY)
                    {
                        flag = true;
                        break;
                    }
                    if(effectParser.getDuelController().getDuelEvents().get(effect.getRequirEvents().get(i)) != null)
                    {
                        Event temp_event = effect.getRequirEvents().get(i);
                        String a[] = temp_event.getValue().split("_");
                        if(a.length == 1)
                        {
                            flag = true;
                            break;
                        }
                        if(a.length == 2)
                        {
                            if(this.idCardHolder == effectParser.getDuelController().getDuelEvents().get(effect.getRequirEvents().get(i)))
                            {
                                flag = true;
                                break;
                            }
                        }                   
                    }
                }
                if(flag)
                {
                    return Boolean.parseBoolean(effectParser.getCommandResult(effect.getRequirementCommandString()));
                }
                else
                {
                    return false;
                }            
            }
        }   
        return false;
        
    }
}