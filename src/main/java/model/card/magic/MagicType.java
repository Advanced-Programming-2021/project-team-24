package model.card.magic;

import com.google.gson.annotations.SerializedName;

public enum MagicType {
    @SerializedName("Spell")
    SPELL("Spell"),
    @SerializedName("Trap")
    TRAP("Trap");
    private String label;
    private MagicType(String label)
    {
        this.label = label;
    }
    public String getLabel()
    {
        return label.toUpperCase();        
    }
}
