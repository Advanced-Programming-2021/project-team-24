package model.effect;

import controller.Message;

import java.util.List;

public class Effect {
    private EffectType typeOfEffect;
    private String requirement;
    private boolean askForActivation;
    private String name;
    private String askAbleMessage;
    private List<String> requiredEvents;
}
