package model.card;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("ANY")
    ANY("ANY"),
    @SerializedName("ATTACK_OWNER")
    ATTACK_OWNER("ATTACK_OWNER"),
    @SerializedName("UNDER_ATTACK_OWNER")
    UNDER_ATTACK_OWNER("UNDER_ATTACK_OWNER"),
    @SerializedName("DEATH_OWNER")
    DEATH_OWNER("DEATH_OWNER"),
    @SerializedName("FLIP_OWNER")
    FLIP_OWNER("FLIP_OWNER"),
    @SerializedName("FLIP_SUMMON_OWNER")
    FLIP_SUMMON_OWNER("FLIP_SUMMON_OWNER"),

    @SerializedName("SET_OWNER")
    SET_OWNER("SET_OWNER"),
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
    SET("SET"),
    @SerializedName("ACTIVE_SPELL")
    ACTIVE_SPELL("ACTIVE_SPELL"),
    @SerializedName("REVERSE")
    REVERSE("REVERSE"),
    @SerializedName("END_TURN")
    END_TURN("END_TURN"),
    @SerializedName("SUMMON_OWNER")
    SUMMON_OWNER("SUMMON_OWNER");
    ;
    private String label;
    private Event(String label)
    {
        this.label = label;
    }    
    public String getValue()
    {
        return this.label;
    }
}