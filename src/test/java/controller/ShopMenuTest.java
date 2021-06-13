package controller;

import model.card.Card;
import model.user.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ShopMenuTest {
    @Test
    public void buyCardTest(){
        Card.intialize();
        User user = new User("user", "123", "asd");
        ShopMenu shopMenu = new ShopMenu(user);
        assertEquals("there is no card with this name", shopMenu.buyCard("aaaaaaa").getContent());
        assertNull(shopMenu.buyCard("Axe Raider").getContent());
        List<Card> cards = Card.getAllCards();
        String content = "";
        for (int i = 0; i < cards.size(); i++) {
            content += cards.get(i).getName() +":"+cards.get(i).getPrice()+"\n";
        }
        assertEquals(content, shopMenu.getInfo().getContent());
        user.setCoin(0);
        assertEquals("not enough money", shopMenu.buyCard("Axe Raider").getContent());
        User.deleteUser("user");
    }
    @Test
    public void buyCardNoCardTest(){
        User user = new User("user", "123", "asd");
        assertEquals("there is no card with this name", new ShopMenu(user).buyCard("aaaaaaa").getContent());
        User.deleteUser("user");
    }
    @Test
    public void buyCardNotEnoughMoneyTest(){
        User user = new User("user", "123", "asd");
        user.setCoin(0);
        Card.intialize();
        assertEquals("not enough money", new ShopMenu(user).buyCard("Axe Raider").getContent());
        User.deleteUser("user");
    }
    @Test
    public void buyCardSuccessfullyTest(){
        User user = new User("user", "123", "asd");
        Card.intialize();
        assertEquals(null, new ShopMenu(user).buyCard("Axe Raider").getContent());
        User.deleteUser("user");
    }
    @Test
    public void getInfoTest(){
        User user = new User("user", "123", "asd");
        List<Card> cards = Card.getAllCards();
        String content = "";
        for (int i = 0; i < cards.size(); i++) {
            content += cards.get(i).getName() +":"+cards.get(i).getPrice()+"\n";
        }
        assertEquals(content, new ShopMenu(user).getInfo().getContent());
        User.deleteUser("user");
    }
}
