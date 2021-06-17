package model.duel;

import model.card.*;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.deck.Deck;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;
import model.zone.Zones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    Player otherPlayer;
    private static List<EffectManager> effectManagerList;
    private HashMap<Address, CardHolder> map = new HashMap<>();
    private List<Zone> zones;
    private int rounds;
    private Phase currentPhase;
    private HashMap<Phase, Phase> nextPhase = new HashMap<Phase, Phase>();
    private HashMap<Zone, Integer> zoneCardCount = new HashMap<Zone, Integer>();

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
        //TODO change player

    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public boolean isPhase(Phase phase) {
        if (currentPhase.equals(phase)) return true;
        return false;
    }

    public void pickCard(Zone zone) {
        int count = zoneCardCount.get(zone);
        count--;
        zoneCardCount.put(zone, count);
    }

    public void putCard(Zone zone) {
        int count = zoneCardCount.get(zone);
        count--;
        zoneCardCount.put(zone, count);
    }

    public static EffectManager getEffectManagerById(int id) {
        for (int i = 0; i < effectManagerList.size(); i++) {
            if (effectManagerList.get(i).getId() == id) return effectManagerList.get(i);
        }
        return null;
    }


    public Duel(User user, User opponent, String rounds) {
        this.rounds = Integer.parseInt(rounds);
        zones = new ArrayList<>();
        this.user = new Player(user);
        this.opponent = new Player(opponent);
        this.currentPlayer = this.user;
        this.otherPlayer = this.opponent;
        Zone.init(currentPlayer);
        Zone.init(otherPlayer);
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
        Address.init(otherPlayer);
        Address.init(currentPlayer);
        //TODO draw five cards
        setTheIntialStateOfHandCards(user, currentPlayer);
        setTheIntialStateOfHandCards(opponent, this.opponent);
        Address address = Address.get(Zone.get("monster", currentPlayer), 2);
        map.put(address, new MonsterCardHolder(currentPlayer, new MonsterCard(), CardState.ATTACK_MONSTER));
        System.out.println(address);        
    }

    private void setTheIntialStateOfHandCards(User user, Player player) {
        Deck firstUserDeck = user.getDecks().getActiveDeck();        
        Collections.shuffle(firstUserDeck.getMainCards());
        for(int i = 0; i < firstUserDeck.getMainCards().size(); i++)
        {
            if(i < 5)
            {
                if(firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(Address.get(Zone.get("hand", player), i), new MagicCardHolder(player, (MagicCard)firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(Address.get(Zone.get("hand", player), i), new MonsterCardHolder(player,  (MonsterCard)firstUserDeck.getMainCards().get(i), CardState.HAND));
            }
            else
            {
                if(firstUserDeck.getMainCards().get(i).isMagic())
                    getMap().put(Address.get(Zone.get("deck", player), i - 5), new MagicCardHolder(player, (MagicCard)firstUserDeck.getMainCards().get(i), CardState.HAND));
                else
                    getMap().put(Address.get(Zone.get("deck", player), i - 5), new MonsterCardHolder(player,  (MonsterCard)firstUserDeck.getMainCards().get(i), CardState.HAND));
            }

        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public Zone getCardHolderZone(CardHolder cardHolder) {
        for (Zone zone : zones) {
            List<CardHolder> list = getZone(zone);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(list)) {
                    return zone;
                }
            }
        }
        return null;
    }

    public Player getOpponent() {
        return this.opponent;
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
            address.getNextPlace();
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
        if(currentPlayer.getMap().getId() == cardHolderId)
            return currentPlayer.getMap();
        if(opponent.getMap().getId() == cardHolderId)
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
        //shifting(hand,deck,graveyard)
        for (Zones zone : Zones.values()) {
            if (!zone.isDiscrete) {
                for (int i = 0; i < zone.capacity; i++) {
                    map.put(newAddress, map.get(oldAddress));
                    newAddress = newAddress.getNextPlace();
                    oldAddress = oldAddress.getNextPlace();
                }
                map.put(oldAddress, null);
                newAddress = address.getNextPlace();
                oldAddress = address;
            }
        }
    }

    public void removeCardHolderById(int cardHolderId) {
        Address address = getCardHolderAddressById(cardHolderId);
        removeCardHolderByAddress(address);
    }

    public void addCard(Card card, Zone zone, CardState cardState) {
        //TODO do in map class not here
    }


    public void changeZone(int cardHolderId, model.zone.Zone targetZone, CardState cardState) {
        if (getCardHolderById(cardHolderId) != null) {
            if (targetZone.getName().equals("own")) {
                    //TODO
                } else {
                    addCard(getCardHolderById(cardHolderId).getCard(), targetZone, cardState);
                    removeCardHolderById(cardHolderId);
                }
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
        for (Integer integer : cardHolders) {
            if (getCardHolderById(integer) != null) {
                getCardHolderById(integer).setMapValue(key, value, time);
            }
        }
    }

    public String getterMap(List<Integer> cardHolders, String key, String value) {
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

    public Zone parseZone(String josn) {
        String[] zoneArgument = josn.split("_");
        Player player = null;
        if (zoneArgument[1].compareToIgnoreCase("my") == 0) player = currentPlayer;
        else player = opponent;
        return Zone.get(zoneArgument[0], player);
    }

    public boolean filterMatch(Filter filter, CardHolder cardHolder) {
        //TODO
        if (filter.getIdCardHolder().size() > 0) {
            for (int i = 0; i < filter.getIdCardHolder().size(); i++) {
                if (filter.getIdCardHolder().get(i) == cardHolder.getId()) {
                    return true;
                }
            }
            return false;
        } else {
            //filter
        }
        return true;

    }

    public HashMap<Address, CardHolder> getMap() {
        return map;
    }

    public void setMap(Address address, CardHolder cardHolder) {
        map.put(address, cardHolder);
    }

    public HashMap<Zone, Integer> zoneCardCount() {
        return zoneCardCount;
    }
    public void setNextPhaseHashMap(){
        nextPhase.put(Phase.DRAW, Phase.STANDBY);
        nextPhase.put(Phase.STANDBY, Phase.MAIN1);
        nextPhase.put(Phase.MAIN1, Phase.BATTLE);
        nextPhase.put(Phase.BATTLE, Phase.MAIN2);
        nextPhase.put(Phase.MAIN2, Phase.END);
        nextPhase.put(Phase.END, Phase.DRAW);
    }
}