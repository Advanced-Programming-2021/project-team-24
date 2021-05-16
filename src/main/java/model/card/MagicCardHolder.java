package model.card;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    public MagicCardHolder(MagicCard card, CardState cardState) {
        super(cardState);
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
        // TODO Auto-generated method stub
        
    }
    
}
