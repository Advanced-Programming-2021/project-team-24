package model.duel;

import model.card.CardType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class FilterTest {
    List<String> zones = new ArrayList<>();
    Filter filter = new Filter();
    Filter filter1 = new Filter("ali");
    Filter filter2 = new Filter(2, "ali");
    Filter filter3 = new Filter("Axe Raider", zones, "ali");
    Filter filter4 = new Filter(zones, "ali");
    Filter filter5 = new Filter(2, 3, "ali");
    @Test
    public void getLevelTest(){
        filter.setMinLevel(1);
        filter.setMaxLevel(5);
        assertEquals((int) filter.getMinLevel(), 1);
        assertEquals((int) filter.getMaxLevel(), 5);
    }
    @Test
    public void getCardTypeTest(){
        filter.setCardType(CardType.SPELL);
        assertEquals(CardType.SPELL, filter.getCardType());
    }
    @Test
    public void getAttackDefenceTest(){
        filter.setMaxAttack(1000);
        assertEquals(1000, (int) filter.getMaxAttack());
        filter.setMinAttack(1000);
        assertEquals(1000, (int) filter.getMinAttack());
        filter.setMaxDefence(1000);
        assertEquals(1000, (int) filter.getMaxDefence());
        filter.setMinDefence(1000);
        assertEquals(1000, (int) filter.getMinDefence());
    }
    @Test
    public void getCardNamesTest(){
        List<String> cardNames = new ArrayList<>();
        filter.setCardNames(cardNames);
        assertEquals(cardNames, filter.getCardNames());
    }
    @Test
    public void getIdCardHolderTest(){
        List<Integer> cardHolder = new ArrayList<>();
        filter.setIdCardHolder(cardHolder);
        assertEquals(cardHolder, filter.getIdCardHolder());
    }
    @Test
    public void getZonesTest(){
        filter.setZones(zones);
        assertEquals(zones, filter.getZones());
    }
    @Test
    public void getOwnerName(){
        filter.setOwnerName("ali");
        assertEquals(filter.getOwnerName(), "ali");
    }
}
