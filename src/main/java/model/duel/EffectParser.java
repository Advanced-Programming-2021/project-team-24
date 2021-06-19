package model.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import controller.DuelController;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.CardType;
import model.card.Event;
import model.effect.Effect;
import model.effect.EffectManager;
import model.effect.EffectType;
import model.user.Player;
import model.zone.Zone;
import view.DuelMenu;
import view.Global;


public class EffectParser {    
    DuelController duelController;
    DuelMenu duelMenu;
    Player owner;
    EffectManager effectManager;
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
        this.effectManager = effectManager;
        this.owner = this.effectManager.getOwner();  
        this.duelMenu = duelMenu;      
        this.duelController = duelController;
        if(effectManager.getEffect().getEffectType() == EffectType.QUICK_PLAY)
        {
            //add reverse to the player
            //clone
            //ADD  effect hashMap to the hashMap of player
            Effect reverseEffect = effect;
            reverseEffect.setEffect(effect.getReverse());
            reverseEffect.setReverse(null);
            if(owner.getMap().getEffects().get(Event.END_TURN) == null)
            {
                owner.getMap().getEffects().put(Event.END_TURN, new ArrayList<EffectManager>());
            }
            for(HashMap.Entry entry : effectManager.getExtraKeyWords().entrySet())
            {
                //add value to original hashMap
                owner.getMap().getCardMap().put((String)entry.getKey(), (String)entry.getValue());
            }
            EffectManager deathEffectManager = new EffectManager(reverseEffect, owner, owner.getMap().getId());
            owner.getMap().getEffects().get(Event.END_TURN).add(deathEffectManager);
        }
        else
        {
            //add death event as reverse
            //TODO
            Effect reverseEffect = effect.clone();//TODO it is wrong
            reverseEffect.setEffect(effect.getReverse());
            reverseEffect.setReverse(null);
            if(owner.getMap().getEffects().get(Event.END_TURN) == null)
            {
                owner.getMap().getEffects().put(Event.END_TURN, new ArrayList<EffectManager>());
            }
            EffectManager deathEffectManager = new EffectManager(reverseEffect, owner, effectManager.getOwnerCardHolderId());
            if(duelController.getDuel().getCardHolderById(effectManager.getOwnerCardHolderId()).getEffects().get(Event.DEATH_OWNER) == null)
            {
                duelController.getDuel().getCardHolderById(effectManager.getOwnerCardHolderId()).getEffects().put(Event.DEATH_OWNER , new ArrayList<EffectManager>());
            }
            duelController.getDuel().getCardHolderById(effectManager.getOwnerCardHolderId()).getEffects().get(Event.DEATH_OWNER).add(deathEffectManager);
        }
    }    
    String ans;
    public String runEffect()
    {
        ans = null;
        getCommandResult
        (effect.getEffectCommand()
        );
        return ans;
    }
    public Player getOwner()
    {
        return owner;
    }
    public void setExtraKeyWord(String key, String value)
    {
        extraKeyWords.put(key, value);
    }

    public String handleConditional(String command)
    {
        Matcher matcher = Global.getMatcher(command, "if\\(#(.+)#[<>]#(.+)#\\)(&.+&)");
        matcher.find();
        String s1 = getCommandResult(matcher.group(1));
        String s2 = getCommandResult(matcher.group(2));
        if(s1.compareTo(s2) > 0)
        {
            return getCommandResult(splitCorrect(matcher.group(3), '&').get(1));
        }
        else
        {
            return getCommandResult(splitCorrect(matcher.group(3), '&').get(3));
        }      
    }
    public void changeZone(String command)
    {
        List<String> fields = splitCorrect(command, ',');
        Gson gson = new Gson();
        getCommandResult(fields.get(0));
        List<Integer> cardHolders = getArray(fields.get(0));
        Zone targetZone = null;//parseZone(fields.get(1));        
        for(int i = 0; i < cardHolders.size(); i++)
        {
            targetZone = parseZone(fields.get(1), cardHolders.get(i));
            if(fields.size() == 3)
            {            
                
                CardState cardState = gson.fromJson(fields.get(2), CardState.class);
                duelController.getDuel().changerZone(cardHolders, targetZone, cardState);
            }
            else
            {
                duelController.getDuel().changerZone(cardHolders, targetZone, CardState.NONE);                
            }
        }
        //changeZone(List<>, target_zone, card_state);
        //parse target zone with zoneParser
        //advanced mode : changeZone(List<>, target_zone, Card_State);
        //for putting card in monster zone this part is needed
    }
    public void addEffectCommand(String command)
    {   
        //add(List<E> ,Event, Effect);
        Matcher matcher = Global.getMatcher("add", "add\\((.+)\\)");
        if(matcher.find())
        {
            List<String> arguemnts = splitCorrect(matcher.group(1), ',');
            List<Integer> card = getArray(arguemnts.get(0));
            Event event = new Gson().fromJson(arguemnts.get(1), Event.class);
            Effect effect = new Gson().fromJson(arguemnts.get(2), Effect.class);            
        }
    }
    public String changeLP(String command)
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
            duelController.getDuel().opponent.changeLifePoint(Integer.parseInt(getCommandResult(matcher.group(2))));
        }        
        String result = matcher.group(0);
        command = command.replace(result, "");
        return command;        
    }
    public String q_yn(String command)
    {
        Matcher matcher = Global.getMatcher(command, "q_yn\\(([^{}]+)\\)(.+)");
        matcher.find();
        String queString = matcher.group(1);
        Boolean ans = duelMenu.BooleanQYN(queString);        
        List<String> ifElsePart = splitByBracket(matcher.group(2));
        if(ans)
        {
            return getCommandResult(ifElsePart.get(0));
        }
        else
        {
            return getCommandResult(ifElsePart.get(1));
        }
    }
    
    public void flip(String command)
    {
        //filp(List<E>): 
        String lString = splitByParentheses(command).get(0);
        List<Integer> list = getArray(lString);
        for(int i = 0; i < list.size(); i++)
        {
            duelController.getDuel().getCardHolderById(list.get(i)).flip();
        }

    }
    
    public void coin(String command)
    {
        Matcher matcher = Global.getMatcher(command, "coin()");
        if(matcher.find())
        {
            Integer coin = duelMenu.coin();
            command = command.replace("coin()", coin.toString());
        }        

    }


    public static final String GET_STRING = "get\\(([^()]*)\\)";
    public String getCommandResult(String command)
    {
        if(command.lastIndexOf(';') == -1)
        {
            command = command.replace(" ", "");
            //check get
            for(int i = 0; i < 4; i++)
            {
                command = parseKeyWords(command);
                if(command.length() >= 8 && command.substring(0, 8).equals("return_t") && ans == null)
                {
                    ans = "true";
                    return "true";
                }
                if(command.length() >= new String("select").length() && command.substring(0, 6).equals("select"))
                {
                    command = selective(command);
                }
                //TODO random_selection
                if(command.length() >= new String("random_selection").length() && command.substring(0, 16).equals("random_selection"))
                {
                    command = randomSelection(command);
                }
                if(command.length() >= new String("changeZone").length() && command.substring(0, new String("changeZone").length()).equals("changeZone"))
                {
                    changeZone(command);
                }

                if(command.length() >= 8 && command.substring(0, 8).equals("return_f") && ans == null)
                {
                    ans = "false";
                    return "false";
                }
                if(Global.regexFind(command, "filter"))
                {
                    //TODO
                }
                if(Global.regexFind(command ,"coin"))
                {
                    coin(command);
                }
                command = handleGetCommand(command);
                if(command.length() >= 2 && command.substring(0, 2).equals("if"))
                {
                    return handleConditional(command);
                }
                if(command.length() >= 4 && command.substring(0, 4).equals("q_yn"))
                {
                    return q_yn(command);
                }
                if(command.length() >= 3 && command.substring(0, 3).equals("set"))
                {
                    setCommand(command);
                }
                command = handleChangeLPCommand(command);
                command = handleNormCommand(command);
                
                if(command.length() >= 3 && command.substring(0, 3).equals("del"))
                {
                    deleteListFromList(command);
                }
                if(command.length() >= 3 && command.substring(0, 3).equals("sum"))
                {
                    command = getSumOverField(command);
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
    private String handleChangeLPCommand(String command) {
        try
        {
            if(command.substring(0, 8).equals("changeLP"))
            {
                command = changeLP(command);                
            }
        }
        catch(Exception e)
        {    

        }
        return command;
    }
    private String handleNormCommand(String command) {
        while(true)
        {
            if(Global.regexFind(command, "Norm\\(([^()]+)\\)"))
            {
                Matcher matcher = Global.getMatcher(command, "Norm\\(([^()]+)\\)");
                matcher.find();
                command = command.replace(matcher.group(0), normSet((matcher.group(0))));
            }
            else
                break;
        }
        return command;
    }
    private String handleGetCommand(String command) {
        command = command.replace(" ", "");
        while(true)
        {
            if(Global.regexFind(command, GET_STRING))
            {
                Matcher matcher = Global.getMatcher(command, GET_STRING);
                matcher.find();
                command = command.replace(matcher.group(0), getCommand(matcher.group(0)));
            }
            else
                break;
        }
        return command;
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
        command = command.replace("this", new Gson().toJson(v, new ArrayList<Integer>().getClass()));
        
        //simple zones
        
        Player current = this.owner;
        Player opponent = duelController.getDuel().getOpponent();
        if(opponent.getNickname().equals(owner.getNickname()))
            opponent = duelController.getDuel().getCurrentPlayer();

        for (String string : model.zone.Zone.zoneStrings) {
            String zone = "$my_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(Zone.get(string, current));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }        
            command = command.replace(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }
        
        for (String string : model.zone.Zone.zoneStrings) {
            String zone = "$opp_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(Zone.get(string, opponent));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }
            command = command.replace(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }

        

        return command;
    }
    public static List<Integer> convertToInteger(List<String> stringNumbers)
    {
        List<Integer> temp = new ArrayList<Integer>();        
        for(String string: stringNumbers)
        {
            temp.add(Integer.parseInt(string));
        }
        return temp;
    }
    public List<Integer> getArray(String array)
    {
        Gson gson = new Gson();
        return convertToInteger(gson.fromJson(getCommandResult(getCommandResult(array)), new ArrayList<String>().getClass()));
    }
    public void setCommand(String setCommand)
    {
        Gson gson = new Gson();
        List<String> fields = splitCorrect(splitByParentheses(setCommand).get(0) ,',');
        List<Integer> cardHolders = getArray(fields.get(0));
        String key = fields.get(1);
        String value = getCommandResult(fields.get(2));
        duelController.getDuel().setterMap(cardHolders, key, value, 1);//TODO
        //set("List<>" , "key" , "value"):rev(List<E>, "key", value);
    }
    public String getCommand(String getCommand)
    {
        List<String> fields = splitCorrect(splitByParentheses(getCommand).get(0), ',');
        List<Integer> cardHolders = getArray(getCommandResult(fields.get(0)));
        return duelController.getDuel().getterMap(cardHolders, fields.get(1));        
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
                ans.add(command.substring(pre, i));
                pre = i + 1;
            }
        }
        if(pre < command.length())
            ans.add(command.substring(pre, command.length()));
        return ans;
    }
    public static List<String> splitCorrect(String command, char ch)
    {
        List<String> list = new ArrayList<String>();
        int counter = 0;
        int pre = 0;
        for(int i = 0; i < command.length(); i++)
        {
            if(command.charAt(i) == ch && counter == 0)
            {
                list.add(command.substring(pre, i));
                pre = i + 1;                
            }        
            if(command.charAt(i) == '(' || command.charAt(i) == '[')
            {
                counter ++;
            }
            if(command.charAt(i) == ')' || command.charAt(i) == ']')
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
                if(counter == 0)
                    pre = i + 1;
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
        if(pre > command.length() || pre < 0)
        {
            ans.add(command);
            return ans;
        }
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
                ans.add(command.substring(pre + 1, i));                
                pre = i + 1;
            }
        }
        return ans;
    }
    public List<Integer> deleteListFromList(String command)
    {
        //del(List<>, List<E>)
        Matcher matcher = Global.getMatcher(command, "del\\((.+)\\)");
        List<Integer> ans = new ArrayList<Integer>();
        if(matcher.find())
        {
            List<String> sets = splitCorrect(matcher.group(1), ',');
            List<Integer> first = getArray(sets.get(1));
            List<Integer> second = getArray(sets.get(2));
            List<Integer> ans1 = Global.delListFromList(first, second);
            for(int i = 0; i < ans1.size(); i++)
            {
                ans.add(ans1.get(i));
            }
        }
        return ans;
    }   
    public List<String> getListByFilter(String filterString)    
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
        return convertToString(ansId);
    }
    public String getSumOverField(String command)
    {
        //sum(List<E>, "field");
        Matcher matcher = Global.getMatcher(command, "sum\\((.+)\\)");
        Integer ans = 0;
        if(matcher.find())
        {
            List<String> fields  = splitCorrect(matcher.group(1), ',');
            List<Integer> list = getArray(fields.get(0));
            String value = getCommandResult(fields.get(1));
            for(int i = 0; i < list.size(); i++)
            {
                Card card = duelController.getDuel().getCardHolderById(list.get(i)).getCard();
                if(card.getCardType() == CardType.MONSTER)
                {
                    try{
                        ans += Integer.parseInt(duelController.getDuel().getCardHolderById(list.get(i)).getValue(value));
                    }catch(Exception e){                        
                    }                
                }
                
            }
            command = command.replace(matcher.group(0), ans.toString());
        }
        return command;
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
    public Zone parseZone(String josn, Integer cardHolderId){
        String[] zoneArgument = josn.split("_");
        Player player = null;
        if (zoneArgument[0].compareTo("my") == 0) player = owner;
        else 
        if(zoneArgument[0].compareTo("opp") == 0)
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
        else if(zoneArgument[0].compareTo("owner") == 0){
                if(duelController.getDuel().getCardHolderAddressById(cardHolderId) != null)
                    player = duelController.getDuel().getCardHolderById(cardHolderId).getOwner();
                else
                    return null;
            }     
            else
            {
                return null;
            }
        return Zone.get(zoneArgument[1], player);
    }
    public String selective(String command)
    {
        Matcher ans = Global.getMatcher(command,"select\\(([^()]*)\\)");
        if(ans.find())
        {            
            List<String> fields = splitCorrect(ans.group(1), ',');            
            List<Integer> array = getArray(fields.get(0));
            int count = Integer.parseInt(getCommandResult(fields.get(1)));
            List<Integer> selected;
            if(fields.size() == 3)
                selected =  duelMenu.selective(array, count, fields.get(2));
            else
            {
                selected = duelMenu.selective(array, count, fields.get(2), fields.get(3));
            }
            command = command.replace(ans.group(0), new Gson().toJson(convertToString(selected), new ArrayList<String>().getClass()));
            return command;
        }
        else
            return command;
    }
    public String randomSelection(String command)
    {
        Matcher ans = Global.getMatcher(command,"random_select\\(([^()]*)\\)");
        if(ans.find())
        {
            List<String> fields = splitCorrect(command, ',');
            List<Integer> array = getArray(fields.get(0));
            int count = Integer.parseInt(fields.get(1));    
            List<Integer> selected = duelMenu.randomSelection(array, count, fields.get(2));        
            command = command.replace(ans.group(0), new Gson().toJson(convertToString(selected), new ArrayList<String>().getClass()));
            return command;
        }
        else
            return command;
    }
    public List<String> convertToString(List<Integer> integerList)
    {
        List<String> ans = new ArrayList<String>();        
        for(int i = 0; i < integerList.size(); i++)
        {
            ans.add(String.valueOf(integerList.get(i)));
        }
        return ans;
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