package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import model.card.Card;
import model.card.CardHolder;
import model.effect.EffectManager;
import model.user.Player;
import model.user.User;
import model.zone.Address;

public class Duel {
    Player user;
    Player opponent;
    Player currentPlayer;
    private static List<EffectManager> effectManagerList;
    private HashMap<Address, CardHolder> map;
    private List<Zone> zones;
    public static EffectManager getEffectManagerById(int id) {
        for (int i = 0; i < effectManagerList.size(); i++) {
            if (effectManagerList.get(i).getId() == id) return effectManagerList.get(i);
        }
        return null;
    }
    
    public Duel(User user, User opponent) {
        zones = new ArrayList<Zone>();
        zones.add(new Zone("graveyard", false));
        zones.add(new Zone("graveyard", true));        
        zones.add(new Zone("hand", false));
        zones.add(new Zone("hand", true));
        zones.add(new Zone("monster", false));
        zones.add(new Zone("monster", true));
        zones.add(new Zone("magic", false));
        zones.add(new Zone("magic", true));
        
        
        this.user = new Player(user);
        this.opponent = new Player(opponent);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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
            CardHolder cardHolder = duel.getMap().get(address);
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
    public void removeCardHolder(int cardHolderId)
    {
        //TODO Hesam
        // maybe just make it empty or totally remove in hand or something else
    }
    public void addCard(Card card, Zone zone)
    {
        //TODO do in map class not here
    }   


    public void changeZone(int cardHolderId, Zone targetZone)
    {
        if(getCardHolderById(cardHolderId)  != null)
        {
            addCard(getCardHolderById(cardHolderId).getCard() , targetZone);
            removeCardHolder(cardHolderId);            
        }        
    }


    public void changerZone(List<Integer> cardHolders, Zone targetZone)
    {
        for (Integer cardHolderId : cardHolders) {
                if(getCardHolderById(cardHolderId)  != null)
            {
                addCard(getCardHolderById(cardHolderId).getCard() , targetZone);
                removeCardHolder(cardHolderId);            
            }  
        }
    }


    public HashMap<Address, CardHolder> getMap() {
        return map;
    }
}
