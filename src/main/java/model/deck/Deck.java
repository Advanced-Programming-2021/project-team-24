package model.deck;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;    
    private List<String> mainCardName;
    private List<String> sideCardName;

    public List<String> getMainCardName() {
        return this.mainCardName;
    }

    public void setMainCardName(List<String> mainCardName) {
        this.mainCardName = mainCardName;
    }

    public List<String> getSideCardName() {
        return this.sideCardName;
    }

    public void setSideCardName(List<String> sideCardName) {
        this.sideCardName = sideCardName;
    }
 
    private transient List<Card> mainCards;
    private transient List<Card> sideCards;

    public Deck(String name) {
        this.name = name;
        this.mainCards = new ArrayList<Card>();
        this.sideCards = new ArrayList<Card>();
        this.mainCardName = new ArrayList<>();
        this.sideCardName = new ArrayList<>();
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
        this.mainCardName.add(card.getName());
    }

    public void addSideCard(Card card) {
        this.sideCards.add(card);
        this.sideCardName.add(card.getName());
    }

    public void removeMainCard(Card card) {
        this.mainCards.remove(card);
        this.mainCardName.remove(card.getName());
    }

    public void removeSideCard(Card card) {
        this.sideCards.remove(card);
        this.sideCardName.remove(card.getName());
    }

    public boolean canChangeCards(Card main, Card side){
        if (this.doesContainCard(main, true) && this.doesContainCard(side, false)) return true;
        return false;
    }

    public void changeCards(Card main, Card side){
        addMainCard(side);
        removeSideCard(side);
        addSideCard(main);
        removeMainCard(main);
    }

    public boolean doesContainCard(Card card, boolean isMain){
        if (isMain){
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(card.getName())) return true;
            }
        }
        else {
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(card.getName())) return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        if (this.mainCards.size() >= 40) return true;
        return false;
    }

    public String toStringCards(boolean isMain) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Deck ").append(this.name).append('\n');
        List<Card> cards = new ArrayList<>();
        if (isMain) {
            stringBuilder.append("Main deck:");
            cards = this.mainCards;
        } else {
            stringBuilder.append("Side deck:");
            cards = this.sideCards;
        }
        stringBuilder.append('\n');
        stringBuilder.append("Monsters:").append('\n');
        for (Card card : cards) {
            if (!card.isMagic())
                stringBuilder.append(card.toString());
        }
        stringBuilder.append("Spell and Traps:").append('\n');
        for (Card card : cards) {
            if (card.isMagic())
                stringBuilder.append(card.toString());
        }
        return stringBuilder.toString();
    }
}
