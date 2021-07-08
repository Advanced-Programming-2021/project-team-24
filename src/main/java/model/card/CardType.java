package model.card;

import com.google.gson.annotations.SerializedName;

public enum CardType {
    @SerializedName("TRAP")
    TRAP("TRAP"),
    @SerializedName("MONSTER")
    MONSTER("MONSTER"),
    @SerializedName("MAGIC")
    MAGIC("MAGIC"),
    @SerializedName("SPELL")
    SPELL("SPELL");
    public final String label;

    private CardType(String label) {
        this.label = label;
    }

}
