package model.card.monster;

import model.card.Card;
import model.user.Player;
import model.user.User;
import org.junit.Test;

import static org.junit.Assert.*;
public class MonsterCardTest {
    @Test
    public void getMonsterTypeTest(){
        MonsterCard monsterCard = (MonsterCard) Card.getCardByName("Axe Raider");
        assertEquals(monsterCard.getMonsterType(), MonsterType.WARRIOR);
    }
}
