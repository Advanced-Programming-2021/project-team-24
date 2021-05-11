package controller;

import model.deck.Deck;
import model.user.User;
import view.DuelMenu;

public class MainMenuController {
    public Message createDuel(User user, String opponentUsername, String rounds) {
        User opponent = User.readUser(opponentUsername);
        if (opponent != null) {
            Deck userDeck = user.getDecks().getActiveDeck();
            if (userDeck != null) {
                Deck opponentDeck = opponent.getDecks().getActiveDeck();
                if (opponentDeck != null) {
                    if(userDeck.isValid()){
                        if(userDeck.isValid()){
                            if(rounds.equals("1") || rounds.equals("3")){
                                new DuelMenu(user,opponent,rounds).run();
                                return null;
                            }
                            else{
                                return new Message(TypeMessage.ERROR,"number of rounds is not supported");
                            }
                        }
                        else{
                            return new Message(TypeMessage.ERROR, opponentUsername + "’s deck is invalid");
                        }
                    }
                    else{
                        return new Message(TypeMessage.ERROR, user.getUsername() + "'s deck is invalid");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, opponentUsername + " has no active deck");
                }
            } else {
                return new Message(TypeMessage.ERROR, user.getUsername() + " has no active deck");
            }
        } else {
            return new Message(TypeMessage.ERROR, "there is no player with this username");
        }
    }
}
