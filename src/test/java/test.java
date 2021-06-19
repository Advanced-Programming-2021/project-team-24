import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.user.UserTest;
import org.junit.Test;

import model.card.CardState;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.duel.EffectParser;
import model.effect.Effect;
import model.effect.EffectManager;
import model.effect.EffectType;
import model.user.Player;
import model.user.User;
import view.DuelMenu;
public class test {
    @Test
    public void readFile()
    {
        assertEquals("true", "true");
        
    }
    @Test
    public void testReturnStatement() throws JsonSyntaxException, IOException
    {        
        MonsterCard u = new Gson().fromJson(
            fileRead("Battle warrior.json"),
             MonsterCard.class);
        User.register("behzad", "password", "nickname");
        User.register("alireza", "haqi", "hesam");
        User a = User.readUser("alireza");
        User b= User.readUser("behzad");
        DuelMenu duelMenu = new DuelMenu(a, b, "1");
        
        MonsterCardHolder temp = new MonsterCardHolder(duelMenu.getPlayer(true), u, CardState.ATTACK_MONSTER);
        //User a = new User("alireza", "haqi", "alirezaaaaa");
        //User b = new User("b   ", "b " , "alirezaa aaa aa a ");        
        
        Effect effect = new Effect();
        effect.setEffect("if(#1#>#2#)&return_t&else&return_f&");
        effect.setReverse("if(#1#>#2#)&return_t&else&return_f&");
        effect.setEffectType(EffectType.CONTINUES);
        EffectManager vvvvvvv = new EffectManager(effect,
         duelMenu.getPlayer(true) 
         , temp.getId());
        duelMenu.getDuelController().setDuelMenu(duelMenu);
        EffectParser v = new EffectParser(duelMenu, duelMenu.getDuelController(), vvvvvvv);
        String command = "if(#1#>#2#)&return_t&else&return_f&";
        String ans = v.runEffect();
        assertEquals(ans, "false");
        
        

    }
    public void checkBooleanQuestion() throws JsonSyntaxException, IOException
    {
        MonsterCard u = new Gson().fromJson(
            fileRead("Battle warrior.json"),
             MonsterCard.class);
        User.register("behzad", "password", "nickname");
        User.register("alireza", "haqi", "hesam");
        User a = User.readUser("alireza");
        User b= User.readUser("behzad");
        DuelMenu duelMenu = new DuelMenu(a, b, "1");
        
        MonsterCardHolder temp = new MonsterCardHolder(duelMenu.getPlayer(true), u, CardState.ATTACK_MONSTER);
        
        Effect effect = new Effect();
        effect.setEffect("q_yn(halet chetoreh){return_t}{return_f}");
        effect.setReverse("q_yn(halet chetoreh){return_t}{return_f}");
        effect.setEffectType(EffectType.CONTINUES);
        EffectManager vvvvvvv = new EffectManager(effect,
         duelMenu.getPlayer(true) 
         , temp.getId());
        duelMenu.getDuelController().setDuelMenu(duelMenu);
        EffectParser v = new EffectParser(duelMenu, duelMenu.getDuelController(), vvvvvvv);
        String ans = v.runEffect();    
    }
    @Test
    public void testFilter() throws JsonSyntaxException, IOException
    {
        MonsterCard u = new Gson().fromJson(
            fileRead("Battle warrior.json"),
             MonsterCard.class);
        User.register("behzad", "password", "nickname");
        User.register("alireza", "haqi", "hesam");
        User a = User.readUser("alireza");
        User b= User.readUser("behzad");
        DuelMenu duelMenu = new DuelMenu(a, b, "1");
        
        MonsterCardHolder temp = new MonsterCardHolder(duelMenu.getPlayer(true), u, CardState.ATTACK_MONSTER);
        
        Effect effect = new Effect();
        effect.setEffect("set(boz,1300);set(hesam,bemazeh);");
        effect.setReverse("set(boz,1300);set(hesam,bemazeh);");
        effect.setEffectType(EffectType.CONTINUES);
        EffectManager vvvvvvv = new EffectManager(effect,
         duelMenu.getPlayer(true) 
         , temp.getId());
        duelMenu.getDuelController().setDuelMenu(duelMenu);
        EffectParser v = new EffectParser(duelMenu, duelMenu.getDuelController(), vvvvvvv);
        String ans = v.runEffect();    
    }
    
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
    
    
    public String fileRead(String path) throws IOException
    {
        File currentDirFile = new File("Cards/MonsterCards/Battle warrior.json");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
        System.out.println(helper);
        File myObj = new File(helper);
        Scanner myReader = new Scanner(myObj);
        String ans = "";
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            ans += data;
        }
        myReader.close();
        return ans;
    }

}
