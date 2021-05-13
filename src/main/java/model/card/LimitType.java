package model.card;

import com.google.gson.annotations.SerializedName;

public enum LimitType {
    @SerializedName("Limited")
    LIMITED("Limited"),
    @SerializedName("Unlimited")
    UNLIMITED("Unlimited");
    private String label;
    private LimitType(String label)
    {
        this.label = label;
    }
}
