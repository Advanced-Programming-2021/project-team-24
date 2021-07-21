package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import model.card.CardHolder;
import model.card.CardState;
import model.card.CardType;
import model.card.monster.MonsterType;

public class Filter {
    

    private Integer minLevel;
    private Integer maxLevel;
    @SerializedName("cardType")
    private CardType cardType;
    private Integer minAttack;
    private Integer maxAttack;
    private Integer minDefence;
    private Integer maxDefence;
    private List<String> cardNames;
    private List<String> idCardHolder;
    protected List<CardState> cardStates;
    private String ownerName;
    private List<String> zones;
    @SerializedName("monsterType")
    private MonsterType monsterType;

    public MonsterType getMonsterType() {
        return this.monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public List<CardState> getCardStates() {
        return this.cardStates;
        
    }

    public void setCardStates(List<CardState> cardStates) {
        this.cardStates = cardStates;
    }

    
    
    

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

    public CardType  getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Integer getMinAttack() {
        return this.minAttack;
    }

    public void setMinAttack(Integer minAttack) {
        this.minAttack = minAttack;
    }
    public static void main(String[] args) {
        Filter x = new Filter();
        x.setMaxAttack(1000);   
        System.out.println(new Gson().toJson(x));
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

    public List<String> getIdCardHolder() {
        return this.idCardHolder;
    }

    public void setIdCardHolder(List<String> idCardHolder) {
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
        this.idCardHolder = new ArrayList<String>();
        this.idCardHolder.add(String.valueOf(cardHolderId));
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
    }    
    public Filter(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public Filter() {
    }

    
}