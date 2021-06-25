package model.effect;

import controller.Message;
import model.card.Event;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
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
    private EffectType effectType;
    private String requirement; //BOOLEAN FUNCTION
    private String effect;
    private Boolean askForActivation;
    private String name;
    private String reverse;
    private String askAbleMessage;        
    private List<Event> requiredEvents;
    private Integer speed;
    public void setRequiredEvent(List<Event> event)    
    {
        this.requiredEvents = event;
    }
    public Effect(String effect)    
    {
        this.effect = effect;
        askForActivation = false;
        name = "";
        reverse = "";
        requiredEvents = new ArrayList<Event>();
        speed = 1;
        askAbleMessage = "";
        requirement = "return_t";        
    }    
    public Integer getSpeed() {
        return this.speed;
    }

    public void setAskForActivation(boolean askForActivation)
    {
        this.askForActivation = askForActivation;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    public void setEffectType(EffectType effectType)
    {
        this.effectType = effectType;
    }
    public void setReverse(String reverse)
    {
        this.reverse = reverse;
    }
    public String getReverse()
    {
        return this.reverse;
    }
    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    public EffectType getEffectType()
    {
        return this.effectType;
    }
    public List<Event> getRequireEvents()
    {
        return this.requiredEvents;
    }
    public String getRequirementCommandString()
    {
        return this.requirement;
    }
    public void setRequirementString(String required)
    {
        this.requirement = required;
    }

    public String getName()
    {
        return this.name;
    }
    public String getAskableMessage()
    {
        return this.askAbleMessage;
    }

    /* public List<String> getRequirementEvent()
    {
        return this.requiredEvents;
    } */


    
    public Boolean getAskForActivation()
    {
        return askForActivation;
    }    

    public String getEffectCommand()
    {
        return this.effect;
    }
    public Effect clone()
    {
        Effect temp = new Effect("");
        Gson gson = new Gson();
        temp = gson.fromJson(gson.toJson(this), this.getClass());
        return temp;
    }

}