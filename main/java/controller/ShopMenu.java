package controller;


import model.card.Card;
import model.user.User;

import java.util.List;


public class ShopMenu extends MainMenu {
    public ShopMenu(User user){
        super(user);
    }
    public Message buyCard(String cardName){
        Card card = Card.getCardByName(cardName);
        if (card == null) return new Message(TypeMessage.ERROR, "there is no card with this name");
        else if (card.getPrice()>user.getCoin()) return new Message(TypeMessage.ERROR, "not enough money");
        else user.addCard(card);
        return null;
    }
    public Message getInfo(){
        String content = "";
        List<Card> cards = user.getCards();
        for (int i = 0; i < cards.size(); i++) {
            content += cards.get(i).getName() +":"+cards.get(i).getDescription()+"\n";
        }
        return new Message(TypeMessage.INFO, content);
    }
}