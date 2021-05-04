package controller;

import model.user.Player;
import model.user.User;
import model.zones.Address;

public class DuelMenu extends Menu{
    Player player = new Player(user);

    public Message select(Address address) {
        if (Address.map.get(address) == null)
            return new Message(TypeMessage.ERROR, "no card found in the given position");
        player.selectAddress(address);
        return new Message(TypeMessage.SUCCESSFUL, "card selected");
    }

    public Message deselect() {
        if (player.getSelectedAddress() == null) return new Message(TypeMessage.ERROR, "no card is selected yet");
        player.selectAddress(null);
        return new Message(TypeMessage.SUCCESSFUL, "card deselected");
    }

    public Message showSelectedCard() {
        if (player.getSelectedAddress() == null) return new Message(TypeMessage.ERROR, "no card is selected yet");
        //TODO check "card is not visible"
        return new Message(TypeMessage.INFO, Address.map.get(player.getSelectedAddress()).toString());
    }
}
