package controller;

import com.google.gson.Gson;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.Event;
import model.card.magic.MagicCard;
import model.card.magic.MagicCardHolder;
import model.card.monster.MonsterCard;
import model.card.monster.MonsterCardHolder;
import model.card.monster.MonsterType;
import model.deck.Deck;
import model.deck.Decks;
import model.duel.Duel;
import model.duel.EffectParser;
import model.duel.Filter;
import model.effect.Effect;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;
import view.DuelMenu;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DuelControllerTest {
    @Test
    public void nextPhaseTest(){
        User user1 = new User("user1", "aaaaaa", "nickname1");
        User user2 = new User("user2", "aaaaaa", "nickname2");
        Card axeRaider = Card.getCardByName("Axe Raider");
        Card blackPendant = Card.getCardByName("Black Pendant");
        Deck deck = new Deck("deck");
        Decks decks = new Decks();
        decks.add(deck);
        deck.addMainCard(axeRaider);
        deck.addMainCard(blackPendant);
        decks.setActiveDeck(deck);
        user1.setDecks(decks);
        user2.setDecks(decks);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        Duel duel = new Duel(player1, player2);
        DuelController duelController = new DuelController(duel);
        for (int i = 0; i < 2; i++) duel.nextPhase();
        assertEquals("BATTLE", duelController.nextPhase().getContent());
    }
    @Test
    public void drawTest(){
        //TODO
    }
    @Test
    public void selectTest(){
        //Address address = new Address();
    }
    @Test
    public void test(){
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        Deck alireza = new Deck("alireza");
        for(int i = 0; i < Card.getAllCards().size(); i++)
        {
            alireza.addMainCard(Card.getAllCards().get(i));
        }
        a.getDecks().add(alireza);
        a.getDecks().setActiveDeck(a.getDecks().getDeckByName("alireza"));
        b.getDecks().add(alireza);
        b.getDecks().setActiveDeck(b.getDecks().getDeckByName("alireza"));
        Player playerA= new Player(a);
        Player playerB = new Player(b);
        Duel duel = new Duel(playerA,playerB);
        DuelController duelController = new DuelController(duel);
        Address addressMonster = Address.get(Zone.get("monster", playerA), 0);
        MonsterCard monsterCard = (MonsterCard) Card.getCardByName("Battle warrior");
        MonsterCardHolder monsterCardHolder = new MonsterCardHolder(playerA, monsterCard, CardState.ATTACK_MONSTER);
        duel.setMap(addressMonster,monsterCardHolder);
        MagicCard magicCard = (MagicCard) Card.getCardByName("Dark Hole");
        MagicCardHolder magicCardHolder = new MagicCardHolder(playerB, magicCard, CardState.ACTIVE_MAGIC);
        Random x = new Random();
        for(int j = 0; j < 50; j++)
        {

            duel.nextPhase();
            List<CardHolder> all = new ArrayList<>();
            for(int aa = 0; aa < duel.getAllCardHolder().size(); aa++)
                all.add(duel.getAllCardHolder().get(aa));
            Collections.shuffle(all);
            for(int jj = 0; jj < all.size(); jj++)
            {
                duelController.select(duel.getCardHolderAddressById(all.get(jj).getId()));
                duelController.deselect();
                for(int qq = 0; qq < all.size(); qq++)
                {
                    try{
                        duelController.attack(duel.getCardHolderAddressById(all.get(qq).getId()));
                        duelController.activeMagic();
                        if(x.nextInt(2) % 2 == 1)
                            duelController.changePosition();
                        else
                            duelController.flipSummon();
                            duelController.directAttack();
                            //duelController.attack(Address.get(Zone.get("monster", )))

                            if(x.nextInt(2) % 2 == 1)
                                duelController.set();
                            else
                                duelController.summon();    
                            duelController.directAttack();                   
                            duelController.changePosition();
                            duelController.directAttack();
                            duelController.activeMagic();
                            duelController.activeMagic();
                        }catch(Exception e){

                        }
                    }
                }
            duel.setMap(addressMonster,monsterCardHolder);
            duelController.select(Address.get(Zone.get("monster", playerA), 0));
            duelController.set();
            duelController.summon();
            duelController.changePosition();
            duelController.showSelectedCard();
            duelController.directAttack();
            //duelController.draw();
            duelController.updateAutomaticEffect();
            duelController.nextPhase();
        }
    }

}
