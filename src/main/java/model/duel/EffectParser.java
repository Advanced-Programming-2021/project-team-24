package model.duel;

import java.util.List;

import model.user.Player;

public class EffectParser {
    Duel duel;
    Player owner;
    String effect;
    int idCardHolderOwner;
    public EffectParser(Duel duel, Player owner, String effect, int idCardHolderOwner)
    {
        this.idCardHolderOwner = idCardHolderOwner;
        this.effect = effect;
        this.owner = owner;
        this.duel = duel;
    }    
    public void runEffect()
    {
        //check "{}" matching then split by ";"
        //main part of effect
    }
    public void handleConditional(String command)
    {
        //if statement: if{}else{};        
    }
    public void changeZone(String command)
    {
        //changeZone(List<>, target_zone);
        //parse target zone with zoneParser
        //advanced mode : changeZone(List<>, target_zone, Card_State);
        //for putting card in monster zone this part is needed
    }
    public void changeLP(String command)
    {
        //own and opp key word
    }
    
    public void q_yn(String command)
    {
        //handle view part
        // q_yn("message"){}else{};
    }
    
    public void flip(String command)
    {
        //filp(List<E>): 
    }


    public String getResult(String command)
    {
        //TODO
        //return string as result of command, maybe some get or ...
        return null;
    }
    
    public String normSet(String command)
    {
        //Norm(List<E>): return size of List in String integer        
        return null;
    }
    public String parseKeyWords(String keyWord)
    {
        //
        return null;
    }
    public List<Integer> parseKeyWordsList(String keyWord)
    {
        //this and attacker and ...
        return null;
    }

    public void setCommand(String setCommand)
    {
        //set("List<>" , "key" , "value");
    }
    public String getCommand(String getCommand)
    {
        //get("List<>", "key")
        // for simplify assume the first part of list as getting                
        return null;
    }
    public List<Integer> getListByFilter(String filterString)    
    {
        //Filter(["key":"value"]);
        return null;
    }
    public Integer sumCommand(String command)
    {
        //sum(List<>, "key") e.g : sum(List<>, "level");
        return null;
    }
    public List<Integer> selective(String command)
    {   
        // selective(List<E> , "count", "message")
        //TODO handle view part for this
        return null;
    }
    public List<Integer> randomSelection(String command)
    {
        // random()
        return null;
    }
    public Integer dice()
    {
        //handle the view part use : dice
        return null;
    }    

    public String calculater(String command)
    {
        //for multiple and sum operations
        return null;
    }
    
}
