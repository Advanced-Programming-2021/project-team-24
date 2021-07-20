package model.card.magic;

import com.google.gson.annotations.SerializedName;

public enum MagicIcon {
    @SerializedName("Normal")
    NORMAL("Normal"),
    @SerializedName("Counter")
    COUNTER("Counter"),
    @SerializedName("Continuous")
    CONTINUOUS("Continuous"),
    @SerializedName("Quick-Play")
    QUICK_PLAY("Quick-Play"),
    @SerializedName("Field")
    FIELD("Field"),
    @SerializedName("Equip")
    EQUIP("Equip"),
    @SerializedName("Ritual")
    RITUAL("Ritual");
    private String label;
    private MagicIcon(String label)
    {
        this.label = label;
    }
    
}
