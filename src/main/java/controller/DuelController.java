package controller;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.duel.AutomaticEffectHandler;
import model.duel.Duel;
import model.duel.EffectChainer;
import model.duel.EffectParser;
import model.effect.EffectType;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.user.Player;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;
import view.DuelMenu;

public class DuelController {
    Duel duel;
    DuelMenu duelMenu;
    public HashMap<Event, Integer> duelEvents;

    public void updateAutomaticEffect()
    {
        //TODO
        new AutomaticEffectHandler(this, duelMenu).update();
    }
    public void resetDuelEvents()
    {
        for(Map.Entry event : duelEvents.entrySet())
        {
            if((Integer)event.getValue() == -1)
            {
                duelEvents.put((Event)event.getKey(), null);
            }
        }
        
    }
    public void surrender(boolean isOpponent){
        duel.surrender(isOpponent);
    }

    public boolean isRoundFinished() {
        return duel.isRoundFinished();
    }

    public void resetDuelEventTurn()
    {
        duelEvents = new HashMap<>();
    }
    public void setDuelMenu(DuelMenu duelMenu)
    {
        this.duelMenu = duelMenu;
    }  
    public HashMap<Event, Integer> getDuelEvents()
    {
        return this.duelEvents;
    }
    public void setEvent(Event event, Integer idCardHolder)
    {
        this.duelEvents.put(event, idCardHolder);
    }
    public DuelController(Duel duel) {
        
        this.duel = duel;
        //TODO wirte 5 card drawing in start of game

    }

    public Message nextPhase() {
        duel.nextPhase();

        return new Message(TypeMessage.INFO, duel.getCurrentPhase().toString());
    }

    public Message draw() {
        Zone deck = Zone.get("deck", duel.getCurrentPlayer());
        Zone hand = Zone.get("hand", duel.getCurrentPlayer());
        changeZoneOfLastCard(deck, hand);
        //TODO
        return null;
    }

    private void changeZoneOfLastCard(Zone origin, Zone destination) {
        duel.setMap(Address.get(destination, duel.zoneCardCount().get(destination)), duel.getMap().get(Address.get(origin, duel.zoneCardCount().get(origin) - 1)));
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
        if (duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).getOwner().equals(duel.getOpponent())) {
            CardState cardHolderState = duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).getCardState();
            if (cardHolderState == CardState.ACTIVE_MAGIC || cardHolderState == CardState.ATTACK_MONSTER || cardHolderState == CardState.VISIBLE_MAGIC || CardState.DEFENCE_MONSTER == cardHolderState) {
                return new Message(TypeMessage.INFO, duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).getCard().toString());
            } else {
                return new Message(TypeMessage.ERROR, "You can't see detail of this card");
            }
        }
        return new Message(TypeMessage.INFO, duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).toString());
    }

    public Message activeMagicCard(Address selectedAddress) {
        CardHolder cardHolder = duel.getMap().get(selectedAddress);
        if (cardHolder.getOwnerName().equals(duel.getCurrentPlayer().getNickname())) {
            if (cardHolder.getBoolMapValue("can_active")) {
                if (cardHolder.getCardState() == CardState.SET_MAGIC) {              
                    MagicCardHolder magicCard = (MagicCardHolder) cardHolder;
                    if(magicCard.getEffectManager().isConditionSatisfied(new EffectParser(duelMenu, this, magicCard.getEffectManager())))//TODO
                    {
                        duelEvents.put(Event.ACTIVE_SPELL, -1);
                        new EffectChainer(Event.ACTIVE_SPELL, magicCard, duel.getOpponent()).askForChain(duel.getOpponent());
                        
                    }
                    else
                        return new Message(TypeMessage.ERROR, "Activation conditions are not provided");
                    
                } else if (cardHolder.getCardState() == CardState.HAND) {
                    //TODO requirement
                    if(1 == 1)
                    {
                        return new Message(TypeMessage.ERROR, "This card is already activated");
                    }
                }
            } else {
                return new Message(TypeMessage.ERROR, "You can't active this card");
            }
        } else {
            return new Message(TypeMessage.ERROR, "Please select your own magic for activation");
        }
        return null;
    }

    public Message summon() {
        if (getSelectedAddress() != null) {            
            if(duel.getCurrentPlayer().getMap().getBoolMapValue("can_summon")){
                if (getSelectedAddress().getZone().getName().equals("hand") && !duel.getMap().get(getSelectedAddress()).getCard().isMagic()) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                        if (duel.zoneCardCount().get(Zone.get("monster", duel.getCurrentPlayer())) < 5) {
                            if(duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
                            {
                                if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON__OWNER) == null)
                                {
                                    duel.getMap().put(getSelectedAddress(), ((CardHolder)(new MonsterCardHolder(duel.getCurrentPlayer() ,(MonsterCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.ATTACK_MONSTER))));
                                    duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);                            
                                }
                                else
                                {
                                    if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON__OWNER).get(0).isConditionSatisfied(new EffectParser(null, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON__OWNER).get(0))))//TODO;
                                    {
                                        new EffectParser(duelMenu, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON).get(0)).runEffect();
                                        duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);
                                    }
                                    else
                                    {
                                        return new Message(TypeMessage.ERROR, "summon conditions are not provided");
                                    }
                                }
                            }
                        } else {
                            return new Message(TypeMessage.ERROR, "monster card zone is full");
                        }
                    } else {
                        return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, "you can’t summon this card");
                }
            }
            else
            {
                return new Message(TypeMessage.ERROR, "you can't summon card");
            }
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
        return null;
    }

    public Message set() {
        if (getSelectedAddress() != null) {
            if(duel.getCurrentPlayer().getMap().getBoolMapValue("can_set_monster")){
                if (getSelectedAddress().getZone().getName().equals("hand")) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                        if (duel.getMap().get(getSelectedAddress()).getCard().isMagic()) {
                            return setMagicCard();                            
                        } else {
                            return setMonsterCard();
                        }
                    }
                    else
                    { 
                        return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                    }                    
                }
                else {
                return new Message(TypeMessage.ERROR, "you can’t set this card");
                }            
            }
            else
            {
                return new Message(TypeMessage.ERROR, "you can't set monster");
            }
        
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
    }
    private Message setMonsterCard()
    {
        if (duel.zoneCardCount().get(Zone.get("monster", duel.getCurrentPlayer())) < 5) {
            if(!duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
            {
                if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER) == null)
                {
                    duel.getMap().put(getSelectedAddress(), ((CardHolder)(new MonsterCardHolder(duel.getCurrentPlayer() ,(MonsterCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.ATTACK_MONSTER))));
                    duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);                            
                }
                else
                {
                    if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0).isConditionSatisfied(new EffectParser(null, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0))))//TODO;
                    {
                        new EffectParser(null, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0)).runEffect();
                        duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);
                    }
                    else
                    {
                        return new Message(TypeMessage.ERROR, "summon conditions are not provided");
                    }
                }
            }
            else
            {
                return new Message(TypeMessage.ERROR, "You have already summoned the monster card during the turn");
            }
            
        } else {
            return new Message(TypeMessage.ERROR, "monster card zone is full");
        }
        return new Message(TypeMessage.SUCCESSFUL, "");


    }
    private Message setMagicCard()
    {
        if(((MagicCard) duel.getMap().get(getSelectedAddress()).getCard()).getEffect().getEffectType() == EffectType.FIELD)
        {
            //TODO check if it is full
            if(getZone(Zone.get("field", duel.getCurrentPlayer())).size() > 0)
            {
                MagicCardHolder temp = (MagicCardHolder)getZone(Zone.get("field", duel.getCurrentPlayer())).get(0);
                new EffectParser(duelMenu, this, temp.getEffectManager()).getCommandResult(((MagicCard)(temp.getCard())).getEffect().getReverse());
                changeZoneOfLastCard(Zone.get("field", duel.getCurrentPlayer()), null);//TODO      
                duel.getMap().put(Address.get(Zone.get("field", duel.getCurrentPlayer()), 1), new MagicCardHolder(duel.getCurrentPlayer(), (MagicCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.ACTIVE_MAGIC));
                //TODO check for chainer and run magic
                //TODO do reverse
                //replace new card
            }
            duel.getMap().put(Address.get(Zone.get("field", duel.getCurrentPlayer()), 0), new MagicCardHolder(duel.getCurrentPlayer(), (MagicCard)(duel.getMap().get(Address.get(Zone.get("field", duel.getCurrentPlayer()), 0)).getCard()), CardState.ACTIVE_MAGIC));            
        }
        else
        if (duel.zoneCardCount().get(Zone.get("spell", duel.getCurrentPlayer())) < 5) {
            if(duel.getCurrentPlayer().getMap().getBoolMapValue("add_magic_turn"))
            {
                duel.getMap().put(getSelectedAddress(), ((CardHolder)(new MagicCardHolder(duel.getCurrentPlayer() ,(MagicCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.SET_MAGIC))));
                duel.getCurrentPlayer().getMap().setMapValue("add_magic_turn", "true", 1);
            }
            else
            {
                return new Message(TypeMessage.ERROR, "You have already set magic card during the turn");
            }
        } else {
            return new Message(TypeMessage.ERROR, "spell card zone is full");
        }
        return new Message(TypeMessage.SUCCESSFUL, "");
    }

    public Address getSelectedAddress() {
        return duel.getCurrentPlayer().getSelectedAddress();
    }

    public Message flipSummon() {
        CardHolder selected = duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress());
        if (selected != null) {
            if (selected.getOwnerName().equals(duel.getCurrentPlayer().getNickname())) {
                if (selected.getCardState() != CardState.ACTIVE_MAGIC && selected.getCardState() != CardState.SET_MAGIC) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                        if (selected.getCardState() == CardState.SET_DEFENCE || selected.getCardState() == CardState.DEFENCE_MONSTER) {
                            MonsterCardHolder select = (MonsterCardHolder) selected;
                            if (selected.getCardState() == CardState.SET_DEFENCE)
                                select.flip();
                            select.flipSummon();
                            if(select.getEventEffect(Event.FLIP_OWNER) != null)
                            {
                                if(select.getEventEffect(Event.FLIP_OWNER).get(0).isConditionSatisfied(new EffectParser(null, this, select.getEventEffect(Event.FLIP_OWNER).get(0))))
                                {
                                    new EffectParser(null, this, select.getEventEffect(Event.FLIP_OWNER).get(0)).runEffect();
                                }                                
                            }
                        } else {
                            return new Message(TypeMessage.ERROR, "You can't flip summon this card");
                        }
                    } else {
                        return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, "Please select monster card for flip summon");
                }
            } else {
                return new Message(TypeMessage.ERROR, "Please select your own card to flip summon");
            }
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
        return null;
    }

    public Message attack(Address opponentCard) {
        MonsterCardHolder attacker = (MonsterCardHolder) duel.getMap().get(this.duel.getCurrentPlayer().getSelectedAddress());
        if (attacker != null) {
            if (attacker.getOwnerName().compareTo(duel.getCurrentPlayer().getNickname()) == 0) {
                if (duel.getCardHolderZone(attacker).getName().equals(Zones.MONSTER.getValue())) {
                    MonsterCardHolder opponent = (MonsterCardHolder) duel.getMap().get(opponentCard);
                    if (duel.getCardHolderZone(duel.getMap().get(opponentCard)).getName().equals(Zones.MONSTER.getValue())) {
                        if (duel.getCurrentPhase().equals(Duel.Phase.BATTLE)) {
                            if (attacker.getBoolMapValue("can_attack")) {
                                if (opponent.getBoolMapValue("can_be_under_attack")) {
                                    if (attacker.getCardState() == CardState.ATTACK_MONSTER) 
                                    {
                                        attackCalculator(attacker, opponent);
                                    } else {
                                        if(attacker.getBoolMapValue("can_attack_in_defence"))
                                        {
                                            attackCalculator(attacker, opponent);
                                        }
                                    }
                                } else {
                                    return new Message(TypeMessage.ERROR, "This card can't be under attack");
                                }
                            } else {
                                return new Message(TypeMessage.ERROR, "This card can't perform attack");
                            }
                        } else {
                            return new Message(TypeMessage.ERROR, "you can’t do this action in this phase");
                        }
                    } else {
                        return new Message(TypeMessage.ERROR, "Please select card in monster zone");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, "Please select monster card in monster zone as attacker");
                }
            } else {
                return new Message(TypeMessage.ERROR, "Pleas select your own card as attacker");
            }
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
        return null;
    }

    private void attackCalculator(MonsterCardHolder attacker, MonsterCardHolder opponent) {
        //2 poss
        if (opponent.getCardState() == CardState.SET_DEFENCE) {
            opponent.flip();
            //TODO some exception
        }

        if (opponent.getCardState() == CardState.ATTACK_MONSTER) {
            int attackAmount = attacker.getAttack();
            int oppDef = attacker.getAttack();
            if (oppDef == attackAmount) {
                duel.changeZone(attacker.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE);
                duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE);
            } else if (attackAmount > oppDef) {
                duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE);
                duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
            } else {
                duel.changeZone(attacker.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE);
                duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
            }
        } else if (opponent.getCardState() == CardState.DEFENCE_MONSTER) {
            int attackAmount = attacker.getAttack();
            int oppDef = attacker.getDefence();
            if (oppDef == attackAmount) {
            } else if (attackAmount > oppDef) {
                duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE);
                duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
            } else {
                duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
            }
        }
    }

    public Message directAttack() {
        if (getSelectedAddress() != null) {
            if (getSelectedAddress().getZone().getName().equals("monster")) {
                if (duel.getCurrentPhase().equals(Duel.Phase.BATTLE)) {
                    CardHolder card = duel.getMap().get(getSelectedAddress());
                    if(!card.getBoolMapValue("attack_turn"))
                    {
                        card.setMapValue("attack_turn", "true", 1);
                        duel.getOpponent().changeLifePoint(Integer.parseInt(card.getCardMap().get("attack")));
                    }
                    else
                    {
                        return new Message(TypeMessage.ERROR, "this card performed attack in this round before");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                }
            } else {
                return new Message(TypeMessage.ERROR, "you can’t attack with this card");
            }
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
        return null;
    }

    public Message changePosition() {
        if (getSelectedAddress() != null) {
            if (getSelectedAddress().getZone().getName().equals("monster")) {
                if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                    CardHolder card = duel.getMap().get(getSelectedAddress());
                    if(!card.getBoolMapValue("change_position_turn"))
                    {
                        card.setMapValue("change_position_turn", "true", 1);  
                        if(card.getCardState() == CardState.SET_DEFENCE)
                        {
                            card.flip();
                            ((MonsterCardHolder)card).flipSummon();                            
                        }
                        else
                        if(card.getCardState() == CardState.ATTACK_MONSTER)
                        {
                            ((MonsterCardHolder)card).changeCardState(CardState.DEFENCE_MONSTER);
                        }
                        else
                        {
                            ((MonsterCardHolder)card).changeCardState(CardState.ATTACK_MONSTER);
                        }
                    }
                    else{
                        return new Message(TypeMessage.ERROR, "you changed position of this card before");
                    }                    
                } else {
                    return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                }
            } else {
                return new Message(TypeMessage.ERROR, "you can’t change this card position");
            }
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }
        return null;
    }

    public List<CardHolder> getZone(Zone zone) {
        Address address = Address.get(zone, 0);
        List<CardHolder> cardHolders = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            CardHolder cardHolder = duel.getMap().get(address);
            if (cardHolder != null) cardHolders.add(cardHolder);
            address = address.getNextPlace();
            if(address == null)
                break;
        }
        return cardHolders;
    }

    public static void main(String[] args) {

    }
}