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
import model.duel.filterhandle.ZoneHandler;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    private static List<EffectManager> effectManagerList;
    private HashMap<Address, CardHolder> map = new HashMap<>();
    private List<Zone> zones;    
    private Phase currentPhase;
    private HashMap<Phase, Phase> nextPhase = new HashMap<Phase, Phase>();
    private boolean changeTurnPairity;
    private List<CardHolder> cardHoldersList = new ArrayList<CardHolder>();

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
        zones = new ArrayList<>();
        this.user = user;
        changeTurnPairity = true;
        this.opponent = opponent;
        this.currentPlayer = this.user;
        Zone.clear();
        Zone.init(currentPlayer);
        Zone.init(this.opponent);
        zones.add(Zone.get("graveyard", this.user));
        zones.add(Zone.get("graveyard", this.opponent));
        zones.add(Zone.get("hand", this.user));
        zones.add(Zone.get("hand", this.opponent));
        zones.add(Zone.get("monster", this.user));
        zones.add(Zone.get("monster", this.opponent));
        zones.add(Zone.get("magic", this.user));
        zones.add(Zone.get("magic", this.opponent));
        zones.add(Zone.get("deck", this.user));
        zones.add(Zone.get("deck", this.opponent));
        this.currentPhase = Phase.DRAW;
        setNextPhaseHashMap();
        Address.init(this.opponent);
        Address.init(currentPlayer);
        setTheInitialStateOfHandCards(user.getUser(), currentPlayer);
        setTheInitialStateOfHandCards(opponent.getUser(), this.opponent);
    }

    private void setTheInitialStateOfHandCards(User user, Player player) {
        Deck firstUserDeck = user.getDecks().getActiveDeck();
        Collections.shuffle(firstUserDeck.getMainCards());
        for (int i = 0; i < firstUserDeck.getMainCards().size(); i++) {
            if (i < 5) {
                if (firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(Address.get(Zone.get("hand", player), i), new MagicCardHolder(player, (MagicCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(Address.get(Zone.get("hand", player), i), new MonsterCardHolder(player, (MonsterCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
            } else {
                if (firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(Address.get(Zone.get("deck", player), i - 5), new MagicCardHolder(player, (MagicCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(Address.get(Zone.get("deck", player), i - 5), new MonsterCardHolder(player, (MonsterCard) firstUserDeck.getMainCards().get(i), CardState.HAND));
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
        for (Zone zone : zones) {
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
        for (Zone zone : zones) {
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
        Address address = Address.get(zone, 0);
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
        for (Zone zone : zones) {
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
        for (Zone zone : zones) {
            List<CardHolder> v = getZone(zone);
            for (int i = 0; i < v.size(); i++)
                if (v.get(i).getId() == cardHolderId)
                    return Address.get(zone, i);
        }
        return null;
    }

    public void removeCardHolderByAddress(Address address) {
        Address newAddress = address.getNextPlace(), oldAddress = address;
        Zones zone = Zones.valueOfLabel(address.getZone().getName());
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
        Zones zone = Zones.valueOfLabel(zone1.getName());
        for (int i = 0; i < zone.capacity; i++) {
            if (map.get(Address.get(zone1, i)) == null) {
                if (card.getCardType().equals(CardType.MONSTER))
                {
                    MonsterCardHolder monsterCardHolder =  new MonsterCardHolder(zone1.getPlayer(), (MonsterCard) card, cardState);
                    map.put(Address.get(zone1, i), monsterCardHolder);
                    return monsterCardHolder;
                }
                else
                {
                    MagicCardHolder magicCardHolder = new MagicCardHolder(zone1.getPlayer(), (MagicCard) card, cardState);
                    map.put(Address.get(zone1, i), magicCardHolder);
                    return magicCardHolder;
                }
            }
        }
        return null;//TODO assume it's not happening
    }


    public CardHolder changeZone(int cardHolderId, Zone targetZone, CardState cardState, DuelMenu duelMenu) {
        if (getCardHolderById(cardHolderId) != null) {
            if (targetZone.getName() != null && targetZone.getName().equals("owner")) {
                Zone destination = Zone.get(targetZone.getName().split("_")[1], getCardHolderById(cardHolderId).getOwner());
                return addCard(getCardHolderById(cardHolderId).getCard(), destination, cardState);            
            } else {
                //TODO check reverse effect and so on
                if(getCardHolderById(cardHolderId).getCard().isMagic())
                {
                    //run reverse and finish it
                    if(getCardHolderZone(getCardHolderById(cardHolderId)).getName().equals("hand") || getCardHolderZone(getCardHolderById(cardHolderId)).getName().equals("magic"))
                    {
                        MagicCardHolder holder = (MagicCardHolder)(getCardHolderById(cardHolderId));
                        if(holder.getEffectManager().getEffect().getReverse() != null)
                        {
                            new EffectParser(duelMenu, duelMenu.getDuelController(), holder.getEffectManager()).getCommandResult(holder.getEffectManager().getEffect().getReverse());
                        }
                    }
                }
                else
                {
                    MonsterCardHolder monster = (MonsterCardHolder)getCardHolderById(cardHolderId);
                    for(Event event : monster.getEffects().keySet())
                    {
                        if(monster.getEffects().get(event) != null)
                            for(int i = 0; i < monster.getEffects().get(event).size(); i++)
                            {
                                if(monster.getEffects().get(event).get(i).getEffect().getReverse() != null)
                                {
                                    new EffectParser(duelMenu, duelMenu.getDuelController(), monster.getEffects().get(event).get(i)).getCommandResult(monster.getEffects().get(event).get(i).getEffect().getReverse());
                                }
                            }
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

    public void changerZone(List<Integer> cardHolders, Zone targetZone, CardState cardState) {
        for (Integer cardHolderId : cardHolders) {
            if (getCardHolderById(cardHolderId) != null) {
                if (targetZone.getName().equals("own")) {
                    //TODO
                } else {
                    addCard(getCardHolderById(cardHolderId).getCard(), targetZone, cardState);
                    removeCardHolderById(cardHolderId);
                }
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
                        //TODO: handle exception
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
            if (this.filterMatch(filter, all.get(i))) {
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
        return Zone.get(zoneArgument[1], player);
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
        for(Zone zone : zones)
        {
            zoneCount.put(zone, getZone(zone).size());
        }
        return zoneCount;
    }

    public boolean isRoundFinished() {
        if (currentPhase == Phase.END && (user.isDead() || opponent.isDead())) return true;
        return false;
    }

    public void surrender(boolean isOpponent){
        if (isOpponent) opponent.setIsDeadRounds(3);
        else user.setIsDeadRounds(3);
    }

}