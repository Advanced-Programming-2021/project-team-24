package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import controller.DuelController;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.CardType;
import model.effect.Effect;
import model.user.Player;
import model.zone.Zone;
import view.DuelMenu;
import view.Global;


public class EffectParser {    
    DuelController duelController;
    DuelMenu duelMenu;
    Player owner;
    Effect effect;
    int idCardHolderOwner;
    private HashMap<String, String> extraKeyWords;    
    public DuelController getDuelController()
    {
        return this.duelController;
    }
    public EffectParser(DuelMenu duelMenu, DuelController duelController, EffectManager effectManager)
    {
        
        this.extraKeyWords = effectManager.getExtraKeyWords();
        this.idCardHolderOwner = effectManager.getOwnerCardHolderId();
        this.effect = effectManager.getEffect();
        this.owner = this.effectManager.getOwner();        
        this.duelController = duelController;
    }    
    String ans;
    public String runEffect()
    {
        ans = null;
        getCommandResult(effect.getEffectCommand());
        return ans;
    }
    public String handleConditional(String command)
    {
        Matcher matcher = Global.getMatcher(command, "if\\(#(.+)#[<>]#(.+)#\\){(.+)}");
        matcher.find();
        String s1 = matcher.group(1);
        String s2 = matcher.group(2);
        if(s1.compareTo(s2) > 0)
        {
            return getCommandResult(splitByBracket(matcher.group(3)).get(0));
        }
        else
        {
            return getCommandResult(splitByBracket(matcher.group(3)).get(1));
        }
        // if(#statement# [<>] #statement#)        
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
        Matcher matcher = Global.getMatcher(command, "changeLP\\((.+),(.+)\\)");
        matcher.find();
        String player = matcher.group(1);
        if(player.equals("own"))
        {
            owner.changeLifePoint(Integer.parseInt(getCommandResult(matcher.group(2))));
        }
        else
        {
            duelController.getDuel().opponent.changeLifePoint(Integer.parseInt(getCommandResult(command)));
        }        
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
        String lString = splitByParentheses(command).get(0);
        List<Integer> list = new Gson().fromJson(getCommandResult(lString) , new ArrayList<Integer>().getClass());
        for(int i = 0; i < list.size(); i++)
        {
            duelController.getDuel().getCardHolderById(list.get(i)).flip();
        }

    }

    public static final String GET_STRING = "get\\(([^()]*)\\)";
    public String getCommandResult(String command)
    {
        if(command.lastIndexOf(';') == command.length())
        {
            //check get
            for(int i = 0; i < 4; i++)
            {
                if(command.substring(0, 8).equals("return_t") && ans != null)
                {
                    ans = "true";
                }
                if(command.substring(0, 8).equals("return_f") && ans != null)
                {
                    ans = "false";
                }
                
                handleGetCommand(command);
                if(command.substring(0, 2).equals("if"))
                {
                    return handleConditional(command);
                }
                if(command.substring(0, 4).equals("q_yn"))
                {
                    return q_yn(command);
                }
                if(command.substring(0, 3).equals("set"))
                {
                    setCommand(command);
                }
                handleChangeLPCommand(command);
                handleNormCommand(command);
                command = parseKeyWords(command);
                if(command.substring(0, 3).equals("del"))
                {
                    deleteListFromList(command);
                }
                if(command.substring(0, 3).equals("sum"))
                {
                    getSumOverField(command);
                }
                //calculater            
            }
            return command;        
        }
        else
        {
            List<String> subCommands = splitCommands(command);
            for(int i = 0; i < subCommands.size(); i++)
            {
                getCommandResult(subCommands.get(i));
            }

        }        
        // command type:
        // change zone
        //filter
        //TODO
        //return string as result of command, maybe some get or ...
        return command;
    }
    private void handleChangeLPCommand(String command) {
        try
        {
            if(command.substring(0, 8).equals("changeLP"))
            {
                changeLP(command);
            }
        }
        catch(Exception e)
        {                
        }
    }
    private void handleNormCommand(String command) {
        while(true)
        {
            if(Global.regexFind(command, "Norm\\(([^()]+)\\)"))
            {
                Matcher matcher = Global.getMatcher(command, "Norm\\(([^()]+)\\)");
                command.replace(matcher.group(0), normSet(matcher.group(0)));
            }
            else
                break;
        }
    }
    private void handleGetCommand(String command) {
        command.replace(" ", "");
        while(true)
        {
            if(Global.regexFind(command, GET_STRING))
            {
                Matcher matcher = Global.getMatcher(command, GET_STRING);
                command.replace(matcher.group(0), getCommand(matcher.group(0)));
            }
            else
                break;
        }
    }
    
    public String normSet(String command)
    {

        //Norm(List<E>): return size of List in String integer
        //List<E> set
        Matcher matcher = Global.getMatcher(command, "Norm\\((.+)\\)");
        matcher.find();      
        String list = getCommandResult(matcher.group(1));        
        return String.valueOf((new Gson().fromJson(list, new ArrayList<String>().getClass())).size());        
    }
    public String parseKeyWords(String command)
    {
        //this
        List<String> v = new ArrayList<String>();
        v.add(Integer.toString(this.idCardHolderOwner));
        command = command.replace("this", new Gson().toJson(v, new ArrayList<String>().getClass()));
        
        //simple zones
        
        Player current = this.owner;
        Player opponent = duelController.getDuel().getOpponent();
        if(opponent.getNickname().equals(owner.getNickname()))
            opponent = duelController.getDuel().getCurrentPlayer();

        for (String string : model.zone.Zone.zoneStrings) {
            String zone = "$my_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(new Zone(string, current));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }
            command = command.replaceAll(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }
        
        for (String string : model.zone.Zone.zoneStrings) {
            String zone = "$opp_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(new Zone(string, opponent));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }
            command = command.replaceAll(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }



        return command;
    }
    public List<Integer> parseKeyWordsList(String keyWord)
    {
        //this and attacker and ...
        return null;
    }

    public void setCommand(String setCommand)
    {
        Gson gson = new Gson();
        List<String> fields = splitCorrect(splitByParentheses(setCommand).get(0) ,',');
        List<String> cardHolders = gson.fromJson(getCommandResult(fields.get(0)), new ArrayList<String>().getClass());
        String key = fields.get(1);
        String value = getCommandResult(fields.get(2));
        
        //set("List<>" , "key" , "value");
    }
    public String getCommand(String getCommand)
    {
        List<String> fields = splitCorrect(splitByParentheses(getCommand).get(0), ',');
        List<Integer> cardHolders = new Gson().fromJson(getCommandResult(fields.get(0)), new ArrayList<Integer>().getClass());
        return duelController.getDuel().getterMap(cardHolders, fields.get(1), getCommandResult(fields.get(2)));        
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
    public static List<String> splitByParentheses(String command)
    {
        List<String> ans = new ArrayList<String>();
        int pre = command.indexOf('(', 0);
        int counter = 0;
        for(int i = pre; i < command.length(); i++)
        {
            int flag = 0;
            if(command.charAt(i) == ')')
            {
                flag = 1;
                counter--;
            }
            if(command.charAt(i) == '(')
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
    public List<String> deleteListFromList(String command)
    {
        //del(List<>, List<E>)
        Matcher matcher = Global.getMatcher(command, "del\\((.+)\\)");
        List<String> ans = new ArrayList<String>();
        if(matcher.find())
        {
            List<String> sets = splitCorrect(matcher.group(1), ',');
            List<Integer> first = new Gson().fromJson(getCommandResult(sets.get(1)), new ArrayList<Integer>().getClass());
            List<Integer> second = new Gson().fromJson(getCommandResult(sets.get(2)), new ArrayList<Integer>().getClass());            
            for(int i = 0; i < first.size(); i++)
            {   
                int flag = 0;
                for(int j = 0; j < second.size(); j++)
                {
                    if(first.get(i) == second.get(j))
                    {
                        flag = 1;
                    }
                }
                if(flag == 0)
                {
                    ans.add(Integer.toString(first.get(i)));
                }
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
    public void getSumOverField(String command)
    {
        //sum(List<E>, "field");
        Matcher matcher = Global.getMatcher(command, "sum\\((.+)\\)");
        Integer ans = 0;
        if(matcher.find())
        {
            List<String> fields  = splitCorrect(command, ',');
            List<String> list = new Gson().fromJson(getCommandResult(fields.get(0)), new ArrayList<String>().getClass());
            String value = getCommand(fields.get(1));
            for(int i = 0; i < list.size(); i++)
            {
                Card card = duelController.getDuel().getCardHolderById(Integer.parseInt(list.get(i))).getCard();
                if(card.getCardType() == CardType.MONSTER)
                {
                    ans += Integer.parseInt(duelController.getDuel().getCardHolderById(Integer.parseInt(list.get(i))).getValue(value));
                }
                
            }
            //command.replace(matcher.group(0), ans.toString(i));
        }
    }
    public static void main(String[] args) {
        System.out.println(splitCorrect("aa,aa(,aa,a),b,b,()()(,)(,),", ','));
        String filter = " \"minLevel\":\"3\", \"cardType\":\"SPELL\"";
        new EffectParser(null, null , null).getListByFilter(filter);
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
        List<Integer> array = gson.fromJson(getCommand(fields.get(0)), new ArrayList<Integer>().getClass());       
        int count = Integer.parseInt(fields.get(1));    
        List<Integer> selected = duelMenu.selective(array, count, fields.get(2));
        return selected;
    }
    public List<Integer> randomSelection(String command)
    {
        List<String> fields = splitCorrect(command, ',');
        Gson gson = new Gson();
        List<Integer> array = gson.fromJson(getCommand(fields.get(0)), new ArrayList<Integer>().getClass());       
        int count = Integer.parseInt(fields.get(1));    
        List<Integer> selected = duelMenu.randomSelection(array, count, fields.get(2));
        return selected;
    }
    public Integer dice()
    {
        //handle the view part use : dice
        return duelMenu.Dice();
    }    

    public int calculater(String command)
    {
        String[] operators = {"\\*", "\\+", "-", "/"};
        String operator = null;
        int returnNumber = 0;
        for (int i = 0; i < 4; i++) {
            Matcher matcher = Pattern.compile(operators[i]).matcher(command);
            if (matcher.find()) operator = operators[i];
        }
        if (operator == null) returnNumber = Integer.parseInt(command);
        else {
            String[] stringOfNumbers = command.split(" " + operator + " ");
            int firstNumber = Integer.parseInt(stringOfNumbers[0]);
            int secondNumber = Integer.parseInt(stringOfNumbers[1]);
            switch (operator){
                case "*":
                    returnNumber = firstNumber*secondNumber;
                case "+":
                    returnNumber = firstNumber+secondNumber;
                case "-":
                    returnNumber = firstNumber-secondNumber;
                case "/":
                    returnNumber = firstNumber/secondNumber;
            }
        }
        return returnNumber;
    }
    
}
