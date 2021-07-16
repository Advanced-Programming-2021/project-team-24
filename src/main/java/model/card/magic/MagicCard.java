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
     
    
    private Effect effect; 
    @SerializedName("magicIcon")
    private MagicIcon magicIcon;
    @SerializedName("magicType")
    private MagicType magicType;


    
    public MagicType getMagicType() {
        return this.magicType;
    }

    
    public MagicIcon getMagicIcon() {
        return this.magicIcon;
    }

    public void setMagicIcon(MagicIcon magicIcon) {
        this.magicIcon = magicIcon;
    }
    public void setMagicType(MagicType magicType) {
        this.magicType = magicType;
    }

    public Effect getEffect() {
        if(this.effect != null)
        {
            return this.effect;
        }
        else
        {
            return new Effect("");
        }
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    public Integer getSpeed()
    {
        if(effect.getSpeed() == null)
            return 1;
        return this.effect.getSpeed();
    }
    public boolean isSpell()
    {
        if(super.getCardType() == CardType.MAGIC){
            return magicType == MagicType.SPELL;
        }
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
        ans = ans.concat("Name :" + name + "\n");
        if(isSpell())
            ans = ans.concat("Spell\n");
        else
             ans = ans.concat("Trap\n");
        ans = ans.concat("Type: " + (limitType.toString()) + "\n");
        ans = ans.concat("Description:" + description + "\n");
        return ans;
    }
}