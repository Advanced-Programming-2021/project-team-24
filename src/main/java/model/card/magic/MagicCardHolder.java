package model.card.magic;

import java.util.Map;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.effect.EffectManager;
import model.user.Player;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    private EffectManager effectManager;
    public MagicCardHolder(Player owner, MagicCard card, CardState cardState) {
        super(owner ,cardState);
        this.card = card;
        this.effectManager = new EffectManager(card.getEffect(), owner, super.idCounter);
    }        
    public EffectManager getEffectManager()
    {
        return this.effectManager;
    }
    
    public Card getCard() {
        return this.card;
    }

    public void flip() {
        this.cardState = CardState.VISIBLE_MAGIC;        
    }
    @Override
    public String toString() {
        String ans = "";
        ans.concat("Name :" + card.getName() + "\n");
        if(card.isSpell())
            ans.concat("Spell\n");
        else
            ans.concat("Trap\n");
        ans.concat("Type: " + (card.getCardType().toString()));
        ans.concat("Description:" + getCard().getDescription());
        return ans;    
    }
    
}