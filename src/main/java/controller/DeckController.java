package controller;

import model.card.Card;
import model.deck.Deck;
import model.deck.Decks;

import java.util.List;

public class DeckController {
    Decks decks;

    public DeckController(Decks decks) {
        this.decks = decks;
    }

    public Decks getDecks() {
        return decks;
    }

    public Deck getDeckByName(String deckName) {
        List<Deck> decks = this.decks.getDecks();
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public Message create(String name) {
        if (getDeckByName(name) == null) {
            decks.add(new Deck(name));
            return new Message(TypeMessage.SUCCESSFUL, "deck created successfully");
        } else {
            return new Message(TypeMessage.ERROR, "deck with name " + name + " already exists");
        }
    }

    public Message delete(String name) {
        if (getDeckByName(name) != null) {
            decks.remove(name);
            return new Message(TypeMessage.SUCCESSFUL, "deck deleted successfully");
        } else {
            return new Message(TypeMessage.ERROR, "deck with name " + name + "  does not exist");
        }
    }

    public Message active(String name) {
        if (getDeckByName(name) != null) {
            decks.setActiveDeck(getDeckByName(name));
            return new Message(TypeMessage.SUCCESSFUL, "deck activated successfully");
        } else {
            return new Message(TypeMessage.ERROR, "deck with name " + name + "  does not exist");
        }
    }

    public Message addCard(String cardName, String deckName, Boolean isMainCard) {
        if (Card.getCardByName(cardName) != null) {
            if (getDeckByName(deckName) != null) {
                int deckCapacity = 15;
                if (isMainCard) deckCapacity = 60;
                if (decks.getCount(deckName, isMainCard) < deckCapacity) {
                    if (getDeckByName(deckName).getCountOfCard(cardName) < 3) {
                        decks.addCard(Card.getCardByName(cardName), deckName, isMainCard);
                        return new Message(TypeMessage.SUCCESSFUL, "card added to deck successfully");
                    } else
                        return new Message(TypeMessage.ERROR, "there are already three cards with name " + cardName + " in deck " + deckName);
                } else {
                    if (isMainCard)
                        return new Message(TypeMessage.ERROR, "main deck is full");
                    else
                        return new Message(TypeMessage.ERROR, "side deck is full");
                }
            } else {
                return new Message(TypeMessage.ERROR, "deck with name " + deckName + "  does not exist");
            }
        } else {
            return new Message(TypeMessage.ERROR, "card with name " + cardName + "  does not exist");
        }
    }

    public Message removeCard(String cardName, String deckName, Boolean isMainCard) {
        if (getDeckByName(deckName) != null) {
            if (decks.getCardByName(cardName, deckName, isMainCard) != null) {
                decks.removeCard(Card.getCardByName(cardName), deckName, isMainCard);
                return new Message(TypeMessage.SUCCESSFUL, "card removed form deck successfully");
            } else {
                if (isMainCard)
                    return new Message(TypeMessage.ERROR, "card with name " + cardName + "  does not exist in main deck");
                return new Message(TypeMessage.ERROR, "card with name " + cardName + "  does not exist in side deck");
            }
        } else {
            return new Message(TypeMessage.ERROR, "deck with name " + deckName + "  does not exist");
        }
    }

    public Message showDeckCards(String name, boolean isMain) {
        if (getDeckByName(name) != null) {
            return new Message(TypeMessage.ERROR, getDeckByName(name).toStringCards(isMain));
        } else {
            return new Message(TypeMessage.ERROR, "deck with name " + name + "  does not exist");
        }
    }

}
