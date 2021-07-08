package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardState;
import model.card.CardType;
import model.deck.Deck;
import model.duel.Duel;
import model.user.Player;
import model.user.User;
import model.zone.Address;
import model.zone.Zone;
import view.DuelMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DuelController {
    @FXML
    ImageView imageA,imageB;
    @FXML
    ImageView h1a, h2a, h3a, h4a, h5a, s1a, s2a, s3a, s4a, s5a, m1a, m2a, m3a, m4a, m5a;
    @FXML
    ImageView h1b, h2b, h3b, h4b, h5b, m1b, m2b, m3b, m4b, m5b, s1b, s2b, s3b, s4b, s5b;
    @FXML
    Label phase,nameA,nameB,LPA,LPB;
    Image image;
    DuelMenu duelMenu;
    controller.DuelController duelController;
    Duel duel;
    HashMap<Address, CardHolder> map;
    ArrayList<ImageView> handA;
    ArrayList<ImageView> spellA;
    ArrayList<ImageView> monsterA;
    ArrayList<ImageView> handB;
    ArrayList<ImageView> spellB;
    ArrayList<ImageView> monsterB;

    public void initialize() {
        User a = new User("alireza", "alireza", "alireza");
        User b = new User("alir", "alir", "alir");
        model.deck.Deck alireza = new Deck("alireza");
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            alireza.addMainCard(Card.getAllCards().get(i));
        }
        a.getDecks().add(alireza);
        a.getDecks().setActiveDeck(a.getDecks().getDeckByName("alireza"));
        b.getDecks().add(alireza);
        b.getDecks().setActiveDeck(b.getDecks().getDeckByName("alireza"));
        duelMenu = new DuelMenu(new Player(a), new Player(b));
        duelController = duelMenu.getDuelController();
        duel = duelController.getDuel();
        map = duel.getMap();
        duelMenu.checkPhase();
        phase.setText(duel.getCurrentPhase().name());
        handA = new ArrayList<>(Arrays.asList(h1a, h2a, h3a, h4a, h5a));
        spellA = new ArrayList<>(Arrays.asList(s1a, s2a, s3a, s4a, s5a));
        monsterA = new ArrayList<>(Arrays.asList(m1a, m2a, m3a, m4a, m5a));
        handB = new ArrayList<>(Arrays.asList(h1b, h2b, h3b, h4b, h5b));
        spellB = new ArrayList<>(Arrays.asList(s1b, s2b, s3b, s4b, s5b));
        monsterB = new ArrayList<>(Arrays.asList(m1b, m2b, m3b, m4b, m5b));

        //nextPhase();
        update();
//        duelMenu.addMagicCard("Mystical space typhoon");
//        duelMenu.addMonsterCardOpp("Axe Raider");
//        duelMenu.run();
    }

    public void updateCards(ArrayList<ImageView> cards, String zone,boolean isOpponent) {
        Player player;
        if(isOpponent) player = duel.getOpponent();
        else player = duel.getCurrentPlayer();
        for (int i = 0; i < cards.size(); i++) {
            Address key = Address.get(Zone.get(zone, player), i);
            if (map.get(key) != null) {
                CardHolder cardHolder = map.get(key);
                CardState cardHolderState = cardHolder.getCardState();
                String name = cardHolder.getCard().getName().replace(" ", "") + ".jpg";
                if(isOpponent) {
                    if (cardHolderState == CardState.ACTIVE_MAGIC || cardHolderState == CardState.ATTACK_MONSTER || cardHolderState == CardState.VISIBLE_MAGIC || CardState.DEFENCE_MONSTER == cardHolderState) {
                        setImage(cardHolder, name);
                    } else {
                        setImage(cardHolder, "Unknown.jpg");
                    }
                }
                else{
                    if(cardHolderState == CardState.SET_DEFENCE)
                        setImage(cardHolder, "Unknown.jpg");
                    else
                        setImage(cardHolder, name);
                }
                handleRotate(cards.get(i),cardHolder.getCardState());
            } else {
                image = null;
            }
            cards.get(i).setImage(image);
        }
    }

    public void handleRotate(ImageView imageView,CardState cardState){
        if(cardState == CardState.DEFENCE_MONSTER || cardState == CardState.SET_DEFENCE){
            imageView.setRotate(90);
        }
        else
            imageView.setRotate(0);
    }

    public void setImage(CardHolder cardHolder, String name) {
        if (cardHolder.getCard().getCardType().equals(CardType.MONSTER) || name.equals("Unknown.jpg")) {
            //System.out.println(name);
            image = new Image(getClass().getResourceAsStream("images/cards/Monsters/" + name));
        } else {
            //System.out.println(name);
            image = new Image(getClass().getResourceAsStream("images/cards/SpellTrap/" + name));
        }
    }

    public void update() {
        updateCards(spellA, "magic",false);
        updateCards(monsterA, "monster",false);
        updateCards(handA, "hand",false);
        updateCards(spellB, "magic",true);
        updateCards(monsterB, "monster",true);
        updateCards(handB, "hand",true);
        nameA.setText(duel.getCurrentPlayer().getNickname());
        nameB.setText(duel.getOpponent().getNickname());
        LPA.setText(String.valueOf(duel.getCurrentPlayer().getLifePoint()));
        LPB.setText(String.valueOf(duel.getOpponent().getLifePoint()));
        imageA.setImage(new Image(getClass().getResourceAsStream(duel.getCurrentPlayer().getUser().getImageAddress())));
        imageB.setImage(new Image(getClass().getResourceAsStream(duel.getOpponent().getUser().getImageAddress())));
    }

    public void click(MouseEvent mouseEvent) {
        Address address = Address.get(Zone.get("hand", duel.getCurrentPlayer()), 0);;
        String id = ((ImageView) mouseEvent.getSource()).getId();
        if (id.charAt(0) == 'h') {
            address = select(id, "hand");
        }
        else if (id.charAt(0) == 'm') {
            address = select(id, "monster");
        }
        else if (id.charAt(0) == 's') {
            address = select(id, "magic");
        }
        if(id.charAt(2)=='b' && id.charAt(0)=='m' && mouseEvent.isShiftDown()) {
            System.out.println(duelController.attack(address).getContent());
            update();
            return;
        }
        duelController.select(address);
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            if(mouseEvent.isShiftDown())
                System.out.println(duelController.activeMagic().getContent());
            else
                System.out.println(duelController.changePosition().getContent());
        }
        else if (mouseEvent.isAltDown())
            System.out.println(duelController.summon().getContent());
        else if(mouseEvent.isControlDown())
            System.out.println(duelController.set().getContent());
        update();
    }

    public Address select(String id, String zone) {
        Address address;
        if (id.charAt(2) == 'a')
            address = Address.get(Zone.get(zone, duel.getCurrentPlayer()), Character.getNumericValue(id.charAt(1)) - 1);
        else
            address = Address.get(Zone.get(zone, duel.getOpponent()), Character.getNumericValue(id.charAt(1)) - 1);
        return address;
    }

    public void nextPhase() {
        System.out.println(duelController.nextPhase().getContent());
        phase.setText(duel.getCurrentPhase().name());
        checkPhase();
    }

    public void checkPhase(){
        if (duelController.getDuel().getCurrentPhase() == Duel.Phase.DRAW) {
            System.out.println(duelController.draw().getContent());
            nextPhase();
            update();
            phase.setText(duel.getCurrentPhase().name());
            return;
        } else if (duelController.getDuel().getCurrentPhase() == Duel.Phase.STANDBY) {
            System.out.println("okaaaaaaaaaaaaab");
            duelController.updateAutomaticEffect();
            nextPhase();
            update();
            phase.setText(duel.getCurrentPhase().name());
            return;
        }
        else if (duelController.getDuel().getCurrentPhase() == Duel.Phase.END) {
            nextPhase();
        }
    }

    public void directAttack(MouseEvent mouseEvent) {
        if(mouseEvent.isShiftDown())
            System.out.println(duelController.directAttack().getContent());
    }
}
