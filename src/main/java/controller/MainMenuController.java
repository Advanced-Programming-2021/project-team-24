package controller;

import model.deck.Deck;
import model.duel.DuelRoundManager;
import model.user.User;
import view.DuelMenu;

public class MainMenuController {
    public Message createDuel(User user, User opponent, String rounds){
        if (opponent != null) {
            Deck userDeck = user.getDecks().getActiveDeck();
            if (userDeck != null) {
                Deck opponentDeck = opponent.getDecks().getActiveDeck();
                if (opponentDeck != null) {
                    if(userDeck.isValid()){
                        if(opponentDeck.isValid()){
                            if(rounds.equals("1") || rounds.equals("3")){
                                new DuelRoundManager(user, opponent, Integer.parseInt(rounds)).run();
                                //TODO                                
                                return null;
                            }
                            else{
                                return new Message(TypeMessage.ERROR,"number of rounds is not supported");
                            }
                        }
                        else{
                            return new Message(TypeMessage.ERROR, opponent.getUsername() + "'s deck is invalid");
                        }
                    }
                    else{
                        return new Message(TypeMessage.ERROR, user.getUsername() + "'s deck is invalid");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, opponent.getUsername() + " has no active deck");
                }
            } else {
                return new Message(TypeMessage.ERROR, user.getUsername() + " has no active deck");
            }
        } else {
            return new Message(TypeMessage.ERROR, "there is no player with this username");
        }
    }
}
