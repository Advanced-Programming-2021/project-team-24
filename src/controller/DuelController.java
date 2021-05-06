package controller;

import model.Duel;
import model.zones.Address;

public class DuelController{
    Duel duel;

    public DuelController(Duel duel){
        this.duel = duel;
    }

    public Message select(Address address) {
        if (Address.map.get(address) == null)
            return new Message(TypeMessage.ERROR, "no card found in the given position");
        duel.getCurrentPlayer().selectAddress(address);
        return new Message(TypeMessage.SUCCESSFUL, "card selected");
    }

    public Message deselect() {
        if (duel.getCurrentPlayer().getSelectedAddress() == null) return new Message(TypeMessage.ERROR, "no card is selected yet");
        duel.getCurrentPlayer().selectAddress(null);
        return new Message(TypeMessage.SUCCESSFUL, "card deselected");
    }

    public Message showSelectedCard() {
        if (duel.getCurrentPlayer().getSelectedAddress() == null) return new Message(TypeMessage.ERROR, "no card is selected yet");
        //TODO check "card is not visible"
        return new Message(TypeMessage.INFO, Address.map.get(duel.getCurrentPlayer().getSelectedAddress()).toString());
    }
}
