package model.card;

public class MonsterCardHolder extends CardHolder {
    
    public MonsterCardHolder(Card card, CardState cardState , User owner)
    {
        this.owner = owner;
        this.card = card;
        this.cardState = cardState;
    }
    //TODO run hashmap or no
}
