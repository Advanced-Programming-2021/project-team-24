package model.card.magic;

import java.io.*;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import model.card.Card;
import model.card.CardType;
import model.card.magic.MagicType;
import model.effect.Effect;



public class MagicCard extends Card {
     
    
    private Boolean isActived;
    private Effect effect; 
    @SerializedName("magicIcon")
    private MagicIcon magicIcon;
    @SerializedName("magicType")
    private MagicType magicType;
    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    public Integer getSpeed()
    {
        return this.effect.getSpeed();
    }
    public boolean getIsActivated()
    {        
        return isActived;
    }
    public boolean isSpell()
    {
        if(super.getCardType() == CardType.SPELL)
            return true;
        else
            return false;
    }
    public static void main(String[] args) throws IOException {
        MagicCard v = new MagicCard();
        BufferedReader csvReader = v.getCsvReader("SpellTrap.csv");
        String row;
        csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",(?! )");
            v.name = data[0].replace("\"", "");
            v.magicType = MagicType.valueOf(toEnumsFormatString(data[1]));
            v.magicIcon = MagicIcon.valueOf(toEnumsFormatString(data[2]));
            v.description = data[3];
            v.limitType = LimitType.valueOf(toEnumsFormatString(data[4]));
            v.price = Integer.parseInt(data[5]);
            v.cardType = CardType.MAGIC;
            // v.effects = new HashMap<Event, String>();
            // v.effects.put(Event.ATTACK , " nothing");
            v.writeJson("Cards/MagicCards/");
            MagicCard card = new Gson().fromJson(new Gson().toJson(v), MagicCard.class);
        }
        csvReader.close();
    }

    @Override
    public String toString() {
        String ans = "";
        ans.concat("Name :" + name + "\n");
        if(isSpell())
            ans.concat("Spell\n");
        else
            ans.concat("Trap\n");
        ans.concat("Type: " + (magicType.toString()));
        ans.concat("Description:" + description);
        return ans;
    }
}