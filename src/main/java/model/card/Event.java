package model.card;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("ATTACK")
    ATTACK,
    @SerializedName("UNDER_ATTACK")
    UNDER_ATTACK,
    @SerializedName("DEATH")
    DEATH,
    @SerializedName("FLIP")
    FLIP,
    @SerializedName("FLIP_SUMMON")
    FLIP_SUMMON,
    @SerializedName("SUMMON")
    SUMMON,
    @SerializedName("SET")
    SET;
}
