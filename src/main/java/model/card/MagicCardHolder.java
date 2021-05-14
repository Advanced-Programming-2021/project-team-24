package model.card;

import model.effect.EffectManager;
import model.user.Player;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    private EffectManager effectManager;
    public MagicCardHolder(Player owner, MagicCard card, CardState cardState) {
        super(owner ,cardState);
        this.card = card;
        this.effectManager = new EffectManager(null, owner);
        //TODO Auto-generated constructor stub
    }    
    public EffectManager getEffectManager()
    {
        return this.effectManager;
    }
    protected void recalculateEffect() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endTurn() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void makeEmpty() {
        // TODO Auto-generated method stub
        
    }

    public Card getCard() {
        return this.card;
    }

    public void flip() {
        this.cardState = CardState.VISIBLE_MAGIC;        
    }
    
}
