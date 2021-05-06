package model.effect;

import model.user.Player;

import java.util.List;

public class EffectManger {
    private Player owner;
    private int id;
    private static int idCounter = 0;
    private int idCardHolder;
    private boolean isActivated;
    private int ageRemained;
    private int remainedToApplyEffect;
    private Effect effect;
    private static List<EffectManger> effectMangerList;
    public EffectManger(Effect effect){
        idCounter++;
        this.id = idCounter;
        this.effect = effect;
        effectMangerList.add(this);
    }
    public static List<EffectManger> getEffectMangerList(){
        return effectMangerList;
    }
    public static EffectManger getEffectManagerById(int id){
        for (int i = 0; i < effectMangerList.size(); i++) {
            if (effectMangerList.get(i).id == id) return effectMangerList.get(i);
        }
        return null;
    }
    public boolean satisfyRequired(){

    }
    
}
