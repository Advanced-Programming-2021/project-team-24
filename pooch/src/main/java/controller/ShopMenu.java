package controller;


import model.card.Card;
import model.user.User;

import java.util.ArrayList;
import java.util.List;


public class ShopMenu extends MainMenu {
    List<String> bannedCard = new ArrayList<>();
    public ShopMenu(User user){
        super(user);
    }
    public Message buyCard(String cardName){        
        Card card = Card.getCardByName(cardName);
        if (card == null) return new Message(TypeMessage.ERROR, "there is no card with this name");
        else if (card.getPrice()>user.getCoin()) return new Message(TypeMessage.ERROR, "not enough money");
        else {
            if(card.count > 0)
            {
                if(!card.isBanned)
                {
                    user.setCoin(user.getCoin()-card.getPrice());
                    user.addCard(card);
                    return new Message(TypeMessage.SUCCESSFUL, "successful buy");
                }
                else
                    return new Message(TypeMessage.ERROR, "this card trade is banned");
            }
            else
                return new Message(TypeMessage.ERROR, "this card have been finished");
        }
    }
    public boolean isBanned(String cardName)
    {
        if(bannedCard.contains(cardName) && bannedCard.indexOf(cardName) < bannedCard.size()){
            return true;
        }else{
            return false;
        }
    }

    public Message banCard(String cardName){
        if(Card.getCardByName(cardName).isBanned)
        {
            return new Message(TypeMessage.ERROR, "this card is banned before");            
        }else{
            if(user.getIsAdmin()){
                Card card = Card.getCardByName(cardName);
                card.isBanned = true;
                card.updateCard();
                bannedCard.add(cardName);
                return new Message(TypeMessage.SUCCESSFUL, "Successfully banned");
            }
            else
                return new Message(TypeMessage.ERROR, "you are not allowed to do this");

        }
    }
    public Message unbanCard(String cardName){
        if(Card.getCardByName(cardName).isBanned){
            if(user.getIsAdmin()){
                bannedCard.remove(cardName);
                Card card = Card.getCardByName(cardName);
                card.isBanned = false;
                card.updateCard();
                return new Message(TypeMessage.SUCCESSFUL, "successfully unbanned");
            }
            else{
                return new Message(TypeMessage.ERROR, "you are not allowed to do this");
            }
        }else{
            return new Message(TypeMessage.ERROR, "this card is not banned");
        }        
    }


    public Message increaseCardAmount(int amount, String cardName){
        Card card = Card.getCardByName(cardName);
        if(user.getIsAdmin()){
            card.count += amount;
            return new Message(TypeMessage.SUCCESSFUL, "card amount increased successfully");
        }else{
            return new Message(TypeMessage.ERROR, "you are not allowed to do this");
        }        
    }

    public Message decreaseCardAmount(int amount, String cardName){
        Card card = Card.getCardByName(cardName);
        if(user.getIsAdmin()){
            if(card.count - amount < 0){
                return new Message(TypeMessage.ERROR, "invalid decreament");
            }
            card.count -= amount;
            return new Message(TypeMessage.SUCCESSFUL, "operation done successfully");
        }else{
            return new Message(TypeMessage.ERROR, "you are not allowed to do this");
        }        
    }


    public Message getInfo(){
        String content = "";
        List<Card> cards = Card.getAllCards();
        for (int i = 0; i < cards.size(); i++) {
            content += cards.get(i).getName() +":"+cards.get(i).getPrice()+"\n";
        }
        return new Message(TypeMessage.INFO, content);
    }
}