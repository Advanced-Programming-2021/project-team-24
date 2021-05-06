package model.duel.zone;

import java.util.List;

import model.card.Card;
import model.card.CardHolder;
import model.deck.Deck;


public class DrawDeckZone extends Zone{    
    public DrawDeckZone(Deck deck)
    {

    }
    public boolean isFull()
    {
        return true;
    }
    public void addCard(Card card)
    {
        
    }
    
    public void removeCardHolderById(int id)
    {

    }   
    public List<CardHolder> getListOfCard()
    {
        return null;
    }
    public static void main(String[] args) {
        DrawDeckZone v = new DrawDeckZone(null);
        if(v.isFull())
        {
            
        }
    }
}
