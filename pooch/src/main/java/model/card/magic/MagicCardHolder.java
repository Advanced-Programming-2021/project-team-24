package model.card.magic;

import java.util.Map;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.effect.EffectManager;
import model.user.Player;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    private transient EffectManager effectManager;
    public MagicCardHolder(Player owner, MagicCard card, CardState cardState) {
        super(owner ,cardState);
        this.card = card;
        this.effectManager = new EffectManager(card.getEffect().clone(), owner, getId());
    }
    public EffectManager getEffectManager()
    {
        return this.effectManager;
    }

    public void setCardState(CardState cardState)
    {
        this.cardState = cardState;
    }
    public Card getCard() {
        return this.card;
    }

    public void flip() {
        this.cardState = CardState.VISIBLE_MAGIC;
    }
    public void activate()
    {
        this.cardState = CardState.ACTIVE_MAGIC;
    }
    @Override
    public String toString() {
        return card.toString();
    }

}
