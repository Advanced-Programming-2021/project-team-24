package model.duel;

import java.util.List;
import java.util.Map;

import controller.DuelController;
import model.card.CardHolder;
import model.card.Event;
import model.effect.EffectManager;
import view.DuelMenu;

public class AutomaticEffectHandler {
    DuelController duelController;
    DuelMenu duelMenu;

    public AutomaticEffectHandler(DuelController duelController, DuelMenu duelMenu)
    {
        this.duelController = duelController;
        this.duelMenu = duelMenu;
        
    }
    public void update()
    {
        for(int i = 0; i < duelController.getDuel().getAllCardHolder().size(); i++)
        {            
            CardHolder temp = duelController.getDuel().getAllCardHolder().get(i);
            if(!duelController.getDuel().getCardHolderZone(temp).getName().equals("deck") && !duelController.getDuel().getCardHolderZone(temp).getName().equals("hand"))
            for(Map.Entry<Event, List<EffectManager>> mapEntry : temp.getEffects().entrySet())
            {
                List<EffectManager> currenList = ((List<EffectManager>) mapEntry.getValue());
                if(currenList != null && mapEntry.getKey() == Event.ANY)
                    for(int q = 0; q < currenList.size(); q++)
                    {
                        if(currenList.get(q).getEffect().getAskForActivation() == false)
                        {
                            if(currenList.get(q).isConditionSatisfied(new EffectParser(duelMenu, duelController, currenList.get(q))))
                            {
                                new EffectParser(duelMenu, duelController, currenList.get(q)).runEffect();
                            }
                        }
                    }
            }
        }
    }
}
