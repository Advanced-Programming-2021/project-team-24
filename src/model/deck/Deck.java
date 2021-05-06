package model.deck;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private List<Card> mainCards;
    private List<Card> sideCards;

    public Deck(String name) {
        this.name = name;
        this.mainCards = new ArrayList<Card>();
        this.sideCards = new ArrayList<Card>();
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getMainCards() {
        return this.mainCards;
    }

    public List<Card> getSideCards() {
        return this.sideCards;
    }

    public int getCountOfCard(String name) {
        int count = 0;
        String cardName = null;
        for (Card card : getMainCards()) {
            cardName = card.getName();
            if (cardName.equals(name)) count++;
        }
        for (Card card : getSideCards()) {
            cardName = card.getName();
            if (cardName.equals(name)) count++;
        }
        return count;
    }

    public void addMainCard(Card card) {
        this.mainCards.add(card);
    }

    public void addSideCard(Card card) {
        this.sideCards.add(card);
    }

    public void removeMainCard(Card card) {
        this.mainCards.remove(card);
    }

    public void removeSideCard(Card card) {
        this.sideCards.remove(card);
    }

    public boolean isValid() {
        if (this.mainCards.size() >= 40 && this.mainCards.size() <= 60) return true;
        return false;
    }
}
