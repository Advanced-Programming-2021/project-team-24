package model.effect;

import java.util.List;

public abstract class FundamentalEffect {
    private List<String> requiredPhase;
    private List<String> requiredEvent;
    private String requiredString;    
    public abstract boolean isConditionsSatisfied();
    public abstract void runEffect();           
}
