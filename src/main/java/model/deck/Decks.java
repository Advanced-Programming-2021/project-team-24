package model.deck;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Decks {
    List<Deck> decks = new ArrayList<>();
    Deck activeDeck;

    public void add(Deck deck) {
        decks.add(deck);
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public Deck getDeckByName(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public Card getCardByName(String cardName, String deckName, boolean isMainCard) {
        List<Card> cards = new ArrayList<>();
        if (isMainCard) cards = getDeckByName(deckName).getMainCards();
        else cards = getDeckByName(deckName).getSideCards();
        for (Card card : cards){
            if(card.getName().equals(cardName)) return card;
        }
        return null;
    }

    public void remove(String name) {
        decks.remove(getDeckByName(name));
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public void addCard(Card card, String deckName, Boolean isMainCard) {
        if (isMainCard)
            getDeckByName(deckName).addMainCard(card);
        else
            getDeckByName(deckName).addSideCard(card);
    }

    public void removeCard(Card card, String deckName, Boolean isMainCard) {
        if (isMainCard)
            getDeckByName(deckName).removeMainCard(card);
        else
            getDeckByName(deckName).removeSideCard(card);
    }

    public int getCount(String deckName,boolean isMain){
        if(isMain) return getDeckByName(deckName).getMainCards().size();
        else return getDeckByName(deckName).getSideCards().size();
    }
}
