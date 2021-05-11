package model.effect;

import controller.Message;

import java.util.List;

import com.google.gson.annotations.SerializedName;


/*
    effect documentaion:
    handling cases: 
    List<E> = Selective(List<E>, count);
    List<E> = Selective(List<E>, count, "message Contenet");
    List<E> = Random(List<E>, count)
    Set("key", "value"); 
    advanced case : Set(id, "key", "value")
    Get("key") //getting values from hashmap of the card
    GetGlobal("key") // getting value from hashmap of the game    
    SetGlob("key", "value");
    if(){}else{}; //TODO find matching in brackets
    inequalities;  (for example 1 > 0 || something like this)      
    
    Q_YN("question"){get yes answer}else{get no answer};
    
    
    
    Define Filter --> L_x = #Filter()#; // note that the result of the Filter is List<E>
    where L_X is list of some cards
    
    
    for required contidion, we use statement 'return false;' or 'return true;'



    obvious function to parse
    
    note in online parsing we store this data to hashmap<>


    for getting the size of List<> we uses construction:
    Norm(List<>)
    we have some general key word to use :
    {
        %Norm(my_monster)%
        %Norm(opp_monster)%
        %Norm(opp_magic)%
    }
    
    common functions: 
    {
        ChangeLP(Player, amount) :: Player {opp, own}
        ChangeZone(List<E>, targetZone)
        changeAD(List<E>, attack, defence)        
        flip(List<E>)    
    }

    some functions have their special keyWords
    for example in underAttack, we have key word attacking_card

    
    we define four type of variable in parsing : List<Integer> , Integer, CardHolder, String, Boolean

    note : when we apply some effect on some card we have to store the effect on that:
    {
        Set("can_attack", false);
        ChangeAD(200, 300);
    }
    
    when we recalculate effect we trace for special effect such as above not flip, changeZone and ..    
    so we have to store that on some hashMap<>

    i should add another key which is owner and be complemented for cardholder

*/
public class Effect {
    @SerializedName("effectType")
    private EffectType typeOfEffect;
    private String requirement;    
    private String effect;
    private boolean askForActivation;
    private String name;
    private String askAbleMessage;
    private List<String> requiredEvents;
    public String getEffectCommand()
    {
        return this.effect;
    }

}