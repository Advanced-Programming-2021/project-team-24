package model.duel;

import model.card.*;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.deck.Deck;
import model.duel.filterhandle.AttackHandler;
import model.duel.filterhandle.CardNameHandler;
import model.duel.filterhandle.CardStateHandler;
import model.duel.filterhandle.CardTypeHandler;
import model.duel.filterhandle.DefenceHandler;
import model.duel.filterhandle.IdHandler;
import model.duel.filterhandle.LevelHandler;
import model.duel.filterhandle.MonsterTypeHandler;
import model.zone.Address;
import model.duel.filterhandle.ZoneHandler;
import model.effect.Effect;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.DuelAddresses;
import model.zone.DuelZones;
import model.zone.Zone;
import model.zone.ZonesName;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.common.util.concurrent.ExecutionError;


public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    private static List<EffectManager> effectManagerList;
    private HashMap<Address, CardHolder> map = new HashMap<>();
    private Phase currentPhase;
    private HashMap<Phase, Phase> nextPhase = new HashMap<Phase, Phase>();
    private boolean changeTurnPairity;
    
    public DuelZones duelZones;
    public DuelAddresses duelAddresses;

    public enum Phase {
        DRAW,
        STANDBY,
        MAIN1,
        BATTLE,
        MAIN2,
        END;
    }

    public void nextPhase() {
        currentPhase = nextPhase.get(currentPhase);        
    }
    public void changeLifePoint(Player player, int decrement){
        player.changeLifePoint(-decrement);
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public boolean isPhase(Phase phase) {
        if (currentPhase.equals(phase)) return true;
        return false;
    }


    public static EffectManager getEffectManagerById(int id) {
        for (int i = 0; i < effectManagerList.size(); i++) {
            if (effectManagerList.get(i).getId() == id) return effectManagerList.get(i);
        }
        return null;
    }


    public Duel(Player user, Player opponent) {
        
        this.user = user;
        changeTurnPairity = true;
        this.opponent = opponent;
        this.currentPlayer = this.user;        
        this.currentPhase = Phase.DRAW;
        setNextPhaseHashMap();
        duelZones = new DuelZones(this);
        duelAddresses = new DuelAddresses(this);
        
        setTheInitialStateOfHandCards(user.getUser(), currentPlayer);
        setTheInitialStateOfHandCards(opponent.getUser(), this.opponent);
    }

    private void setTheInitialStateOfHandCards(User user, Player player) {
        Deck firstUserDeck = user.getDecks().getActiveDeck();
        Collections.shuffle(firstUserDeck.getMainCards());
        for (int i = 0; i < firstUserDeck.getMainCards().size(); i++) {
            if (i < 4) {
                if (firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(duelAddresses.get(duelZones.get("hand", player), i), new MagicCardHolder(player, (MagicCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(duelAddresses.get(duelZones.get("hand", player), i), new MonsterCardHolder(player, (MonsterCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
            } else {
                if (firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(duelAddresses.get(duelZones.get("deck", player), i - 5), new MagicCardHolder(player, (MagicCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(duelAddresses.get(duelZones.get("deck", player), i - 5), new MonsterCardHolder(player, (MonsterCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
            }

        }
    }

    public Player getCurrentPlayer() {
        if (changeTurnPairity)
            return currentPlayer;
        else
            return opponent;
    }


    public Zone getCardHolderZone(CardHolder cardHolder) {
        for (Zone zone : duelZones.getAllZones()) {
            List<CardHolder> list = getZone(zone);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == cardHolder.getId()) {
                    return zone;
                }
            }
        }
        return null;
    }

    public Player getOpponent() {
        if (changeTurnPairity)
            return this.opponent;
        else
            return currentPlayer;
    }

    public void addEffectManager(EffectManager effectManager) {
        effectManagerList.add(effectManager);
    }

    public List<CardHolder> getAllCardHolder() {
        List<CardHolder> ans = new ArrayList<CardHolder>();
        for (Zone zone : duelZones.getAllZones()) {
            ans.addAll(getZone(zone));
        }
        return ans;
    }

    public void removeEffect(int id) {
        List<CardHolder> allCard = this.getAllCardHolder();
        for (int i = 0; i < allCard.size(); i++) {
            allCard.get(i).removeEffect(id);
        }
    }

    public List<CardHolder> getZone(Zone zone) {
        Address address = duelAddresses.get(zone, 0);
        List<CardHolder> cardHolders = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            CardHolder cardHolder = getMap().get(address);
            if (cardHolder != null) cardHolders.add(cardHolder);        
            address = address.getNextPlace();
            if(address == null)
            {
                break;
            }
        }
        return cardHolders;
    }

    public CardHolder getCardHolderById(int cardHolderId) {
        for (Zone zone : duelZones.getAllZones()) {
            List<CardHolder> v = getZone(zone);
            for (int i = 0; i < v.size(); i++)
                if (v.get(i).getId() == cardHolderId)
                    return v.get(i);
        }
        if (currentPlayer.getMap().getId() == cardHolderId)
            return currentPlayer.getMap();
        if (opponent.getMap().getId() == cardHolderId)
            return opponent.getMap();
        return null;
    }

    public Address getCardHolderAddressById(int cardHolderId) {
        for (Zone zone : duelZones.getAllZones()) {
            List<CardHolder> v = getZone(zone);
            for (int i = 0; i < ZonesName.valueOfLabel(zone.getName()).capacity; i++)
            {
                Address cc = duelAddresses.get(zone, i);
                if(map.get(cc) != null)
                {
                    if(map.get(cc).getId() == cardHolderId)
                        return cc;
                }
            }
        }
        return null;
    }

    public void removeCardHolderByAddress(Address address) {
        Address newAddress = address.getNextPlace(), oldAddress = address;
        ZonesName zone = ZonesName.valueOfLabel(address.getZone().getName());
        map.put(oldAddress, null);
        //shifting(hand,deck,graveyard)
        if (!zone.isDiscrete) {
            for (int i = 0; i < zone.capacity; i++) {
                map.put(oldAddress ,map.get(newAddress));
                if(newAddress != null)
                    newAddress = newAddress.getNextPlace();
                if(newAddress == null)
                {
                    map.put(oldAddress.getNextPlace(), null);
                    break;
                }    
                oldAddress = oldAddress.getNextPlace();
                if(oldAddress == null)
                    break;
            }
        }
    }

    public void removeCardHolderById(int cardHolderId) {
        Address address = getCardHolderAddressById(cardHolderId);
        removeCardHolderByAddress(address);
    }

    public CardHolder addCard(Card card, Zone zone1, CardState cardState) {
        ZonesName zone = ZonesName.valueOfLabel(zone1.getName());
        for (int i = 0; i < zone.capacity; i++) {
            if (map.get(duelAddresses.get(zone1, i)) == null) {
                if (card.getCardType().equals(CardType.MONSTER))
                {
                    MonsterCardHolder monsterCardHolder =  new MonsterCardHolder(zone1.getPlayer(), (MonsterCard) card, cardState);
                    map.put(duelAddresses.get(zone1, i), monsterCardHolder);
                    return monsterCardHolder;
                }
                else
                {
                    MagicCardHolder magicCardHolder = new MagicCardHolder(zone1.getPlayer(), (MagicCard) card, cardState);
                    map.put(duelAddresses.get(zone1, i), magicCardHolder);
                    return magicCardHolder;
                }
            }
        }
        return null;
    }


    public CardHolder changeZone(int cardHolderId, Zone targetZone, CardState cardState, DuelMenu duelMenu) {
        if (getCardHolderById(cardHolderId) != null) {
            if (targetZone.getName() != null && targetZone.getName().equals("owner")) {
                Zone destination = duelZones.get(targetZone.getName().split("_")[1], getCardHolderById(cardHolderId).getOwner());
                return addCard(getCardHolderById(cardHolderId).getCard(), destination, cardState);            
            } else {
                //check reverse effect and so on
                if(getCardHolderById(cardHolderId).getCard().isMagic())
                {
                    //run reverse and finish it
                    if(getCardHolderZone(getCardHolderById(cardHolderId)).getName().equals("hand") || getCardHolderZone(getCardHolderById(cardHolderId)).getName().equals("magic"))
                    {                           
                        MagicCardHolder holder = (MagicCardHolder)(getCardHolderById(cardHolderId));                        
                        try{
                            for(Event event : holder.getEffects().keySet())
                            {
                                if(holder.getEffects().get(event) != null)
                                {
                                    int j = holder.getEffects().size();
                                    for(int i = 0; i < j; i++)
                                    {
                                        if(holder.getEffectManager().getActivated())
                                            if(event == Event.DEATH_OWNER && holder.getEffects().get(event)!=null)
                                            {                                        
                                                new EffectParser(duelMenu, duelMenu.getDuelController(), holder.getEffects().get(event).get(i)).runEffect();
                                            }
                                    }
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }  
                    }
                    
                }
                else
                {
                    
                    MonsterCardHolder monster = (MonsterCardHolder)getCardHolderById(cardHolderId);
                    try{
                        for(Event event : monster.getEffects().keySet())
                        {
                            if(monster.getEffects().get(event) != null)
                                for(int i = 0; i < monster.getEffects().get(event).size(); i++)
                                {
                                    if(event == Event.DEATH_OWNER && monster.getEffects().get(event)!=null)
                                    {
                                        new EffectParser(duelMenu, duelMenu.getDuelController(), monster.getEffects().get(event).get(i)).runEffect();
                                    }
                                }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }                    
                }                
                CardHolder ans = addCard(getCardHolderById(cardHolderId).getCard(), targetZone, cardState);
                removeCardHolderById(cardHolderId);
                return ans;
            }
        }
        else
        {
            return null;
        }
    }

    public void changerZone(List<Integer> cardHolders, Zone targetZone, CardState cardState,  DuelMenu duelMenu) {
        for (Integer cardHolderId : cardHolders) {
            if (getCardHolderById(cardHolderId) != null) {
                if (targetZone.getName().equals("own")) {
                    getCardHolderById(cardHolderId);
                    targetZone.getName();
                    Zone finZone = duelZones.get(targetZone.getName(), getCardHolderById(cardHolderId).getOwner());
                    changeZone(cardHolderId, finZone, cardState, duelMenu);
                } 
                else
                    changeZone(cardHolderId, targetZone, cardState, duelMenu);
                
            }
        }
    }

    public void setterMap(List<Integer> cardHolders, String key, String value, Integer time) {
        for (int i = 0; i < cardHolders.size(); i++) {
            Integer integer = cardHolders.get(i);
            if (getCardHolderById(integer) != null) {
                getCardHolderById(integer).setMapValue(key, value, time);
            }
        }
    }
    public void changerMap(List<Integer> cardHolders, String key, String value, Integer time)
    {
        for (int i = 0; i < cardHolders.size(); i++) {
            Integer integer = cardHolders.get(i);
            if (getCardHolderById(integer) != null) {
                if(getCardHolderById(integer).getCardMap().get(key) == null)
                    getCardHolderById(integer).setMapValue(key, value, time);
                else
                {
                    try {
                        Integer nValue = Integer.parseInt(getCardHolderById(integer).getCardMap().get(key));
                        nValue += Integer.parseInt(value);
                        getCardHolderById(integer).setMapValue(key,String.valueOf(nValue), time);    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                }

            }
        }
    }

    public String getterMap(List<Integer> cardHolders, String key) {
        if (cardHolders == null || cardHolders.size() == 0)
            return "";
        else {
            return getCardHolderById(cardHolders.get(0)).getValue(key);
        }
    }

    public List<CardHolder> getCardHolderFilter(Filter filter) {
        List<CardHolder> ans = new ArrayList<CardHolder>();
        List<CardHolder> all = this.getAllCardHolder();
        for (int i = 0; i < all.size(); i++) {
            //TODO
            if (this.filterMatch(filter, all.get(i))) {
                ans.add(all.get(i));
            }
        }
        return ans;
    }

    public List<Integer> getIdCardHolderFilter(Filter filter) {
        List<Integer> ans = new ArrayList<Integer>();
        List<CardHolder> all = this.getAllCardHolder();
        for (int i = 0; i < all.size(); i++) {
            if (filterMatch(filter, all.get(i))) {
                ans.add(all.get(i).getId());
            }
        }
        return ans;
    }

    public Zone parseZone(String josn, String ownerName) {
        String[] zoneArgument = josn.split("_");
        Player player = null;
        Player owner, opp;
        if(getCurrentPlayer().getNickname().equals(ownerName))
        {   
            owner = getCurrentPlayer();
            opp = getOpponent();
        }
        else
        {
            owner = getOpponent();
            opp = getCurrentPlayer();
        }
        if (zoneArgument[0].compareToIgnoreCase("my") == 0)
         player = owner;
        else player = opp;
        return duelZones.get(zoneArgument[1], player);
    }

    public boolean filterMatch(Filter filter, CardHolder cardHolder) {
        AttackHandler attack = new AttackHandler();
        CardNameHandler cardName = new CardNameHandler();
        CardStateHandler cardState = new CardStateHandler();
        CardTypeHandler cardType = new CardTypeHandler();
        DefenceHandler defence = new DefenceHandler();
        IdHandler id = new IdHandler();
        LevelHandler level = new LevelHandler();
        ZoneHandler zone = new ZoneHandler();
        MonsterTypeHandler monsterType = new MonsterTypeHandler();

        attack.setNextFilterHandler(cardName);
        cardName.setNextFilterHandler(cardState);
        cardState.setNextFilterHandler(cardType);        
        cardType.setNextFilterHandler(defence);
        defence.setNextFilterHandler(id);
        id.setNextFilterHandler(level);
        level.setNextFilterHandler(zone);        
        zone.setNextFilterHandler(monsterType);        
        return attack.Handle(filter, cardHolder, this);
    }

    public HashMap<Address, CardHolder> getMap() {
        return map;
    }

    public void setMap(Address address, CardHolder cardHolder) {
        map.put(address, cardHolder);
    }

    public void setNextPhaseHashMap() {
        nextPhase.put(Phase.DRAW, Phase.STANDBY);
        nextPhase.put(Phase.STANDBY, Phase.MAIN1);
        nextPhase.put(Phase.MAIN1, Phase.BATTLE);
        nextPhase.put(Phase.BATTLE, Phase.MAIN2);
        nextPhase.put(Phase.MAIN2, Phase.END);
        nextPhase.put(Phase.END, Phase.DRAW);
    }

    public void changePlayerTurn() {
        changeTurnPairity = !changeTurnPairity;        
    }

    public HashMap<Zone, Integer> zoneCardCount() {
        HashMap<Zone, Integer> zoneCount = new HashMap<Zone, Integer>();
        for(Zone zone : duelZones.getAllZones())
        {
            zoneCount.put(zone, getZone(zone).size());
        }
        return zoneCount;
    }

    public boolean isRoundFinished() {
        if (getCurrentPlayer().isDead() || getOpponent().isDead()) return true;
        return false;
    }

    public void surrender(){
        currentPlayer.setIsDeadRounds(1);
    }

}