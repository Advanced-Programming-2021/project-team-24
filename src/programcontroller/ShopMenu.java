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
    public Message buyCard(String cardName){
        Card newCard = getCardByName(cardName);
        if (newCard == null) return new Message(TypeMessage.ERROR, "there is no card with this name");
        //check enough money
        else user.addCard(newCard);
        return null;

    }
    public Message getInfo(String cardName){
        String content = "";
        List<Card> cards = user.getCards;
        for (int i = 0; i < cards.size(); i++) {
            content += cards.get(i).getName() +":"+cards.get(i).getDescription()+"\n";
        }
        return new Message(TypeMessage.SUCCESSFUL, content);
    }
}