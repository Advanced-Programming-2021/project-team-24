package controller;

import model.card.Card;
import model.deck.Decks;

public class SideDeckController {
    Decks decks = new Decks();
    public SideDeckController(Decks decks) {
        this.decks = decks;
    }
    public Message change(String mainCardString, String sideCardString){
        Card mainCard = Card.getCardByName(mainCardString);
        Card sideCard = Card.getCardByName(sideCardString);
        if (mainCard == null) return new Message(TypeMessage.ERROR, "There is no card with the name of " + mainCardString);
        else if (sideCard == null) return new Message(TypeMessage.ERROR, "There is no card with the name of " + sideCardString);
        else if (decks.getActiveDeck().canChangeCards(mainCard, sideCard)){
            decks.getActiveDeck().changeCards(mainCard, sideCard);
            return new Message(TypeMessage.SUCCESSFUL, "cards changed successfully");
        }
        return new Message(TypeMessage.ERROR, "can't change cards");
    }

}
