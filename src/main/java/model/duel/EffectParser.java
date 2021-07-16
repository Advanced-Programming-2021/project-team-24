package model.duel;

import java.io.File;
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
import model.zone.Address;
import model.zone.Zone;
import model.zone.ZonesName;
import view.DuelMenu;
import view.Global;


public class EffectParser {    
    DuelController duelController;
    DuelMenu duelMenu;
    Duel duel;
    Player owner;
    EffectManager effectManager;
    Effect effect;
    int idCardHolderOwner;
    private HashMap<String, String> extraKeyWords;    
    String ans;
    public DuelController getDuelController()
    {
        return this.duelController;
    }
    public String draw(String command)    
    {
        if(Global.regexFind(command, "draw()"))
        {
            duelController.draw();
            command = command.replace("draw()", "");
        }
        return command;
    }
    public EffectParser(DuelMenu duelMenu, DuelController duelController, EffectManager effectManager)
    {
        this.extraKeyWords = effectManager.getExtraKeyWords();
        this.idCardHolderOwner = effectManager.getOwnerCardHolderId();
        this.effect = effectManager.getEffect();
        this.effectManager = effectManager;
        this.owner = this.effectManager.getOwner();  
        this.duelMenu = duelMenu;      
        this.extraKeyWords = new HashMap<String, String>();
        this.duelController = duelController;
        this.duel = duelController.getDuel();
    }    
    
    

    public String getAns() {
        return this.ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String runEffect()
    {
        if(effect.getReverse().length() > 0 || effect.getEffectCommand().length() > 0)
        {
            if(effectManager.getEffect().getEffectType() == EffectType.QUICK_PLAY)
            {            
                Effect reverseEffect = effect.clone();
                effectManager.getEffect().setReverse("");
                reverseEffect.setEffect(effect.getReverse());
                reverseEffect.setReverse("");
                if(owner.getMap().getEffects().get(Event.END_TURN) == null)
                {
                    owner.getMap().getEffects().put(Event.END_TURN, new ArrayList<EffectManager>());
                }
                for(HashMap.Entry<String,String> entry : effectManager.getExtraKeyWords().entrySet())
                {
                    owner.getMap().getCardMap().put((String)entry.getKey(), (String)entry.getValue());
                }
                EffectManager deathEffectManager = new EffectManager(reverseEffect, owner, owner.getMap().getId());
                owner.getMap().getEffects().get(Event.END_TURN).add(deathEffectManager);
            }
            else
            {
                Effect reverseEffect = effect.clone();
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
            ans = null;
            effectManager.setActivated(true);        
            getCommandResult
            (effect.getEffectCommand()
            );
            return ans;
        }
        else
            return "";
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
        Matcher matcher = Global.getMatcher(command, "if\\(#([^#]*)#[<>]#([^#]*)#\\)(\\(&.+&\\))");
        if (matcher.find()) {
            String s1 = getCommandResult(matcher.group(1));
            String s2 = getCommandResult(matcher.group(2));
            if (s1.compareTo(s2) > 0) {
                return getCommandResult(splitCorrect(matcher.group(3).substring(1, matcher.group(3).length() - 1), '&').get(1));
            } else {
                return getCommandResult(splitCorrect(matcher.group(3).substring(1, matcher.group(3).length() - 1), '&').get(3));
            }
        }
        return command;
    }
    public String changeZone(String command)
    {
        Matcher matcher = Global.getMatcher(command, "changeZone\\((.+)\\)");
        if(matcher.find())
        {
            List<String> fields = splitCorrect(matcher.group(1), ',');
            Gson gson = new Gson();
            command = command.replace(matcher.group(0), "");
            String changeList = getCommandResult(fields.get(0));
            List<Integer> cardHolders = getArray(changeList);
            Zone targetZone = null;//parseZone(fields.get(1));        
            for(int i = 0; i < cardHolders.size(); i++)
            {
                targetZone = parseZone(fields.get(1), cardHolders.get(i));
                if(fields.size() == 3)
                {            
                    
                    CardState cardState = gson.fromJson(fields.get(2), CardState.class);
                    duelController.getDuel().changerZone(cardHolders, targetZone, cardState, duelMenu);
                }
                else
                {
                    duelController.getDuel().changerZone(cardHolders, targetZone, CardState.NONE, duelMenu);                
                }
            }
        }
        return command;
        //changeZone(List<>, target_zone, card_state);
        //parse target zone with zoneParser
        //advanced mode : changeZone(List<>, target_zone, Card_State);
        //for putting card in monster zone this part is needed
    }
    public String changeLP(String command)
    {
        //changeLp
        Matcher matcher = Global.getMatcher(command, "changeLP\\(([^()]+),(.+)\\)");
        if(matcher.find())
        {
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
            System.out.println(command);
        }
        return command;        
    }
    public String q_yn(String command)
    {
        Matcher matcher = Global.getMatcher(command, "q_yn\\(([^{}()]+)\\)(.+)");
        if (matcher.find()) {
            String queString = matcher.group(1);
            Boolean ans = duelMenu.BooleanQYN(queString);
            List<String> ifElsePart = splitByParentheses(matcher.group(2));
            if (ans) {
                return getCommandResult(ifElsePart.get(0));
            } else {
                return getCommandResult(ifElsePart.get(1));
            }
        }
        return command;
    }
    
    public String handleMessage(String command)
    {
        if(Global.regexFind(command, "message\\(([^()]+)\\)") && command.substring(0,7).equals("message"))
        {
            Matcher matcher = Global.getMatcher(command, "message\\(([^()]+)\\)");
            if(matcher.find())
            {
                //TODO
                command = command.replace(matcher.group(0), "");
                System.out.println(matcher.group(1));
                return command;
            }
        }
        return command;
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

    public int correctStateOfChar(String command, char ch)
    {
        int counter = 0;
        for(int i = 0; i < command.length(); i++)
        {
            if(command.charAt(i) == '(' || command.charAt(i) == '{')
            {
                counter++;
            }
            if(command.charAt(i) == ')' || command.charAt(i) == '}')
            {
                counter--;
            }
            if(counter == 0 && command.charAt(i) == ch)
            {
                return i;
            }
        }
        return -1;
    }
    public static final String GET_STRING = "get\\(([^()]*)\\)";
    public String getCommandResult(String command)
    {
        if(command != null)
        {
            if(correctStateOfChar(command, ';') == -1)
            {
                command = command.replace(" ", "");
                //check get
                command = draw(command);
                for(int i = 0; i < 1; i++)
                {
                    
                    command =  handleMessage(command);
                    if(command.length() >= 8 && command.substring(0, 8).equals("return_t") && ans == null)
                    {
                        ans = "true";
                        return "true";
                    }
                    if(Global.regexFind(command, "changeValue\\(.+\\)"))
                    {
                        command = changeValue(command);
                    }
                    if(command.length() >= 3 && command.substring(0, 3).equals("set"))
                    {
                        setCommand(command);
                    }
                    
                    if(command.length() >= new String("changeZone").length() && command.substring(0, new String("changeZone").length()).equals("changeZone"))
                    {
                        command = changeZone(command);                
                    }
                    if(command.length() >= 2 && command.substring(0, 2).equals("if"))
                    {
                        return handleConditional(command);
                    }
                    if(command.length() >= 4 && command.substring(0, 4).equals("q_yn"))
                    {
                        return q_yn(command);
                    }


                    command = parseKeyWords(command);
                    command = handleGetCommand(command);
                    command = handleGetCommand(command);
                    command = handleGetCommand(command);
                    if(command.equals("nextPhase()"))
                    {
                        duelController.getDuel().nextPhase();
                        return "";
                    }
                    if(command.length() >= new String("select").length() && command.substring(0, 6).equals("select"))
                    {
                        command = selective(command);
                        continue;
                    }
                    if(command.length() >= new String("random_selection").length() && command.substring(0, 16).equals("random_selection"))
                    {
                        command = randomSelection(command);
                        continue;
                    }
                   
                    if(command.length() >= 8 && command.substring(0, 8).equals("return_f") && ans == null)
                    {
                        ans = "false";
                        return "false";
                    }
                    if(Global.regexFind(command, "filter"))
                    {
                        command = getListByFilter(command);
                        continue;
                    }
                    if(Global.regexFind(command ,"coin"))
                    {
                        coin(command);
                        continue;
                    }
                    
                    
                    
                    
                    
                    command = handleChangeLPCommand(command);
                    
                    
                    if(Global.regexFind(command, "del"))
                    {
                        command = deleteListFromList(command);
                    }
                    if(Global.regexFind(command, "sum"))
                    {
                        command = getSumOverField(command);
                    }
                    command = handleNormCommand(command);                
                    
                    command = calculater(command);
                    
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
        }
        else
            return new String();
        // command type:
        // change zone
        //filter
        //TODO
        //return string as result of command, maybe some get or ...
        return command;
    }
    public String handleChangeLPCommand(String command) {
        try
        {
            if(command.substring(0, 8).equals("changeLP"))
            {
                command = changeLP(command);                
            }
        }
        catch(Exception e)
        {    
            //e.printStackTrace();
        }
        return command;
    }
    public String handleNormCommand(String command) {
        while(true)
        {
            if(Global.regexFind(command, "Norm\\((.+)\\)"))
            {
                Matcher matcher = Global.getMatcher(command, "Norm\\((.+)\\)");
                if (matcher.find()) {
                    command = command.replace("Norm(" + splitByParentheses(matcher.group(0)).get(0) + ")", normSet((matcher.group(0))));
                }
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

        String list = getCommandResult(splitByParentheses(matcher.group(0)).get(0));        
        return String.valueOf((new Gson().fromJson(list, new ArrayList<String>().getClass())).size());        
    }
    public String parseKeyWords(String command)
    {
        //this
        for(Map.Entry<String, String> entry : extraKeyWords.entrySet())
        {
            command = command.replace(entry.getKey(), entry.getValue());
        }
        List<String> v = new ArrayList<String>();
        v.add(Integer.toString(this.idCardHolderOwner));
        command = command.replace("this", new Gson().toJson(v, new ArrayList<Integer>().getClass()));
        
        //simple zones
        
        Player current = this.owner;
        Player opponent = duelController.getDuel().getOpponent();
        if(opponent.getNickname().equals(owner.getNickname()))
            opponent = duelController.getDuel().getCurrentPlayer();

        for (String string : model.zone.ZonesName.zoneStrings) {
            String zone = "$my_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(duel.duelZones.get(string, current));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }        
            command = command.replace(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }
        
        for (String string : model.zone.ZonesName.zoneStrings) {
            String zone = "$opp_" + string + "$";
            List<CardHolder> cardList = duelController.getZone(duel.duelZones.get(string, opponent));
            List<String> ans = new ArrayList<String>();
            for(int i = 0; i < cardList.size(); i++)
            {
                ans.add(Integer.toString(cardList.get(i).getId()));
            }
            command = command.replace(zone, new Gson().toJson(ans, new ArrayList<String>().getClass()));
        }
        
        for(String string : ZonesName.zoneStrings)
        {
            String zone = "$" + string + "$";
            List<CardHolder> cardHoldersFirst = duelController.getZone(duel.duelZones.get(string, opponent));
            List<CardHolder> cardHoldersSecond = duelController.getZone(duel.duelZones.get(string, current));
            List<Integer> uIntegers = new ArrayList<>();
            for(int i = 0; i < cardHoldersFirst.size(); i++)
            {
                uIntegers.add(cardHoldersFirst.get(i).getId());                
            }
            for(int i = 0; i < cardHoldersSecond.size(); i++)
            {
                uIntegers.add(cardHoldersSecond.get(i).getId());                
            }
            command = command.replace(zone, new Gson().toJson(convertToString(uIntegers), new ArrayList<Integer>().getClass()));
        }
        

        List<String> own_rit_trib = convertToString(getCardHoldersIdList(duelController.getZone(duel.duelZones.get("hand", duelController.getDuel().getCurrentPlayer()))));
        //own_rit_trib = convertToInteger(own_rit_trib
        List<String> own = new ArrayList<String>();
        own.add(String.valueOf(effectManager.getOwner().getMap().getId()));
        command = command.replace("*own*", new Gson().toJson(own));


        List<String> opp = new ArrayList<String>();
        opp.add(String.valueOf(effectManager.getOwner().getMap().getId()));
        command = command.replace("*opp*", new Gson().toJson(opp));
        
        return command;
    }
    public List<String> union(List<String> a, List<String> b)
    {
        List<String> ans = new ArrayList<>();
        for(int i = 0; i < a.size(); i++)
            ans.add(a.get(i));
        for(int i = 0; i < b.size(); i++)
            ans.add(b.get(i));
    
        return ans;
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
        List<Integer> ans = new ArrayList<>();
        try{
        ans = convertToInteger(
            gson.fromJson(
                getCommandResult(
                    getCommandResult(array)
                    ), new ArrayList<String>().getClass()));
        }catch(Exception e){
            ans = new ArrayList<>();
        }        
        return ans;
    }
    public void setCommand(String setCommand)
    {
        Gson gson = new Gson();
        List<String> fields = splitCorrect(splitByParentheses(setCommand).get(0) ,',');
        List<Integer> cardHolders = getArray(fields.get(0));
        String key = fields.get(1);
        String value = getCommandResult(fields.get(2));
        if(fields.size() == 3)
        {
            duelController.getDuel().setterMap(cardHolders, key, value, 1);//TODO
        }
        else
        {
            Integer time = 1;
            try {
                time = Integer.parseInt(fields.get(3));
            } catch (Exception e) {
                //TODO: handle exception
            }
            duelController.getDuel().setterMap(cardHolders, key, value, time);
        }
        //set("List<>" , "key" , "value"):rev(List<E>, "key", value);
    }
    public String getCommand(String getCommand)
    {
        List<String> fields = splitCorrect(splitByParentheses(getCommand).get(0), ',');
        List<Integer> cardHolders = getArray(
            getCommandResult(fields.get(0))
            );
        return
         duelController.getDuel().getterMap(cardHolders, fields.get(1));        
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
    public String deleteListFromList(String command)
    {
        //del(List<>, List<E>)
        Matcher matcher = Global.getMatcher(command, "del\\(([^()]+)\\)");
        List<String> ans = new ArrayList<String>();
        if(matcher.find())
        {
            List<String> sets = splitCorrect(matcher.group(1), ',');
            List<Integer> first = getArray(sets.get(0));
            List<Integer> second = getArray(sets.get(1));
            List<Integer> ans1 = Global.delListFromList(first, second);
            for(int i = 0; i < ans1.size(); i++)
            {
                ans.add(String.valueOf(ans1.get(i)));
            }
            command = command.replace(matcher.group(0), new Gson().toJson(ans));
            return command;
        }
        return command;
    }   
    public String changeValue(String command)
    {
        //changeValue(List<E>,key,change);
        if(Global.regexFind(command, "changeValue\\(([^()]+)\\)"))
        {
            Matcher matcher = Global.getMatcher(command, "changeValue\\(([^()]+)\\)");
            matcher.find();
            List<String> fields = splitCorrect(matcher.group(1), ',');
            List<Integer> idCardHolders = getArray(getCommandResult(fields.get(0)));
            String key = fields.get(1);
            String value = getCommandResult(fields.get(2));
            if(fields.size() == 3)
            {
                duelController.getDuel().changerMap(idCardHolders, key, value, 1);
            }
            else
            {
                Integer time = 1;
                try {
                    time = Integer.parseInt(fields.get(3));
                } catch (Exception e) {
                }
                duelController.getDuel().changerMap(idCardHolders, key, value, time);
            }
        }
        return command;
    }
    public String getListByFilter(String filterString)    
    {
        //#Filter#(["key":"value"]);        
        Matcher matcher = Global.getMatcher(filterString, "filter\\(([^()]*)\\)");
        if(matcher.find())
        {
            
            String filterJson = "{"+ matcher.group(1) + "}";
            Gson gson = new Gson();;
            Filter filter =  gson.fromJson(filterJson, Filter.class);
            filter.setOwnerName(effectManager.getOwner().getNickname());
            List<CardHolder> ans = duelController.getDuel().getCardHolderFilter(filter);        
            List<Integer> ansId = new ArrayList<Integer>();
            for(int i = 0; i < ans.size(); i++)
            {
                ansId.add(ans.get(i).getId());
            }
            filterString = filterString.replace(matcher.group(0), new Gson().toJson(convertToString(ansId)));
            return filterString;
        }
        else
            return filterString;
    }    
    public List<Integer> getCardHoldersIdList(List<CardHolder> cardHolders)
    {
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < cardHolders.size(); i++)
            ans.add(cardHolders.get(i).getId());
        return ans;
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
                        e.printStackTrace();
                    }                
                }
                
            }
            command = command.replace(matcher.group(0), ans.toString());
        }
        return command;
    }
    public static void main(String[] args) {
        System.out.println(calculater("10-(10+10)"));
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
        return duel.duelZones.get(zoneArgument[1], player);
    }
    public String selective(String command)
    {
        Matcher ans = Global.getMatcher(command,"select\\((.+)\\)");
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
    public static String calculater(String command)
    {
        int flag = 0;
        while(true)
        {
            flag = 0;
            while(true)
            {
                if(Global.regexFind(command, "(\\d+)\\*(\\d+)")){                
                    Matcher matcher = Global.getMatcher(command,"(\\d+)\\*(\\d+)");
                    matcher.find();
                    flag = 1;
                    command = command.replace(matcher.group(0), String.valueOf(Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2))));
                }
                else
                    break;
            
            }
            while(true)
            {
                if(Global.regexFind(command, "(\\d+)\\+(\\d+)")){                
                    Matcher matcher = Global.getMatcher(command,"(\\d+)\\+(\\d+)");
                    matcher.find();
                    flag = 1;
                    command = command.replace(matcher.group(0), String.valueOf(Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(2))));
                }
                else
                    break;
            }
            while(true)
            {
                if(Global.regexFind(command, "(\\d+)-(\\d+)")){                
                    Matcher matcher = Global.getMatcher(command,"(\\d+)-(\\d+)");
                    matcher.find();
                    flag = 1;
                    command = command.replace(matcher.group(0), String.valueOf(Integer.parseInt(matcher.group(1)) - Integer.parseInt(matcher.group(2))));
                }
                else
                    break;
            }
            while(true)
            {
                if(Global.regexFind(command, "\\((\\d+)\\)"))
                {
                    Matcher matcher = Global.getMatcher(command, "\\((\\d+)\\)");
                    matcher.find();                
                    command = command.replace(matcher.group(0), matcher.group(1));
                    flag = 1;
                }
                else
                    break;

            }
            if(flag == 0)
                break;
        }

        return command;
    }
    
}