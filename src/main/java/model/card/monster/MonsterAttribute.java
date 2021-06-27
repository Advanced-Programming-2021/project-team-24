package model.card.monster;

import com.google.gson.annotations.SerializedName;

public enum MonsterAttribute {
    @SerializedName("EARTH")
    EARTH("EARTH"),
    @SerializedName("WATER")
    WATER("WATER"),
    @SerializedName("DARK")
    DARK("DARK"),
    @SerializedName("FIRE")
    FIRE("FIRE"),
    @SerializedName("LIGHT")
    LIGHT("LIGHT"),
    @SerializedName("WIND")
    WIND("WIND");


    private final String label;
    private MonsterAttribute(String label)
    {
        this.label = label;
    }


    
}
