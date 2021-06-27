package model.card.monster;

import model.card.Card;
import model.card.CardState;
import model.user.Player;
import model.user.User;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.*;
public class MonsterCardHolderTest {
    MonsterCard monsterCard = (MonsterCard) Card.getCardByName("Axe Raider");
    User user = new User("salam", "aaaaaa", "sdfsdf");
    Player player = new Player(user);
    MonsterCardHolder monsterCardHolder = new MonsterCardHolder(player, monsterCard, CardState.SET_DEFENCE);
    @Test
    public void getCardTest() {
        assertEquals(monsterCard, monsterCardHolder.getCard());
    }
    @Test
    public void getAttackTest(){
        assertEquals(monsterCardHolder.getAttack(), 1700);
    }
    @Test
    public void getDefenceTest(){
        assertEquals(monsterCardHolder.getDefence(), 1150);
    }
    @Test
    public void flipSummonTest(){
        monsterCardHolder.flipSummon();
        assertEquals(monsterCardHolder.getCardState(), CardState.ATTACK_MONSTER);
    }
    @Test
    public void toStringTest(){
        assertEquals("Name :Axe Raider\n" +
                "Monster\n" +
                "Type: WARRIOR\n" +
                "ATK :1700\n" +
                "DEF :1150\n" +
                "Description: An axe-wielding monster of tremendous strength and agility.\n", monsterCardHolder.toString());
    }
    @Test
    public void changeStateTest(){
        monsterCardHolder.changeCardState(CardState.DEFENCE_MONSTER);
        assertEquals(CardState.DEFENCE_MONSTER, monsterCardHolder.getCardState());
    }
    @AfterClass
    public static void cleanUp(){
        User.deleteUser("salam");
    }
}
