package model.duel.filterhandle;

import model.duel.Duel;
import model.card.CardHolder;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.MonsterCardHolder;
import model.duel.Filter;

public class LevelHandler extends FilterHandler {
    public boolean Handle(Filter filter, CardHolder cardHolder, Duel duel) {
        
        if(filter.getMinLevel() != null || filter.getMaxLevel() != null)
        {
            if(cardHolder.getCard().getCardType() != CardType.MONSTER)
            {
                return false;
            }
            MonsterCardHolder monster = (MonsterCardHolder) cardHolder;
            MonsterCard mm = (MonsterCard)monster.getCard();
            int level = mm.getLevel();
            if(filter.getMinLevel() != null && filter.getMinLevel() > level)
            {
                return false;
            }
            if(filter.getMaxLevel() != null && filter.getMaxLevel() < level)
            {
                return false;
            }
        }        
        if(nextFilterHandler != null)
        {
            return nextFilterHandler.Handle(filter, cardHolder, duel);
        }
        return true;
    }
    
}
