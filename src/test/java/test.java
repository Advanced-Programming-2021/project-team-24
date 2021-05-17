////import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//
////import org.junit.Test;
//
//import controller.DuelController;
//import model.card.CardState;
//import model.card.MonsterCard;
//import model.card.MonsterCardHolder;
//import model.duel.Duel;
//import model.duel.EffectParser;
//import model.effect.Effect;
//import model.effect.EffectManager;
//import model.effect.EffectType;
//import model.user.Player;
//import model.user.User;
//import view.DuelMenu;
//public class test {
//    @Test
//    public void readFile()
//    {
//        assertEquals("true", "true");
//
//    }
//    @Test
//    public void testReturnStatement() throws JsonSyntaxException, IOException
//    {
//        MonsterCard u = new Gson().fromJson(
//            fileRead("Battle warrior.json"),
//             MonsterCard.class);
//        Player vvvv = new Player(null);
//        MonsterCardHolder temp = new MonsterCardHolder(vvvv, u, CardState.ATTACK_MONSTER);
//        //User a = new User("alireza", "haqi", "alirezaaaaa");
//        //User b = new User("b   ", "b " , "alirezaa aaa aa a ");
//        model.user.User.register("behzad", "password", "nickname");
//        model.user.User.register("alireza", "haqi", "hesam");
//        User a = User.readUser("alireza");
//        User b= User.readUser("behzad");
//        DuelController duelController = new DuelController(new Duel(a, b, Integer.toString(1)));
//        DuelMenu duelMenu = new DuelMenu(a, b, "1");
//        Effect effect = new Effect();
//        effect.setEffect("");
//        effect.setReverse("reverse");
//        effect.setEffectType(EffectType.CONTINUES);
//        EffectManager vvvvvvv = new EffectManager(effect, vvvv , temp.getId());
//        duelController.setDuelMenu(duelMenu);
//        EffectParser v = new EffectParser(duelMenu, duelController, vvvvvvv);
//        String command = "if(#1#>#2#){return_t}else{return_f}";
//        String ans = v.getCommandResult(command);
//        assertEquals(ans, "false");
//
//    }
//    public void listFilesForFolder(final File folder) {
//        for (final File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                listFilesForFolder(fileEntry);
//            } else {
//                System.out.println(fileEntry.getName());
//            }
//        }
//    }
//
//
//    public String fileRead(String path) throws IOException
//    {
//        File currentDirFile = new File("Cards/MonsterCards/Battle warrior.json");
//        String helper = currentDirFile.getAbsolutePath();
//        String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
//        System.out.println(helper);
//        File myObj = new File(helper);
//        Scanner myReader = new Scanner(myObj);
//        String ans = "";
//        while (myReader.hasNextLine()) {
//            String data = myReader.nextLine();
//            ans += data;
//        }
//        myReader.close();
//        return ans;
//    }
//
//}
