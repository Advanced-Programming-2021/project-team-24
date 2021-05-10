package controller;

import java.util.ArrayList;
import java.util.List;

import model.Duel;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.MagicCard;
import model.card.MagicCardHolder;
import model.card.MonsterCardHolder;
import model.effect.ChangeZone;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;

import java.util.ArrayList;
import java.util.List;

public class DuelController {
    Duel duel;

    public DuelController(Duel duel) {
        this.duel = duel;        
    }

    public Duel getDuel() {
        return duel;
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
    public Message activeMagicCard(Address selectedAddress)
    {
        CardHolder cardHolder = duel.getMap().get(selectedAddress);
        if(cardHolder.getOnwerName().equals(duel.getCurrentPlayer().getNickname()))
        {
            if(cardHolder.getCardState() == CardState.SET_MAGIC)
            {
                //TODO check requirement
                MagicCardHolder magicCard = (MagicCardHolder) cardHolder;
                
            }
            else
            if(cardHolder.getCardState() == CardState.HAND)
            {
                //TODO requirement
            }
        }
        else
        {
            //TODO
        }
    }
    public Message summon()
    {
        
        return null;
    }
    public Address getSelectedAddress()
    {
        return duel.getCurrentPlayer().getSelectedAddress();
    }
    public Message flipSummon()
    {
        CardHolder selected = duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress());
        if(selected.getOnwerName().equals(duel.getCurrentPlayer().getNickname()))
        {
            if(selected.getCardState() != CardState.ACTIVE_MAGIC && selected.getCardState() != CardState.SET_MAGIC)
            {
                if(selected.getCardState() == CardState.SET_DEFENCE)
                {
                    MonsterCardHolder select = (MonsterCardHolder)selected;
                    select.flipSummon();
                }
                else
                {
                    //TODO
                }
            }
            else
            {
                //TODO
            }            
        }
        else
        {
            //TODO
        }
        return null;
    }

    public Message attack(Address opponentCard)
    {
        MonsterCardHolder attacker = duel.getMap().get(this.duel.getCurrentPlayer().getSelectedAddress());
        if(attacker.getOnwerName().compareTo(duel.getCurrentPlayer().getNickname()) == 0)
        {            
            if(duel.getCardHolderZone(attacker).getName() == Zones.MONSTER)
            {                
                MonsterCardHolder opponent = duel.getMap().get(opponentCard)
                if(duel.getCardHolderZone(duel.getMap().get(opponentCard)) == Zones.Monster)
                {
                    if(attacker.getCardState() == CardState.ATTACK_MONSTER)
                    {
                        //2 poss
                        if(opponent.getCardState() == CardState.SET_DEFENCE)
                        {
                            opponent.flip();                            
                            //TODO some exception
                        }                   

                        if(opponent.getCardState() == CardState.ATTACK_MONSTER)
                        {
                            int attackAmount = attacker.getAttack();
                            int oppDef = attacker.getAttack();
                            if(oppDef == attackAmount)
                            {
                                duel.changeZone(attacker.getId(), new Zone("graveyard",  duel.getCurrentPlayer()));
                                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()));
                            }      
                            else
                            if(attackAmount > oppDef)
                            {
                                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()));
                                duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
                            }
                            else
                            {
                                duel.changeZone(attacker.getId(), new Zone("graveyard", duel.getCurrentPlayer()));
                                duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
                            }                            
                        }
                        else
                        if(opponent.getCardState() == CardState.DEFENCE_MONSTER)
                        {
                            int attackAmount = attacker.getAttack();
                            int oppDef = attacker.getDefence();
                            if(oppDef == attackAmount)
                            {
                            }      
                            else
                            if(attackAmount > oppDef)
                            {
                                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()));
                                duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
                            }
                            else
                            {
                                duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
                            }
                        }
                    }
                    else
                    {
                        //TODO CARD IN DEFENCE MODE
                    }
                }
            }
        }
        else
        {
            return null;
        }
        
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
    public static void main(String[] args) {
        
    }
}
