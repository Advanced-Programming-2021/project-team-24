package model.duel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;

import controller.DuelController;
import model.card.CardHolder;
import model.card.CardState;
import model.user.Player;
import model.zone.Zone;
import view.DuelMenu;
import view.Global;

public class EffectParser {    
    DuelController duelController;
    DuelMenu duelMenu;
    Player owner;
    String effect;
    int idCardHolderOwner;
    public EffectParser(DuelMenu duelMenu, DuelController duelController, Player owner, String effect, Integer idCardHolderOwner)
    {
        this.idCardHolderOwner = idCardHolderOwner;
        this.effect = effect;
        this.owner = owner;
        this.duelController = duelController;

    }    
    public void runEffect()
    {
        //effect managin;
        //first layer {}    

        //check "{}" matching then split by ";"
        //main part of effect
    }
    public void handleConditional(String command)
    {

        //if statement: if{}else{};        
    }
    public void changeZone(String command)
    {
        List<String> fields = splitCorrect(command, ',');
        Gson gson = new Gson();
        getCommandResult(fields.get(0));
        List<Integer> cardHolders = gson.fromJson(getCommandResult(fields.get(0)), new ArrayList<Integer>().getClass());            
        Zone targetZone = parseZone(fields.get(1));        
        if(fields.size() == 3)
        {
            CardState cardState = gson.fromJson(fields.get(2), CardState.class);
            duelController.getDuel().changerZone(cardHolders, targetZone, cardState);
        }
        else
        {
            duelController.getDuel().changerZone(cardHolders, targetZone, CardState.NONE);
            //TODO handle none as default case
        }
        //changeZone(List<>, target_zone, card_state);
        //parse target zone with zoneParser
        //advanced mode : changeZone(List<>, target_zone, Card_State);
        //for putting card in monster zone this part is needed
    }
    public void changeLP(String command)
    {
        //changeLp
        Matcher matcher = Global.getMatcher(command, "changeLP\\((.+)\\)");
        
        //own and opp key word
    }
    //TODO
    public String q_yn(String command)
    {
        Matcher matcher = Global.getMatcher(command, "q_yn\\(\"(.+)\"\\)(.+)");
        String queString = matcher.group(1);
        Boolean ans = duelMenu.BooleanQYN(queString);        
        List<String> ifElsePart = splitByBracket(matcher.group(2));
        if(ans)
        {
            return getCommandResult(ifElsePart.get(0));
        }
        else
        {
            return getCommand(ifElsePart.get(1));
        }
    }
    
    public void flip(String command)
    {
        //filp(List<E>): 
    }

    public String getCommandResult(String command)
    {
        // command type:
        // change zone
        //filter

        //TODO
        //return string as result of command, maybe some get or ...
        return command;
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
    public static List<String> splitCommands(String command)
    {
        List<String> ans = new ArrayList<String>();
        int pre = 0;
        int counter = 0;
        for(int i = 0; i < command.length(); i++)
        {
            if(command.charAt(i) == '{' || command.charAt(i) == '(')
                counter++;            
            if(command.charAt(i) == '}' || command.charAt(i) == ')')
                counter--;
            if(counter == 0 && command.charAt(i) == ';')
            {
                pre = i + 1;
                ans.add(command.substring(pre, i));
            }
        }
        return ans;
    }
    public static List<String> splitCorrect(String command, char ch)
    {
        List<String> list = new ArrayList<String>();
        int counter = 0;
        int pre = 0;
        for(int i = 0; i < command.length(); i++)
        {
            if(command.charAt(i) == ',' && counter == 0)
            {
                list.add(command.substring(pre, i));
                pre = i + 1;                
            }        
            if(command.charAt(i) == '(')
            {
                counter ++;
            }
            if(command.charAt(i) == ')')
            {
                counter --;
            }
        }
        list.add(command.substring(pre, command.length()));
        return list;
    }
    public static List<String> splitByBracket(String command)
    {
        List<String> ans = new ArrayList<String>();
        int pre = command.indexOf('{', 0);
        int counter = 0;
        for(int i = pre; i < command.length(); i++)
        {
            int flag = 0;
            if(command.charAt(i) == '}')
            {
                flag = 1;
                counter--;
            }
            if(command.charAt(i) == '{')
            {
                flag = 1;   
                counter++;
            }
            if(counter == 0 && flag == 1)
            {
                ans.add(command.substring(pre, i));
                pre = i + 1;
            }
        }
        return ans;
    }
    public List<Integer> getListByFilter(String filterString)    
    {
        //#Filter#(["key":"value"]);        
        List<String> list = new ArrayList<String>();
        list = splitCorrect(filterString, ',');
        //format = json
        List<String> key = new ArrayList<String>(), value = new ArrayList<String>();
        for(int i = 0; i < list.size(); i++)
        {

            Matcher mathcer = Global.getMatcher(list.get(i).replaceAll(" ", ""), "(\".+\"):\"(.+)\"");
            if(mathcer.find())
            {
                key.add(mathcer.group(1));
                value.add(getCommandResult(mathcer.group(2)));
            }
        }
        String filterJson = "{";
        for(int i = 0; i < key.size(); i++)
        {
            filterJson += key.get(i) + ":\"" + value.get(i) + "\"";
            if(i != key.size() - 1)
                filterJson += ",";                            
        }
        filterJson += "}";
        Gson gson = new Gson();;
        Filter filter =  gson.fromJson(filterJson, Filter.class);
        List<CardHolder> ans = duelController.getDuel().getCardHolderFilter(filter);        
        List<Integer> ansId = new ArrayList<Integer>();
        for(int i = 0; i < ans.size(); i++)
        {
            ansId.add(ans.get(i).getId());
        }
        return ansId;
    }
    public static void main(String[] args) {
        System.out.println(splitCorrect("aa,aa(,aa,a),b,b,()()(,)(,),", ','));
        String filter = " \"minLevel\":\"3\", \"cardType\":\"SPELL\"";
        new EffectParser(null, null, null, null, 1).getListByFilter(filter);
    }
    public Integer sumCommand(String command)
    {
        //sum(List<>, "key") e.g : sum(List<>, "level");
        return null;
    }
    public Zone parseZone(String josn){
        String[] zoneArgument = josn.split("_");
        Player player = null;
        if (zoneArgument[1].compareToIgnoreCase("my") == 0) player = owner;
        else 
        {            
            Player a1 = duelController.getDuel().getCurrentPlayer();
            Player a2 = duelController.getDuel().getOpponent();
            if(a1.getNickname().equals(owner.getNickname()))
            {
                player = a2;
            }
            else
                player = a1;
        }
        return new Zone(zoneArgument[0], player);
    }
    public List<Integer> selective(String command)
    {   
        List<String> fields = splitCorrect(command, ',');
        Gson gson = new Gson();
        List<String> array = gson.fromJson(getCommand(fields.get(0)), new ArrayList<String>().getClass());       
        fields.get(1);
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
        return duelMenu.Dice();
    }    

    public String calculater(String command)
    {
        //for multiple and sum operations
        return null;
    }
    
}
