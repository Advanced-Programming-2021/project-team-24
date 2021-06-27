package controller;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.graph.ElementOrder.Type;
import com.google.gson.Gson;

import model.duel.AutomaticEffectHandler;
import model.duel.Duel;
import model.duel.EffectChainer;
import model.duel.EffectParser;
import model.duel.Duel.Phase;
import model.effect.EffectType;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.magic.MagicIcon;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.user.Player;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;
import view.DuelMenu;
import view.Global;

public class DuelController {
    Duel duel;
    DuelMenu duelMenu;
    public HashMap<Event, Integer> duelEvents;
    private int numberOfEndTurn = 0;
    public void updateAutomaticEffect()
    {
        //TODO
        new AutomaticEffectHandler(this, duelMenu).update();
    }
    public void surrender(){
        duel.surrender();
    }

    public boolean isRoundFinished() {
        return duel.isRoundFinished();
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
        this.duelEvents = new HashMap<Event, Integer>();
    }

    public Message nextPhase() {
        if (duel.getCurrentPhase() == Phase.BATTLE){
            if (duel.isRoundFinished()) return new Message(TypeMessage.INFO, "End");
        }
        if(duel.getCurrentPhase() == Phase.END){
            numberOfEndTurn++;
            for(String zone : Zone.zoneStrings){
                List<CardHolder> cardHolders = getZone(Zone.get(zone, duel.getCurrentPlayer()));
                for(int i = 0; i < cardHolders.size(); i++)
                {
                    
                    cardHolders.get(i).endTurn();
                    if(duel.getCardHolderZone(cardHolders.get(i)).getName().equals("magic"))
                    {                        
                        MagicCardHolder magic = (MagicCardHolder)cardHolders.get(i);
                        if(magic.getEffectManager().getEffect().getEffectType() == EffectType.NORMAL)
                        {
                            if(magic.getEffectManager().getActivated())
                            {
                                duel.changeZone(magic.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE, duelMenu);
                            }
                        }
                    }
                }
            }
            CardHolder temp = duel.getCurrentPlayer().getMap();
            temp.endTurn();
            duel.changePlayerTurn();
            duel.nextPhase();
            return new Message(TypeMessage.INFO, duel.getCurrentPhase().toString() + "\n" + duel.getCurrentPlayer().getNickname() + "'s turn");
        }
        else
        {
            duel.nextPhase();
        }
        return new Message(TypeMessage.INFO, duel.getCurrentPhase().toString());

    }

    public Message draw() {
        if(getZone(Zone.get("hand", duel.getCurrentPlayer())).size() == 6)
        {
            List<Integer> ans = new ArrayList<>();
            for(int i = 0; i < getZone(Zone.get("hand", duel.getCurrentPlayer())).size(); i++)
            {
                ans.add(getZone(Zone.get("hand", duel.getCurrentPlayer())).get(i).getId());
            }
            duel.changeZone(duelMenu.selective(ans, 1, "please select one card from your hand to remove").get(0), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE, duelMenu);

        }
        Zone deck = Zone.get("deck", duel.getCurrentPlayer());
        Zone hand = Zone.get("hand", duel.getCurrentPlayer());
        String cardName = changeZoneOfLastCard(deck, hand, duelMenu).getCard().getName();        
        return new Message(TypeMessage.SUCCESSFUL, "card " + cardName + " drawed successfully.");
    }

    private CardHolder changeZoneOfLastCard(Zone origin, Zone destination, DuelMenu duelMenu) {
        //TODO check reverse effect
        return duel.changeZone(getZone(origin).get(getZone(origin).size() - 1).getId(), destination, CardState.NONE, duelMenu);        
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
    public Message activeMagic()
    {
        if(getSelectedAddress() != null)
        {
            if(duel.getMap().get(getSelectedAddress()).getOwner().getNickname().equals(duel.getCurrentPlayer().getNickname()))
            {
                if(duel.getMap().get(getSelectedAddress()).getCard().isMagic())
                {
                    return activeMagicCard(getSelectedAddress());
                    
                }
                else
                    return new Message(TypeMessage.ERROR, "this is not magic card");
            }
            else
                return new Message(TypeMessage.ERROR, "you can't active this card");
        }
        else
            return new Message(TypeMessage.ERROR, "no card selected yet");
            
            
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
                        new EffectChainer(Event.ACTIVE_SPELL, magicCard, duel.getOpponent(), this).askForChain(duel.getOpponent());
                        updateAutomaticEffect();
                        return new Message(TypeMessage.SUCCESSFUL, "");
                    }
                    else
                        return new Message(TypeMessage.ERROR, "Activation conditions are not provided");                                    
                }
                else
                    return new Message(TypeMessage.ERROR, "please select card in magic zone");
            } else {
                return new Message(TypeMessage.ERROR, "You can't active this card");
            }
        } else {
            return new Message(TypeMessage.ERROR, "Please select your own magic for activation");
        }
        
    }

    public Message summon() {
        if (getSelectedAddress() != null) {            
            if(duel.getCurrentPlayer().getMap().getBoolMapValue("can_summon")){
                if (getSelectedAddress().getZone().getName().equals("hand") && !duel.getMap().get(getSelectedAddress()).getCard().isMagic()) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.MAIN1) || duel.getCurrentPhase().equals(Duel.Phase.MAIN2)) {
                        if (duel.zoneCardCount().get(Zone.get("monster", duel.getCurrentPlayer())) < 5) {
                            if(!duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
                            {
                                if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON_OWNER) == null)
                                {
                                    CardHolder temp = duel.changeZone(duel.getMap().get(getSelectedAddress()).getId(),Zone.get("monster", duel.getCurrentPlayer()),CardState.ATTACK_MONSTER , duelMenu);                                    
                                    duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);                                                                
                                    temp.setMapValue("change_position_turn", "true", 1);
                                    updateAutomaticEffect();
                                }
                                else
                                {
                                    List<CardHolder> init = getZone(Zone.get("monster", duel.getCurrentPlayer()));
                                    if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON_OWNER).get(0).isConditionSatisfied(new EffectParser(duelMenu, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON_OWNER).get(0))))//TODO;
                                    {
                                        
                                        new EffectParser(duelMenu, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SUMMON_OWNER).get(0)).runEffect();
                                        List<CardHolder> second =getZone(Zone.get("monster", duel.getCurrentPlayer()));
                                        if(delListFromAnother(second, init).size() > 0)
                                        {
                                            duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);
                                            delListFromAnother(second,init).get(0).setMapValue("change_position_turn", "true", 1);
                                        }
                                        else
                                            return new Message(TypeMessage.ERROR, "you weren't able to summon");
                                        updateAutomaticEffect();
                                    }
                                    else
                                    {
                                        return new Message(TypeMessage.ERROR, "summon conditions are not provided");
                                    }
                                }
                                return new Message(TypeMessage.SUCCESSFUL, "card summoned successfully");
                            }
                            else
                                return new Message(TypeMessage.ERROR, "you have added monster before");
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
    private List<CardHolder> delListFromAnother(List<CardHolder> first, List<CardHolder> second)
    {
        List<CardHolder> ans = new ArrayList<>();
        for(int i = 0; i < first.size(); i++)
        {
            int flag = 0;
            for(int j = 0; j < second.size(); j++)
            {
                if(second.get(j).getId() == first.get(i).getId())
                {
                    flag = 1;
                }
            }
            if(flag == 0)
                ans.add(first.get(i));
        }
        return ans;

    }
    private Message setMonsterCard()
    {
        if (duel.zoneCardCount().get(Zone.get("monster", duel.getCurrentPlayer())) < 5) {
            if(!duel.getCurrentPlayer().getMap().getBoolMapValue("add_monster_turn"))
            {                
                if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER) == null)
                {
                    CardHolder temp = duel.changeZone(duel.getMap().get(getSelectedAddress()).getId(),Zone.get("monster", duel.getCurrentPlayer()), CardState.SET_DEFENCE, duelMenu);                    
                    duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);                            
                    temp.setMapValue("change_position_turn", "true", 1);
                }
                else
                {
                    List<CardHolder> init = getZone(Zone.get("monster", duel.getCurrentPlayer()));
                    if(((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0).isConditionSatisfied(new EffectParser(duelMenu, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0))))
                    {                                            
                        new EffectParser(duelMenu, this, ((MonsterCardHolder)duel.getMap().get(getSelectedAddress())).getEventEffect(Event.SET_OWNER).get(0)).runEffect();
                        List<CardHolder> second = getZone(Zone.get("monster", duel.getCurrentPlayer()));
                        if(delListFromAnother(second, init).size() > 0)
                        {
                            duel.getCurrentPlayer().getMap().setMapValue("add_monster_turn", "true", 1);
                            delListFromAnother(second, init).get(0).setMapValue("change_position_turn", "true", 1);
                        }            
                        else
                            return new Message(TypeMessage.ERROR,"you coudln't set the card");
                        updateAutomaticEffect();            
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
        return new Message(TypeMessage.SUCCESSFUL, "monster set successfully");


    }
    private Message setMagicCard()
    {
        if(((MagicCard) duel.getMap().get(getSelectedAddress()).getCard()).getEffect().getEffectType() == EffectType.FIELD)
        {
            if(duel.getCurrentPlayer().getMap().getBoolMapValue("can_active_field"))
            {
                MagicCardHolder added;
                if(getZone(Zone.get("field", duel.getCurrentPlayer())).size() > 0)
                {                                   
                    MagicCardHolder temp = (MagicCardHolder)getZone(Zone.get("field", duel.getCurrentPlayer())).get(0);                
                    duel.changeZone(temp.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE, duelMenu);
                    
                }
                added = (MagicCardHolder) duel.changeZone(duel.getMap().get(getSelectedAddress()).getId(), Zone.get("field", duel.getCurrentPlayer()), CardState.ACTIVE_MAGIC, duelMenu);                
                new EffectChainer(Event.ACTIVE_SPELL, added, duel.getOpponent(), this).askForChain(duel.getOpponent());
                updateAutomaticEffect();
            }
            else
                return new Message(TypeMessage.ERROR, "you can't active field card");
            
        }
        else
        if (duel.zoneCardCount().get(Zone.get("magic", duel.getCurrentPlayer())) < 5) {                            
            MagicCardHolder temp = (MagicCardHolder)duel.changeZone(duel.getMap().get(getSelectedAddress()).getId(), Zone.get("magic", duel.getCurrentPlayer()), CardState.SET_MAGIC, duelMenu);                                
            int idNewAdded = temp.getId();
            if(((MagicCard) duel.getCardHolderById(idNewAdded).getCard()).isSpell() == true)
            {
                duel.getCardHolderById(idNewAdded).setMapValue("can_active", "false", 1);
            }               
            new EffectChainer(Event.SET, temp, duel.getOpponent(), this).askForChain(duel.getOpponent());
            updateAutomaticEffect();
        
        } else {
            return new Message(TypeMessage.ERROR, "magic card zone is full");
        }
        return new Message(TypeMessage.SUCCESSFUL, "magic card set successfully");
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
                        if(selected.getCardMap().get("change_position_turn") == null)
                        {
                            if (selected.getCardState() == CardState.SET_DEFENCE || selected.getCardState() == CardState.DEFENCE_MONSTER) {
                                MonsterCardHolder select = (MonsterCardHolder) selected;
                                if (selected.getCardState() == CardState.SET_DEFENCE)
                                    select.flip();
                                activeEffectByEvent(select, Event.FLIP);
                                select.flipSummon();
                                activeEffectByEvent(select, Event.FLIP_SUMMON);
                                eventChainer(select, Event.FLIP_SUMMON);
                                eventChainer(select, Event.FLIP);
                                updateAutomaticEffect();
                                return new Message(TypeMessage.SUCCESSFUL, "flip summoned succssfully");
                                
                            } else {
                                return new Message(TypeMessage.ERROR, "You can't flip summon this card");
                            }
                        }
                        else
                            return new Message(TypeMessage.ERROR, "you changed the direction before");
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
    }

    public Message attack(Address opponentCard) {        
        if((this.duel.getCurrentPlayer().getSelectedAddress()) == null)
        {
            return new Message(TypeMessage.ERROR, "invalid card selection");
        }
        if(duel.getMap().get(this.duel.getCurrentPlayer().getSelectedAddress()).getCard().isMagic())
        {
            return new Message(TypeMessage.ERROR, "you can't attack with magic");
        }        
        MonsterCardHolder attacker = (MonsterCardHolder) duel.getMap().get(this.duel.getCurrentPlayer().getSelectedAddress());
        if (attacker != null) {
            if(numberOfEndTurn > 0)
            {
                if(duel.getCardHolderZone(attacker).getName().equals("monster"))
                {
                    if (attacker.getOwnerName().compareTo(duel.getCurrentPlayer().getNickname()) == 0) {
                        if (!attacker.getCard().isMagic()) {                        
                            if(opponentCard != null)
                            {
                                if(attacker.getCardState().equals(CardState.DEFENCE_MONSTER) || attacker.getCardState().equals(CardState.SET_DEFENCE))
                                {
                                    return new Message(TypeMessage.ERROR, "you can't perform attack with defence mosnter");
                                }
                                else
                                {
                                    if(duel.getMap().get(opponentCard) != null)
                                    {
                                        MonsterCardHolder opponent = (MonsterCardHolder) duel.getMap().get(opponentCard);                            
                                        if (duel.getCardHolderZone(duel.getMap().get(opponentCard)).getName().equals(Zones.MONSTER.getValue())) {
                                            if (duel.getCurrentPhase().equals(Duel.Phase.BATTLE)) {
                                                if (attacker.getBoolMapValue("can_attack")) {
                                                    if (opponent.getBoolMapValue("can_be_under_attack")) {
                                                        if (attacker.getCardState() == CardState.ATTACK_MONSTER) 
                                                        {
                                                            return attackCalculator(attacker, opponent);                                                        
                                                        } else {
                                                            return attackCalculator(attacker, opponent);                                                            
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
                                    }
                                    else
                                        return new Message(TypeMessage.ERROR, "Invalid opponent selection");
                                }
                            }
                            else
                                return new Message(TypeMessage.ERROR, "invalid card have been selected");
                        } else {
                            return new Message(TypeMessage.ERROR, "Please select monster card in monster zone as attacker");
                        }
                    } else {
                        return new Message(TypeMessage.ERROR, "Pleas select your own card as attacker");
                    }
                }
                else
                    return new Message(TypeMessage.ERROR, "you can't attack with card in selected zone");
            }
            else
                return new Message(TypeMessage.ERROR, "you can't perform attack in first turn");
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }        
    }

    private Message attackCalculator(MonsterCardHolder attacker, MonsterCardHolder opponent) {
        //2 poss
        if (opponent.getCardState() == CardState.SET_DEFENCE) {
            opponent.flip();
            activeEffectByEvent(opponent, Event.FLIP_OWNER);
            updateAutomaticEffect();
        }
        if(opponent.getEffects().get(Event.UNDER_ATTACK_OWNER) != null && opponent.getEffects().get(Event.UNDER_ATTACK_OWNER).size() > 0)
        {
            List<String> temp = new ArrayList<>();
            temp.add(String.valueOf(attacker.getId()));
            EffectParser underAttackParser = new EffectParser(duelMenu, this, opponent.getEffects().get(Event.UNDER_ATTACK_OWNER).get(0));
            underAttackParser.setExtraKeyWord("attacker", new Gson().toJson(temp));
            underAttackParser.runEffect();            
            updateAutomaticEffect();
            return new Message(TypeMessage.SUCCESSFUL, "");
        }
        else
        if(attacker.getEffects().get(Event.ATTACK_OWNER) != null && attacker.getEffects().get(Event.ATTACK_OWNER).size() > 0)
        {
            List<String> temp = new ArrayList<>();
            temp.add(String.valueOf(opponent.getId()));
            EffectParser attackerParse = new EffectParser(duelMenu, this, attacker.getEffects().get(Event.UNDER_ATTACK_OWNER).get(0));
            attackerParse.setExtraKeyWord("under_attack", new Gson().toJson(temp));
            attackerParse.runEffect();
            updateAutomaticEffect();
            return new Message(TypeMessage.SUCCESSFUL, "");
        }
        else
        {        
            attacker.setMapValue("change_position_turn", "0", 1);
            if (opponent.getCardState() == CardState.ATTACK_MONSTER) {
                int attackAmount = attacker.getAttack();
                int oppDef = opponent.getAttack();
                if (oppDef == attackAmount) {
                    duel.changeZone(attacker.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE, duelMenu);
                    duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE, duelMenu);
                    return new Message(TypeMessage.SUCCESSFUL, "both you and your opponent monster cards are destroyed and no one recieves damage");
                } else if (attackAmount > oppDef) {
                    duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE, duelMenu);
                    duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
                    return new Message(TypeMessage.SUCCESSFUL, "your opponent monster is destroyed and recieves" + String.valueOf(attackAmount - oppDef) + " damage");
                } else {
                    duel.changeZone(attacker.getId(), Zone.get("graveyard", duel.getCurrentPlayer()), CardState.NONE, duelMenu);
                    duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);
                    return new Message(TypeMessage.SUCCESSFUL, "your card have been destroyed and you recieved" + String.valueOf(oppDef - attackAmount+ " damage"));
                }
            } else {
                int attackAmount = attacker.getAttack();
                int oppDef = attacker.getDefence();
                if (oppDef == attackAmount) {
                    return new Message(TypeMessage.SUCCESSFUL, "nothing have happend");
                } else if (attackAmount > oppDef) {
                    duel.changeZone(opponent.getId(), Zone.get("graveyard", duel.getOpponent()), CardState.NONE, duelMenu);
                    duel.getOpponent().changeLifePoint(-attackAmount + oppDef);
                    return new Message(TypeMessage.SUCCESSFUL, "opponent card is destroyed and no one recieved damage");
                } else {                    
                    duel.getCurrentPlayer().changeLifePoint(attackAmount - oppDef);                    
                    return new Message(TypeMessage.SUCCESSFUL, "opponent card is not destroyed and you recieved" + String.valueOf(oppDef - attackAmount)+ " damage");
                }
            }
            
        }
    }

    public Message directAttack() {
        if (getSelectedAddress() != null) {
            if(numberOfEndTurn > 0)
            {                
                if (getSelectedAddress().getZone().getName().equals("monster")) {
                    if (duel.getCurrentPhase().equals(Duel.Phase.BATTLE)) {
                        CardHolder card = duel.getMap().get(getSelectedAddress());
                        if(card.getCardState().equals(CardState.DEFENCE_MONSTER) || card.getCardState().equals(CardState.SET_DEFENCE))
                        {   
                            return new Message(TypeMessage.ERROR, "you can't perform attack with defence monster");
                        }
                        else
                        {
                            if(getZone(Zone.get("monster", duel.getOpponent())).size() == 0)
                            {
                                if(!card.getBoolMapValue("attack_turn"))
                                {
                                    card.setMapValue("attack_turn", "true", 1);
                                    duel.getOpponent().changeLifePoint(-Integer.parseInt(card.getCardMap().get("attack")));
                                    updateAutomaticEffect();
                                    return new Message(TypeMessage.SUCCESSFUL, "direct attack performed successfully with "+ Integer.parseInt(card.getCardMap().get("attack")) + " inflict damage to opponent");
                                }
                                else
                                {
                                    return new Message(TypeMessage.ERROR, "this card performed attack in this round before by card");
                                }
                            }
                            else
                                return new Message(TypeMessage.ERROR, "the opponent monster field is not empty");
                        }                        
                    } else {
                        return new Message(TypeMessage.ERROR, "action not allowed in this phase");
                    }
                } else {
                    return new Message(TypeMessage.ERROR, "you can’t attack with this card");
                }
            }
            else
                return new Message(TypeMessage.ERROR, "you can't perform attack in first turn");
        } else {
            return new Message(TypeMessage.ERROR, "no card is selected yet");
        }        
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
                        return new Message(TypeMessage.SUCCESSFUL, "changed position successfully");
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
    }
    public void eventChainer(CardHolder cardHolder, Event event)
    {
        duelEvents.put(event, 1);
        new EffectChainer(event, duel.getOpponent(), this).askForChain(duel.getOpponent());
        duelEvents.put(event, null);
    }
    public void activeEffectByEvent(CardHolder cardHolder, Event event)
    {
        if(cardHolder.getEffects().get(event)!=null && cardHolder.getEffects().get(event).size() > 0)
        {
            new EffectParser(duelMenu, this, cardHolder.getEffects().get(event).get(0)).runEffect();
        }
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