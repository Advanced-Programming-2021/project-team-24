package model.effect;

import model.card.Event;
import model.duel.EffectParser;
import model.user.Player;


import java.util.*;

public class EffectManager {
    private Player owner;
    private int id;
    private static int idCounter = 0;
    private int idCardHolder;
    private Boolean isActivated;    
    private HashMap<String, String> extraKeyWords;
    private Effect effect;
    public EffectManager(Effect effect, Player owner, Integer idCardHolder){
        idCounter++;
        isActivated = false;
        this.owner = owner;
        this.id = idCounter;
        this.effect = effect;
        this.idCardHolder = idCardHolder;
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

    public Boolean isConditionSatisfied(EffectParser effectParser)
    {
        if(this.effect.getRequireEvents() == null || this.effect.getRequireEvents().size() == 0)
        {
            if(effectParser.getDuelController().getDuel().getCardHolderById(this.idCardHolder).getBoolMapValue("can_active"))
            {
                boolean flag = false;
                for(int i = 0; i < this.effect.getRequireEvents().size(); i++)
                {
                    if(effect.getRequireEvents().get(i) == Event.ANY)
                    {
                        flag = true;
                        break;
                    }
                    if(effectParser.getDuelController().getDuelEvents().get(effect.getRequireEvents().get(i)) != null)
                    {
                        Event temp_event = effect.getRequireEvents().get(i);
                        String a[] = temp_event.getValue().split("_");
                        if(a.length == 1)
                        {
                            flag = true;
                            break;
                        }
                        if(a.length == 2)
                        {
                            if(this.idCardHolder == effectParser.getDuelController().getDuelEvents().get(effect.getRequireEvents().get(i)))
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