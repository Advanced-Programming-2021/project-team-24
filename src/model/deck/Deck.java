package model.deck;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private List<String> mainCards;
    private List<String> sideCards;

    public Deck(String name) {
        this.name = name;
        this.mainCards = new ArrayList<String>();
        this.sideCards = new ArrayList<String>();
    }

    public String getName() {
        return this.name;
    }

    public List<String> getMainCards() {
        return this.mainCards;
    }

    public List<String> getSideCards() {
        return this.sideCards;
    }

    public int getCountOfCard(String name) {
        int count = 0;
        for (String cardName : getMainCards())
            if (cardName.equals(name)) count++;
        for (String cardName : getSideCards())
            if (cardName.equals(name)) count++;
        return count;
    }

    public void addMainCard(String name) {
        this.mainCards.add(name);
    }

    public void addSideCard(String name) {
        this.sideCards.add(name);
    }

    public void removeMainCard(String name) {
        this.mainCards.remove(name);
    }

    public void removeSideCard(String name) {
        this.sideCards.remove(name);
    }

    public boolean isValid() {
        if (this.mainCards.size() >= 40 && this.mainCards.size() <= 60) return true;
        return false;
    }
}
