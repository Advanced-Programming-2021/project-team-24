package model.card;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("ATTACK")
    ATTACK("ATTACK"),
    @SerializedName("UNDER_ATTACK")
    UNDER_ATTACK("UNDER_ATTACK"),
    @SerializedName("DEATH")
    DEATH("DEATH"),
    @SerializedName("FLIP")
    FLIP("FLIP"),
    @SerializedName("FLIP_SUMMON")
    FLIP_SUMMON("FLIP_SUMMON"),
    @SerializedName("SUMMON")
    SUMMON("SUMMON"),
    @SerializedName("SET")
    SET("SET");
    private String label;
    private Event(String label)
    {
        this.label = label;
    }    
}
