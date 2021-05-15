package controller;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.ElementScanner14;

import com.google.common.graph.ElementOrder.Type;

import model.duel.Duel;
import model.duel.EffectParser;
import model.effect.Effect;
import model.effect.EffectManager;
import model.card.CardHolder;
import model.card.CardState;
import model.card.MagicCard;
import model.card.MagicCardHolder;
import model.card.MonsterCard;
import model.card.MonsterCardHolder;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;

public class DuelController {
    Duel duel;

    public DuelController(Duel duel) {
        this.duel = duel;
    }

    public Message runPhase() {
        duel.nextPhase();
        if (duel.isPhase(Duel.Phase.DRAW)) {
            draw();
        }
        return new Message(TypeMessage.INFO, duel.getCurrentPhase().toString());
    }

    private void draw() {
        Zone deck = new Zone("deck", duel.getCurrentPlayer());
        Zone hand = new Zone("hand", duel.getCurrentPlayer());
        changeZoneOfLastCard(deck, hand);
    }

    private void changeZoneOfLastCard(Zone origin, Zone destination) {
        duel.setMap(new Address(destination, duel.zoneCardCount().get(destination)), duel.getMap().get(new Address(origin, duel.zoneCardCount().get(origin))));
        duel.pickCard(origin);
        duel.putCard(destination);
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
        if (duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).getOwner().equals(duel.getOpponent())) {
            CardState cardHolderState = duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).getCardState();
            if (cardHolderState == CardState.ACTIVE_MAGIC || cardHolderState == CardState.ATTACK_MONSTER || cardHolderState == CardState.VISIBLE_MAGIC || CardState.DEFENCE_MONSTER == cardHolderState) {
                return new Message(TypeMessage.INFO, duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).toString());
            } else {
                return new Message(TypeMessage.ERROR, "You can't see detail of this card");
            }
        }
        return new Message(TypeMessage.INFO, duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress()).toString());
    }

    private List<String> zoneStrings = new ArrayList<String>();

    public List<CardHolder> getZones(List<String> zones) {
        return null;
    }

    public Message activeMagicCard(Address selectedAddress) {
        CardHolder cardHolder = duel.getMap().get(selectedAddress);
        if (cardHolder.getOnwerName().equals(duel.getCurrentPlayer().getNickname())) {
            if (cardHolder.getBoolMapValue("can_active")) {
                if (cardHolder.getCardState() == CardState.SET_MAGIC) {
                    //TODO check requirement
                    MagicCardHolder magicCard = (MagicCardHolder) cardHolder;
                    
                    
                } else if (cardHolder.getCardState() == CardState.HAND) {
                    //TODO requirement
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
                        if (duel.zoneCardCount().get(new Zone("monster", duel.getCurrentPlayer())) < 5) {                            
                            if(duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
                            {
                                duel.getMap().put(getSelectedAddress(), ((CardHolder)(new MonsterCardHolder(duel.getCurrentPlayer() ,(MonsterCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.ATTACK_MONSTER))));
                                duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);                            
                            }
                            //TODO normalSummon
                            //TODO tributeSummon
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
                            if (duel.zoneCardCount().get(new Zone("spell", duel.getCurrentPlayer())) < 5) {
                                if(duel.getCurrentPlayer().getMap().getBoolMapValue("add_magic_turn"))
                                {
                                    duel.getMap().put(getSelectedAddress(), ((CardHolder)(new MagicCardHolder(duel.getCurrentPlayer() ,(MagicCard)duel.getMap().get(getSelectedAddress()).getCard(), CardState.SET_MAGIC))));
                                    duel.getCurrentPlayer().getMap().setMapValue("add_magic_turn", "true", 1);
                                }
                                else
                                {
                                    return new Message(TypeMessage.ERROR, "You have already set magic card during the turn");
                                }
                                //TODO check already summoned/set
                                //TODO setSpell/Trap
                            } else {
                                return new Message(TypeMessage.ERROR, "spell card zone is full");
                            }
                        } else {
                            if (duel.zoneCardCount().get(new Zone("monster", duel.getCurrentPlayer())) < 5) {
                                if(!duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
                                {
                                    //TODO                                
                                    duel.getMap().put(getSelectedAddress(), (CardHolder)(new MonsterCardHolder(duel.getCurrentPlayer() ,((MonsterCard)duel.getMap().get(getSelectedAddress()).getCard()), CardState.SET_DEFENCE)));                                    
                                    duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);
                                }
                                else
                                {
                                    return new Message(TypeMessage.ERROR, "You have already summoned the monster card during the turn");
                                }
                                
                                //TODO setMonster
                            } else {
                                return new Message(TypeMessage.ERROR, "monster card zone is full");
                            }
                        }
                    } else {
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
        return null;
    }

    public Address getSelectedAddress() {
        return duel.getCurrentPlayer().getSelectedAddress();
    }

    public Message flipSummon() {
        CardHolder selected = duel.getMap().get(duel.getCurrentPlayer().getSelectedAddress());
        if (selected != null) {
            if (selected.getOnwerName().equals(duel.getCurrentPlayer().getNickname())) {
                if (selected.getCardState() != CardState.ACTIVE_MAGIC && selected.getCardState() != CardState.SET_MAGIC) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                        if (selected.getCardState() == CardState.SET_DEFENCE || selected.getCardState() == CardState.DEFENCE_MONSTER) {
                            MonsterCardHolder select = (MonsterCardHolder) selected;
                            if (selected.getCardState() == CardState.SET_DEFENCE)
                                select.flip();
                            select.flipSummon();
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
            if (attacker.getOnwerName().compareTo(duel.getCurrentPlayer().getNickname()) == 0) {
                if (duel.getCardHolderZone(attacker).getName().equals(Zones.MONSTER.getValue())) {
                    MonsterCardHolder opponent = (MonsterCardHolder) duel.getMap().get(opponentCard);
                    if (duel.getCardHolderZone(duel.getMap().get(opponentCard)).getName().equals(Zones.MONSTER.getValue())) {
                        if (duel.getCurrentPhase().equals(Duel.Phase.BATTLE)) {
                            if (attacker.getBoolMapValue("can_attack")) {
                                if (opponent.getBoolMapValue("can_be_under_attack")) {
                                    if (attacker.getCardState() == CardState.ATTACK_MONSTER) 
                                    {
                                        attackCalculater(attacker, opponent);
                                    } else {
                                        if(attacker.getBoolMapValue("can_attack_in_defence"))
                                        {
                                            attackCalculater(attacker, opponent);
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

    private void attackCalculater(MonsterCardHolder attacker, MonsterCardHolder opponent) {
        //2 poss
        if (opponent.getCardState() == CardState.SET_DEFENCE) {
            opponent.flip();
            //TODO some exception
        }

        if (opponent.getCardState() == CardState.ATTACK_MONSTER) {
            int attackAmount = attacker.getAttack();
            int oppDef = attacker.getAttack();
            if (oppDef == attackAmount) {
                duel.changeZone(attacker.getId(), new Zone("graveyard", duel.getCurrentPlayer()), CardState.NONE);
                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()), CardState.NONE);
            } else if (attackAmount > oppDef) {
                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()), CardState.NONE);
                duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
            } else {
                duel.changeZone(attacker.getId(), new Zone("graveyard", duel.getCurrentPlayer()), CardState.NONE);
                duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
            }
        } else if (opponent.getCardState() == CardState.DEFENCE_MONSTER) {
            int attackAmount = attacker.getAttack();
            int oppDef = attacker.getDefence();
            if (oppDef == attackAmount) {
            } else if (attackAmount > oppDef) {
                duel.changeZone(opponent.getId(), new Zone("graveyard", duel.getOpponent()), CardState.NONE);
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
                    //TODO check if pointless try
                    //TODO check if already changed position once
                    //TODO change position
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
        Address address = new Address(zone, 0);
        List<CardHolder> cardHolders = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            CardHolder cardHolder = duel.getMap().get(address);
            if (cardHolder != null) cardHolders.add(cardHolder);
            address.plusplus();
        }
        return cardHolders;
    }

    public Boolean satisfyCondition(Integer idCardHolder, EffectManager effectManager) {
        //check phase
        //check requirement event

        Effect effect = effectManager.getEffect();
        //EffectParser effectParser = new EffectParser(null, this, this.getDuel().getCardHolderById(idCardHolder).getOwner(), effect, idCardHolder);
        
        
        return true;

    }

    public static void main(String[] args) {

    }
}
