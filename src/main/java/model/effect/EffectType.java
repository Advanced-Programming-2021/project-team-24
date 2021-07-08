package model.effect;

import com.google.gson.annotations.SerializedName;

public enum EffectType {
       @SerializedName("CONTINUES")
       CONTINUES("CONTINUES"),
       @SerializedName("NORMAL")
       NORMAL("NORMAL"),
       @SerializedName("RITUAL")
       RITUAL("RITUAL"),
       @SerializedName("COUNTER")
       COUNTER("COUNTER"),
       @SerializedName("EQUIP")
       EQUIP("EQUIP"),
       @SerializedName("QUICK_PLAY")
       QUICK_PLAY("QUICK_PLAY"),
       @SerializedName("FIELD")
       FIELD("FIELD") ;
       public final String label;

    private EffectType(String label) {
        this.label = label;
    }
}
