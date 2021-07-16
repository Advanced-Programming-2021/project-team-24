package model.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import org.jboss.msc.value.MapEntry;

import model.card.monster.MonsterCard;

public class CardCreator {
    int attack = 0;
    int defence = 0;
    int level = 0;
    List<String> combineEffect = new ArrayList<>();
    HashMap<Event,String> effects = new HashMap<>();
    public CardCreator()
    {        
    }
    public int calculatePrice()
    {
        return (int)(((int)(attack + defence)/2) * ((int)(attack + defence)/2)/2 + combineEffect.size() * combineEffect.size() * 500);
    }
    public void setAttack(int attack)
    {
        this.attack = attack;
    }
    public void setDefence(int defence){
        this.defence = defence;
    }
    public void loadCombineEffect(String effects)
    {
        if(effects == null || effects.length() == 0)
            return ;
        String cardName[] = effects.split("|");
        List<String> ans = new ArrayList<>();
        for(int i = 0; i < cardName.length; i++)
        {
            if(Card.getCardByName(cardName[i]) != null)
            {
                ans.add(cardName[i]);

            }
        }   
        
    }
    public Card generateCard()
    {
        MonsterCard card = new MonsterCard();
        card.setAttack(attack);
        card.setDefence(defence);
        card.setLevel(level);
        for(int i = 0; i < combineEffect.size(); i++)
        {
            Card x = Card.getCardByName(combineEffect.get(i));
            for(Map.Entry<Event,String> maEntry : x.getEffects().entrySet())
            {
                if(x.getEffects().get(maEntry.getKey()) == null)
                {
                    x.getEffects().put(maEntry.getKey(), "");                    
                }
                if(x.getEffects().get(maEntry.getKey()) != null)
                    card.getEffects().put(maEntry.getKey(), card.getEffects().get(maEntry.getKey()) + x.getEffects().get(maEntry.getKey()));
            }
        }
        if(this.effects.entrySet().size() > 0)
        {
            card.setEffects(this.effects);
        }
        return card;

    }
    public void setEffectHashMap(String effects)
    {
        try{
        this.effects = new Gson().fromJson(effects, new HashMap<Event,String>().getClass());
        }catch(Exception e){
            
        }
    }
}
