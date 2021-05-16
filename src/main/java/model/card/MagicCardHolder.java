package model.card;

import java.util.Map;

import model.effect.EffectManager;
import model.user.Player;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    private EffectManager effectManager;
    public MagicCardHolder(Player owner, MagicCard card, CardState cardState) {
        super(owner ,cardState);
        this.card = card;
        this.onDeath = new EffectManager(card.onDeath, owner);
        this.effectManager = new EffectManager(null, owner);
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
    
}