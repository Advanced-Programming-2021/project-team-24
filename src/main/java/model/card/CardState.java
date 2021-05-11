package model.card;

import com.google.gson.annotations.SerializedName;

public enum CardState
{
  @SerializedName("SET_DEFENCE")
  SET_DEFENCE("SET_DEFENCE"),
  @SerializedName("SET_MAGIC")
  SET_MAGIC("SET_MAGIC"),
  @SerializedName("ATTACK_MONSTER")
  ATTACK_MONSTER("ATTACK_MONSTER"),
  @SerializedName("DEFENCE_MONSTER")
  DEFENCE_MONSTER("DEFENCE_MONSTER"),
  @SerializedName("SPECIAL_SUMMON")
  SPECIAL_SUMMON("SPECIAL_SUMMON"),
  @SerializedName("ACTIVE_MAGIC")
  ACTIVE_MAGIC("ACTIVE_MAGIC"),
  @SerializedName("HAND")
  HAND("HAND"),
  @SerializedName("NONE")
  NONE("NONE");
  public final String label;

    private CardState(String label) {
        this.label = label;
    }
}
