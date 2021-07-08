package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.card.CardType;
import model.card.monster.MonsterCardHolder;
import model.duel.Filter;

public class DefenceHandler extends FilterHandler {

    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        if(filter.getMinDefence() != null || filter.getMaxDefence() != null)
        {
            int flag = 0;
            try{
                MonsterCardHolder monster = (MonsterCardHolder)cardHolder;    
                if(filter.getMinDefence() != null)
                {
                    if(cardHolder.getCard().getCardType() == CardType.MONSTER)
                    {
                        if(monster.getDefence() < filter.getMinDefence())   
                            return false;
                    }
                    else
                        return false;            
                }
                if(filter.getMaxDefence() != null)
                {
                    if(cardHolder.getCard().getCardType() == CardType.MONSTER)
                    {
                        if(monster.getDefence() > filter.getMaxDefence())
                            return false;
                    }
                }
            }catch(Exception e){
                flag = 1;
            }
            if(flag == 1)
                return false;
        }
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
