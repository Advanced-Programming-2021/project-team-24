package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    Player otherPlayer;
    private static List<EffectManager> effectManagerList;
    private HashMap<Address, CardHolder> map;
    private List<Zone> zones;
    private int rounds;
    private Phase currentPhase;
    private HashMap<Phase,Phase> nextPhase = new HashMap<Phase,Phase>();
    private HashMap<Zone,int> zoneCardCount = new HashMap<Zone, int>();
    public enum Phase{
        DRAW,
        STANDBY,
        MAIN1,
        BATTLE,
        MAIN2,
        END
    }

    public void nextPhase(){
        currentPhase = nextPhase.get(currentPhase);
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public boolean isPhase(Phase phase){
        if(currentPhase.equals(phase)) return true;
        return false;
    }

    public void pickCard(Zone zone){
        int count = zoneCardCount.get(zone);
        count--;
        zoneCardCount.put(zone,count);
    }

    public void putCard(Zone zone){
        int count = zoneCardCount.get(zone);
        count--;
        zoneCardCount.put(zone,count);
    }

    public static EffectManager getEffectManagerById(int id) {
        for (int i = 0; i < effectManagerList.size(); i++) {
            if (effectManagerList.get(i).getId() == id) return effectManagerList.get(i);
        }
        return null;
    }


    public Duel(User user, User opponent , String rounds) {
        this.rounds = Integer.parseInt(rounds);
        zones = new ArrayList<Zone>();
        this.user = new Player(user);
        this.opponent = new Player(opponent);
        zones.add(new Zone("graveyard", this.user));
        zones.add(new Zone("graveyard", this.opponent));
        zones.add(new Zone("hand", this.user));
        zones.add(new Zone("hand", this.opponent));
        zones.add(new Zone("monster", this.user));
        zones.add(new Zone("monster", this.opponent));
        zones.add(new Zone("magic", this.user));
        zones.add(new Zone("magic", this.opponent));
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public Zone getCardHolderZone(CardHolder cardHolder)
    {
        for (Zone zone : zones) {
            List<CardHolder> list = getZone(zone);
            for(int i = 0; i < list.size(); i++)
            {
                if(list.get(i).equals(list))
                {
                    return zone;
                }
            }
        }
        return null;
    }
    public Player getOpponent()
    {
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
    public List<CardHolder> getZone(Zone zone){
        Address address = new Address(zone , 0);
        List<CardHolder> cardHolders = new ArrayList<>();
        for(int i=1;i<=60;i++){
            CardHolder cardHolder = getMap().get(address);
            if(cardHolder!=null) cardHolders.add(cardHolder);
            address.plusplus();
        }
        return cardHolders;
    }

    public CardHolder getCardHolderById(int cardHolderId)
    {
        for (Zone zone : zones) {
            List<CardHolder> v = getZone(zone);
            for(int i = 0; i < v.size(); i++)
                if(v.get(i).getId() == cardHolderId)
                    return v.get(i);
        }
        return null;
    }

    public Address getCardHolderAddressById(int cardHolderId)
    {
        for (Zone zone : zones) {
            List<CardHolder> v = getZone(zone);
            for(int i = 0; i < v.size(); i++)
                if(v.get(i).getId() == cardHolderId)
                    return new Address(zone,i);
        }
        return null;
    }

    public void removeCardHolderByAddress(Address address){
        Address newAddress = new Address(address), oldAddress = new Address(address);
        String zoneName = address.getZone().getName();
        //shifting(hand,deck,graveyard)
        if(zoneName.equals("hand")||zoneName.equals("deck")||zoneName.equals("graveyard")){
            newAddress.plusplus();
            map.put(newAddress,map.get(address));
            address.plusplus();
        }
        map.put(oldAddress,null);
    }

    public void removeCardHolderById(int cardHolderId)
    {
        Address address = getCardHolderAddressById(cardHolderId);
        removeCardHolderByAddress(address);
    }
    public void addCard(Card card, Zone zone, CardState cardState)
    {
        //TODO do in map class not here
    }   


    public void changeZone(int cardHolderId, model.zone.Zone targetZone, CardState cardState)
    {
        if(getCardHolderById(cardHolderId)  != null)
        {
            addCard(getCardHolderById(cardHolderId).getCard() , targetZone, cardState);
            removeCardHolderById(cardHolderId);
        }        
    }


    public void changerZone(List<Integer> cardHolders, Zone targetZone , CardState cardState)
    {
        for (Integer cardHolderId : cardHolders) {
            if(getCardHolderById(cardHolderId)  != null)
            {
                addCard(getCardHolderById(cardHolderId).getCard() , targetZone, cardState);
                removeCardHolderById(cardHolderId);
            }  
        }
    }

    public void setterMap(List<Integer> cardHolders, String key, String value)
    {
        for (Integer integer : cardHolders) {
            if(getCardHolderById(integer) != null)
            {
                getCardHolderById(integer).setMapValue(key, value);
            }
        }
    }
    public String getterMap(List<Integer> cardHolders, String key, String value)
    {
        if(cardHolders == null || cardHolders.size() == 0)
            return "";
        else
        {
            return getCardHolderById(cardHolders.get(0)).getValue(key);
        }
    }
    
    public List<CardHolder> getCardHolderFilter(Filter filter)
    {
        List<CardHolder> ans = new ArrayList<CardHolder>();
        List<CardHolder> all = this.getAllCardHolder();
        for(int i = 0; i < all.size(); i++)
        {
            if(this.filterMatch(filter, all.get(i)))
            {
                ans.add(all.get(i));
            }
        }
        return ans;
    }


    public List<Integer> getIdCardHolderFilter(Filter filter)
    {
        List<Integer> ans = new ArrayList<Integer>();
        List<CardHolder> all = this.getAllCardHolder();
        for(int i = 0; i < all.size(); i++)
        {
            if(this.filterMatch(filter, all.get(i)))
            {
                ans.add(all.get(i).getId());
            }
        }
        return ans;
    }

    public Zone parseZone(String josn){
        String[] zoneArgument = josn.split("_");
        Player player = null;
        if (zoneArgument[1].compareToIgnoreCase("my") == 0) player = currentPlayer;
        else player = opponent;
        return new Zone(zoneArgument[0], player);
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
        }
        return true;

    }
    public HashMap<Address, CardHolder> getMap() {
        return map;
    }
    public void setMap(Address address,CardHolder cardHolder) {
        map.put(address,cardHolder);
    }

    public HashMap<Zone, int> zoneCardCount() {
        return zoneCardCount;
    }
}
