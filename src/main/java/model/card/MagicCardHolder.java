package model.card;

public class MagicCardHolder extends CardHolder {
    private MagicCard card;
    public MagicCardHolder(MagicCard card, CardState cardState) {
        super(cardState);
        this.card = card;
        //TODO Auto-generated constructor stub
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
        // TODO Auto-generated method stub
        
    }
    
}
