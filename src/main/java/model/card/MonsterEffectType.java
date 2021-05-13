package model.card;

import com.google.gson.annotations.SerializedName;

public enum MonsterEffectType {
    @SerializedName("Normal")
    NORMAL("Normal"),
    @SerializedName("Effect")
    EFFECT("Effect");
    private String label;
    private MonsterEffectType(String label)
    {
        this.label = label;
    }
    
}
