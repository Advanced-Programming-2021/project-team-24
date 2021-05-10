<<<<<<< HEAD
=======
<<<<<<<< HEAD:src/main/java/model/duel/Filter.java
package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.card.CardHolder;
import model.card.CardType;

public class Filter {
    

    private int minLevel;
    private int maxLevel;
    private String cardType;
    private Integer minAttack;
    private Integer maxAttack;
    private Integer minDefence;
    private Integer maxDefence;
    private List<String> cardNames;
    private List<Integer> idCardHolder;
    private List<String> zones;
    private String ownerName;

    public int getMinLevel() {
        return this.minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getMinAttack() {
        return this.minAttack;
    }

    public void setMinAttack(Integer minAttack) {
        this.minAttack = minAttack;
    }

    public Integer getMaxAttack() {
        return this.maxAttack;
    }

    public void setMaxAttack(Integer maxAttack) {
        this.maxAttack = maxAttack;
    }

    public Integer getMinDefence() {
        return this.minDefence;
    }

    public void setMinDefence(Integer minDefence) {
        this.minDefence = minDefence;
    }

    public Integer getMaxDefence() {
        return this.maxDefence;
    }

    public void setMaxDefence(Integer maxDefence) {
        this.maxDefence = maxDefence;
    }

    public List<String> getCardNames() {
        return this.cardNames;
    }

    public void setCardNames(List<String> cardNames) {
        this.cardNames = cardNames;
    }

    public List<Integer> getIdCardHolder() {
        return this.idCardHolder;
    }

    public void setIdCardHolder(List<Integer> idCardHolder) {
        this.idCardHolder = idCardHolder;
    }

    public List<String> getZones() {
        return this.zones;
    }

    public void setZones(List<String> zones) {
        this.zones = zones;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public Filter(int cardHolderId, String ownerName)
    {
        this.idCardHolder = new ArrayList<Integer>();
        this.idCardHolder.add(cardHolderId);
        this.ownerName = ownerName;
    }
    
    public Filter(String cardName, List<String> zones, String ownerName)    
    {
        this.cardNames = new ArrayList<String>();
        this.cardNames.add(cardName);
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(List<String> zones, String ownerName)
    {
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(int minLevel, int maxLevel, String ownerName)
    {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.ownerName = ownerName;
    }
    public Filter(HashMap<String, String> data, String ownerName)
    {
        this.ownerName = ownerName;
        //TODO others;
    }    
    
}
========
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Duel;
import model.card.CardHolder;
import model.card.CardType;
import model.duel.filterhandle.AttackHandler;
import model.duel.filterhandle.CardNameHandler;
import model.duel.filterhandle.DefenceHandler;
import model.duel.filterhandle.FilterHandler;
import model.duel.filterhandle.IdHandler;
import model.duel.filterhandle.LevelHandler;
import model.duel.filterhandle.ZoneHandler;

public class Filter {
    

    private int minLevel;
    private int maxLevel;
    private String cardType;
    private Integer minAttack;
    private Integer maxAttack;
    private Integer minDefence;
    private Integer maxDefence;
    private List<String> cardNames;
    private List<Integer> idCardHolder;
    private List<String> zones;
    private String ownerName;

    public Integer getMinLevel() {
        return this.minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getMinAttack() {
        return this.minAttack;
    }

    public void setMinAttack(Integer minAttack) {
        this.minAttack = minAttack;
    }

    public Integer getMaxAttack() {
        return this.maxAttack;
    }

    public void setMaxAttack(Integer maxAttack) {
        this.maxAttack = maxAttack;
    }

    public Integer getMinDefence() {
        return this.minDefence;
    }

    public void setMinDefence(Integer minDefence) {
        this.minDefence = minDefence;
    }

    public Integer getMaxDefence() {
        return this.maxDefence;
    }

    public void setMaxDefence(Integer maxDefence) {
        this.maxDefence = maxDefence;
    }

    public List<String> getCardNames() {
        return this.cardNames;
    }

    public void setCardNames(List<String> cardNames) {
        this.cardNames = cardNames;
    }

    public List<Integer> getIdCardHolder() {
        return this.idCardHolder;
    }

    public void setIdCardHolder(List<Integer> idCardHolder) {
        this.idCardHolder = idCardHolder;
    }

    public List<String> getZones() {
        return this.zones;
    }

    public void setZones(List<String> zones) {
        this.zones = zones;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public Filter(int cardHolderId, String ownerName)
    {
        this.idCardHolder = new ArrayList<Integer>();
        this.idCardHolder.add(cardHolderId);
        this.ownerName = ownerName;
    }
    
    public Filter(String cardName, List<String> zones, String ownerName)    
    {
        this.cardNames = new ArrayList<String>();
        this.cardNames.add(cardName);
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(List<String> zones, String ownerName)
    {
        this.zones = zones;
        this.ownerName = ownerName;
    }
    public Filter(int minLevel, int maxLevel, String ownerName)
    {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.ownerName = ownerName;
    }
    public Filter(HashMap<String, String> data, String ownerName)
    {
        this.ownerName = ownerName;
        //TODO others;
    }    
    public boolean match(CardHolder cardHolder, Duel duel)
    {
        IdHandler id = new IdHandler();        
        CardNameHandler cardName = new CardNameHandler();    
        LevelHandler level = new LevelHandler();
        AttackHandler attack = new AttackHandler();
        DefenceHandler defence = new DefenceHandler();
        ZoneHandler zoneH = new ZoneHandler();        
        id.setNextFilterHandler(cardName);
        cardName.setNextFilterHandler(level);
        level.setNextFilterHandler(attack);
        attack.setNextFilterHandler(defence);
        defence.setNextFilterHandler(zoneH);

        return id.Handle(this, cardHolder, duel);
    }
    
}
<<<<<<< HEAD
=======
>>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f:src/model/duel/Filter.java
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
