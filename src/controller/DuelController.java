package controller;

import model.Duel;
import model.card.Card;
import model.card.CardHolder;
import model.zones.Address;

public class DuelController {
    Duel duel;

    public DuelController(Duel duel) {
        this.duel = duel;
    }

    public Message select(Address address) {
        if (duel.getMap().get(address) == null)
            return new Message(TypeMessage.ERROR, "no card found in the given position");
        duel.getCurrentPlayer().selectAddress(address);
        return new Message(TypeMessage.SUCCESSFUL, "card selected");
    }

    public Message deselect() {
        if (duel.getCurrentPlayer().getSelectedAddress() == null)
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        duel.getCurrentPlayer().selectAddress(null);
        return new Message(TypeMessage.SUCCESSFUL, "card deselected");
    }

    public Message showSelectedCard() {
        if (duel.getCurrentPlayer().getSelectedAddress() == null)
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        //TODO check "card is not visible"
        return new Message(TypeMessage.INFO, duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).toString());
    }

    public Message showGraveyard() {
        StringBuilder stringBuilder = new StringBuilder();
        Address address = new Address("graveyard",false,0);
        for(int i=1;i<60;i++){
            CardHolder cardHolder = duel.getMap().get(address);
            if(cardHolder!=null)
                stringBuilder.append(i).append(". ").append(duel.getMap().get(address).toString()).append('\n');
            address.plusplus();
        }
        if(stringBuilder.isEmpty()) return new Message(TypeMessage.INFO,"graveyard empty");
        return new Message(TypeMessage.INFO,stringBuilder.toString());
    }
}
