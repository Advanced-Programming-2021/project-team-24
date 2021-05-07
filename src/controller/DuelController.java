package controller;

import java.util.ArrayList;
import java.util.List;

import model.Duel;
import model.card.Card;
import model.card.CardHolder;
import model.zone.Address;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> zoneStrings = new ArrayList<String>();

    public List<CardHolder> getZones(List<String> zones)
    {
        return null;
    }
    

    public Message showGraveyard() {
        StringBuilder stringBuilder = new StringBuilder();
        Address address = new Address(new Zone("graveyard",false),0);
        for(int i=1;i<60;i++){
            CardHolder cardHolder = duel.getMap().get(address);
            if(cardHolder!=null)
                list.add(cardHolder);
            address.plusplus();
        }
        return list;
    }
    public Message attack(Address opponentCard)
    {
        
    }
    public List<CardHolder> getZone(Zone zone){
        Address address = new Address(zone,0);
        List<CardHolder> cardHolders = new ArrayList<>();
        for(int i=1;i<=60;i++){
            CardHolder cardHolder = duel.getMap().get(address);
            if(cardHolder!=null) cardHolders.add(cardHolder);
            address.plusplus();
        }
        return cardHolders;
    }
}
