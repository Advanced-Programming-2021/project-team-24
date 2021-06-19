package dueltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controller.DuelController;
import model.card.Card;
import model.duel.Duel;
import model.duel.EffectParser;
import model.effect.EffectManager;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;
import view.DuelMenu;

public class CompilerTester {
    @Test
    public void checkCommands()
    {    
        User user1 = new User("testUser1", "1234", "qwer1");
        User user2 = new User("testUser2", "1234", "qwer1");        
        
        user1.getCards().addAll(Card.getAllCards());
        user2.getCards().addAll(Card.getAllCards());        
        System.out.println(user1.getCards().size());

        DuelMenu duelMenu = new DuelMenu(user1, user2, "1");
        DuelController duelController = duelMenu.getDuelController();
        Duel duel = duelController.getDuel();
        duelController.setDuelMenu(duelMenu);
        
        duelController.runPhase();
        System.out.println(duelController.select(Address.get(Zone.get("hand", duelMenu.getPlayer(false)), 1)).getContent());
        //EffectParser effectParser = new EffectParser(duelMenu, duelController, new EffectManager(effect, owner, idCardHolder));
    
        
        user1.logout();
    }
}
