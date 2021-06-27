package model.card.magic;
import model.card.Card;

import model.effect.Effect;
import org.junit.Test;

import static org.junit.Assert.*;

public class MagicCardTest {
    @Test
    public void getMagicTypeTest(){
        MagicCard magicCard = (MagicCard) Card.getCardByName("Dark Hole");
        assertEquals(magicCard.getMagicType().toString(), "SPELL");
    }
    @Test
    public void getMagicIconTest(){
        MagicCard magicCard = (MagicCard) Card.getCardByName("Dark Hole");
        assertEquals(magicCard.getMagicIcon().toString(), "NORMAL");
    }
    @Test
    public void isSpellTest(){
        MagicCard magicCard = (MagicCard) Card.getCardByName("Dark Hole");
        assertTrue(magicCard.isSpell());
    }
    @Test
    public void toStringTest(){
        MagicCard magicCard1 = (MagicCard) Card.getCardByName("Dark Hole");
        assertEquals("Name :Dark Hole\n" +
                "Spell\n" +
                "Type: UNLIMITED\n" +
                "Description:Destroy all monsters on the field.\n", magicCard1.toString());
        MagicCard magicCard2 = (MagicCard) Card.getCardByName("Magic Cylinder");
        assertEquals(magicCard2.toString(), "Name :Magic Cylinder\n" +
                "Trap\n" +
                "Type: UNLIMITED\n" +
                "Description:\"When an opponent's monster declares an attack: Target the attacking monster; negate the attack," +
                " and if you do, inflict damage to your opponent equal to its ATK.\"\n");
    }
    @Test
    public void getEffectTest(){
        Effect effect = new Effect("");
        effect.setSpeed(1);
        MagicCard magicCard = new MagicCard();
        magicCard.setEffect(effect);
        assertEquals(effect, magicCard.getEffect());
        assertEquals(1, (int) magicCard.getSpeed());
    }
}
