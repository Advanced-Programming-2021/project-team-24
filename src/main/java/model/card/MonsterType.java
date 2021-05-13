package model.card;

import com.google.gson.annotations.SerializedName;

public enum MonsterType {
    @SerializedName("Beast-Warrior")
    BEAST_WARRIOR("Beast-Warrior"),
    @SerializedName("Warrior")
    WARRIOR("Warrior"),
    @SerializedName("Aqua")
    AQUA("Aqua"),
    @SerializedName("Fiend")
    FIEND("Fiend"),
    @SerializedName("Beast")
    BEAST("Beast"),
    @SerializedName("Pyro")
    PYRO("Pyro"),
    @SerializedName("Spellcaster")
    SPELLCASTER("Spellcaster"),
    @SerializedName("Thunder")
    THUNDER("Thunder"),
    @SerializedName("Dragon")
    DRAGON("Dragon"),
    @SerializedName("Machine")
    MACHINE("Machine"),
    @SerializedName("Rock")
    ROCK("Rock"),
    @SerializedName("Cyberse")
    CYBERSE("Cyberse"),
    @SerializedName("Fairy")
    FAIRY("Fairy"),
    @SerializedName("Sea-Serpent")
    SEA_SERPENT("Sea-Serpent"),
    @SerializedName("Insect")
    INSECT("Insect")
    ;

    private final String label;
    private MonsterType(String label)
    {
        this.label = label;
    }
}
