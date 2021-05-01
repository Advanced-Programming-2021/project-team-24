package model.effect;

import java.util.List;

public abstract class FundamentalEffect {
    private List<String> requiredPhase;
    private List<String> requiredEvent;
    private String requiredString;
    public boolean isConditionsSatisfied() {
        return true;
    }
    
}
