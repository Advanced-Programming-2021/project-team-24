package programcontroller;


import model.card.Card;
import model.user.User;


public class ShopMenu extends Menu {
    private Card getCardByName(String cardName){
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            if (Card.getAllCards.get(i) != null) return Card.getAllCards.get(i);
        }
        return null;
    }
    public String buyCard(String cardName){
        user.
        return null;

    }
    public void getInfo(String cardName){

    }
}