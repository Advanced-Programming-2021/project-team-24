package model.effect;

public enum RequiredEvent {
    SUMMON("summon"),
    TRIBUTE("tribute"),
    ATTACK("attack"),
    ;
    public final String label;

    private RequiredEvent(String label) {
        this.label = label;
    }
}
