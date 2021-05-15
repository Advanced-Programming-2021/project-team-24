package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.DuelController;
import model.card.CardType;
import model.card.Event;
import model.card.MagicCard;
import model.card.MagicCardHolder;
import model.user.Player;
import view.DuelMenu;
import view.Global;

public class EffectChainer {
    DuelController duelController;
    DuelMenu duelMenu;
    
    Event event;
    List<Integer> idMagicCardHolderChain;
    
    public EffectChainer(Event event, Player opponent)
    {    
        this.event = event;
        askForChain(opponent);
    }
    public EffectChainer(MagicCardHolder magicCardHolder, Player opponent)
    {
        idMagicCardHolderChain.add(magicCardHolder.getId());
        askForChain(opponent);
    }

    
    public void askForChain(Player currentChainer)
    {
        Filter filter = new Filter(currentChainer.getNickname());
        List<String> zones = new ArrayList<String>();
        zones.add("my_hand");
        zones.add("my_spell");
        filter.setZones(zones);
        filter.setCardType(CardType.TRAP);
        List<Integer> v = duelController.getDuel().getIdCardHolderFilter(filter);
        if(Global.delListFromList(v, idMagicCardHolderChain).size() > 0)
        {
            boolean ans = duelMenu.BooleanQYN("Do you want to chain another card?");
            if(ans == true)
            {
                while(true)
                {
                    int number = duelMenu.selective(v, 1, "select magic card to chain:").get(0);
                    MagicCardHolder temp = (MagicCardHolder)duelController.getDuel().getCardHolderById(number);
                    EffectParser effectParser = new EffectParser(duelMenu, duelController, temp.getOwner(), temp.getEffectManager(), temp.getId());
                    Boolean vv = Boolean.parseBoolean(effectParser.getCommandResult(temp.toString()));//check requirement
                    if(vv)
                    {   
                        idMagicCardHolderChain.add(number);                        
                    }
                }
            }
            else
                runChain();
        }
        
        
    }
    
    
    private void runChain()
    {
        //three part for speed
        HashMap<Integer, List<Integer>> speedMagic = new HashMap<Integer, List<Integer>>();
        for(int j = 1; j <= 3; j++)
        {
            speedMagic.put(j, new ArrayList<Integer>());
        }
        for(int i  = 0; i < idMagicCardHolderChain.size(); i++)
        {
            int number = idMagicCardHolderChain.get(i);  
            MagicCardHolder temp = (MagicCardHolder)duelController.getDuel().getCardHolderById(number);
            speedMagic.get(((MagicCard)temp.getCard()).getSpeed()).add(idMagicCardHolderChain.get(i));
        }   
        for(int j = 3; j >= 1; j--)
        {
            for(int i = 0; i < speedMagic.get(j).size(); i ++)
            {
                int number = speedMagic.get(j).get(i);
                MagicCardHolder temp = (MagicCardHolder)duelController.getDuel().getCardHolderById(number);
                EffectParser effectParser = new EffectParser(duelMenu, duelController, temp.getOwner(), temp.getEffectManager(), temp.getId());
                effectParser.runEffect();

            }
        }
    }
}
