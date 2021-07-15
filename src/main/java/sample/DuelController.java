package sample;

import controller.Message;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
import org.controlsfx.control.PopOver;
import view.DuelMenu;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class DuelController {
    @FXML
    public ImageView graveYard;
    @FXML
    ImageView imageA, imageB;
    @FXML
    ImageView h1a, h2a, h3a, h4a, h5a, s1a, s2a, s3a, s4a, s5a, m1a, m2a, m3a, m4a, m5a;
    @FXML
    ImageView h1b, h2b, h3b, h4b, h5b, m1b, m2b, m3b, m4b, m5b, s1b, s2b, s3b, s4b, s5b;
    @FXML
    ImageView f1b, f1a;
    @FXML
    AnchorPane fieldBG;
    @FXML
    Label phase, nameA, nameB, LPA, LPB;
    @FXML
    Group menu;
    @FXML
    FontAwesomeIcon setting;
    User user;
    Image image;
    DuelMenu duelMenu;
    controller.DuelController duelController;
    Duel duel;
    HashMap<Address, CardHolder> map;
    ArrayList<ImageView> handA, spellA, monsterA, handB, spellB, monsterB, fieldA, fieldB;
    ArrayList<ArrayList<ImageView>> allCards;
    int flag = 0;
    PopOver popOver;
    MediaPlayer mediaPlayer;
    private String currentMusic;

    public DuelController(User user) {
        this.user = user;
    }

    public void initialize() {
        handleMusic("main");
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
        fieldA  = new ArrayList<>(Collections.singletonList(f1a));
        fieldB  = new ArrayList<>(Collections.singletonList(f1b));
        allCards = new ArrayList<>(Arrays.asList(handA, spellA, monsterA, spellB, handB, monsterB,fieldA,fieldB));
        for (ArrayList<ImageView> zone : allCards) {
            for (ImageView card : zone) {
                card.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.isShiftDown() && flag == 0) {
                            String id = ((ImageView) mouseEvent.getSource()).getId();
                            CardState cardHolderState = map.get(getAddressById(id)).getCardState();
                            if(id.charAt(2)=='a' || (cardHolderState == CardState.ACTIVE_MAGIC || cardHolderState == CardState.ATTACK_MONSTER || cardHolderState == CardState.VISIBLE_MAGIC || CardState.DEFENCE_MONSTER == cardHolderState)) {
                                CardView cardView = new CardView(map.get(getAddressById(id)));
                                popOver = new PopOver(cardView);
                                popOver.show(card);
                                flag = 1;
                            }
                        }
                    }
                });
            }
        }
        for (ArrayList<ImageView> zone : allCards) {
            for (ImageView card : zone) {
                card.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (flag == 1) {
                            popOver.hide();
                            flag = 0;
                        }
                    }
                });
            }
        }
        checkPhase();
        update();
        for (ImageView card : monsterA) {
            handleDrag(card);
        }
        for (ImageView card : monsterB) {
            handleDrop(card);
        }
    }

    private void handleMusic(String name) {
        currentMusic = name;
        String s = "src/main/resources/musics/"+name+".mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        if(mediaPlayer!=null) mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }


    private void handleDrag(ImageView source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                String id = ((ImageView) event.getSource()).getId();
                duelController.select(getAddress(id, "monster"));
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(id);
                db.setContent(content);
                event.consume();
            }
        });
    }

    private void handleDrop(ImageView target) {

        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    String id = ((ImageView) event.getSource()).getId();
                    System.out.println(duelController.attack(duel.duelAddresses.get(duel.duelZones.get("monster", duel.getOpponent()), Character.getNumericValue(id.charAt(1)) - 1)).getContent());
                    update();
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private Address getAddressById(String id) {
        Player player;
        if (id.charAt(2) == 'a') {
            player = duel.getCurrentPlayer();
        } else {
            player = duel.getOpponent();
        }
        String zone;
        if (id.charAt(0) == 'h')
            zone = "hand";
        else if (id.charAt(0) == 'm')
            zone = "monster";
        else
            zone = "magic";
        return duel.duelAddresses.get(duel.duelZones.get(zone, player), Character.getNumericValue(id.charAt(1)) - 1);
    }

    public void updateCards(ArrayList<ImageView> cards, String zone, boolean isOpponent) {
        Player player;
        if (isOpponent) player = duel.getOpponent();
        else player = duel.getCurrentPlayer();
        for (int i = 0; i < cards.size(); i++) {
            Address key = duel.duelAddresses.get(duel.duelZones.get(zone, player), i);
            if (map.get(key) != null) {
                CardHolder cardHolder = map.get(key);
                CardState cardHolderState = cardHolder.getCardState();
                String name = cardHolder.getCard().getName().replace(" ", "") + ".jpg";
                if (isOpponent) {
                    if (cardHolderState == CardState.ACTIVE_MAGIC || cardHolderState == CardState.ATTACK_MONSTER || cardHolderState == CardState.VISIBLE_MAGIC || CardState.DEFENCE_MONSTER == cardHolderState) {
                        setImage(cardHolder, name);
                    } else {
                        setImage(cardHolder, "Unknown.jpg");
                    }
                } else {
                    if (cardHolderState == CardState.SET_DEFENCE || cardHolderState == CardState.SET_MAGIC)
                        setImage(cardHolder, "Unknown.jpg");
                    else
                        setImage(cardHolder, name);
                }
                handleRotate(cards.get(i), cardHolder.getCardState());
            } else {
                if(zone.equals("field"))
                    image = new Image(getClass().getResourceAsStream("images/duel/field.png"));
                else image = null;
            }
            cards.get(i).setImage(image);
        }
    }

    public void handleRotate(ImageView imageView, CardState cardState) {
        if (cardState == CardState.DEFENCE_MONSTER || cardState == CardState.SET_DEFENCE) {
            imageView.setRotate(90);
        } else
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
        handleField();
        if (duelController.isRoundFinished()) {
            System.out.println("End");
            String message;
            boolean same = duel.getCurrentPlayer().getUser().equals(user);
            boolean dead = duel.getCurrentPlayer().isDead();
            if (same ^ dead) message = "You Lose!";
            else message = "You Win!";
            System.out.println(message);
            Info info = new Info(message);
            popOver = new PopOver(info);
            popOver.show(phase);
            info.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        Common.switchToSceneMainMenu(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        updateCards(fieldA,"field",false);
        updateCards(spellA, "magic", false);
        updateCards(monsterA, "monster", false);
        updateCards(handA, "hand", false);
        updateCards(spellB, "magic", true);
        updateCards(monsterB, "monster", true);
        updateCards(handB, "hand", true);
        updateCards(fieldB,"field",true);
        nameA.setText(duel.getCurrentPlayer().getNickname());
        nameB.setText(duel.getOpponent().getNickname());
        LPA.setText(String.valueOf(duel.getCurrentPlayer().getLifePoint()));
        LPB.setText(String.valueOf(duel.getOpponent().getLifePoint()));
        LPA.setTextFill(getColorByLP(duel.getCurrentPlayer().getLifePoint()));
        LPB.setTextFill(getColorByLP(duel.getOpponent().getLifePoint()));
        imageA.setImage(new Image(getClass().getResourceAsStream(duel.getCurrentPlayer().getUser().getImageAddress())));
        imageB.setImage(new Image(getClass().getResourceAsStream(duel.getOpponent().getUser().getImageAddress())));
    }

    private void handleField() {
        Address fieldAddress = duel.duelAddresses.get(duel.duelZones.get("field",duel.getCurrentPlayer()),0);
        CardHolder fieldCardholder = map.get(fieldAddress);
        if(fieldCardholder!=null){
            if(currentMusic.equals("main"))
                handleMusic("fieldActive");
            fieldBG.getStyleClass().clear();
            String fieldName = fieldCardholder.getCard().getName();
            if (fieldName.equals("Forest"))
                fieldBG.getStyleClass().add("forest");
            else if (fieldName.equals("ClosedForest"))
                fieldBG.getStyleClass().add("closedForest");
            else if(fieldName.equals("Yami"))
                fieldBG.getStyleClass().add("yami");
            else if(fieldName.equals("Umiiruka"))
                fieldBG.getStyleClass().add("umiiruka");
        }
    }

    private Color getColorByLP(int lp) {
        int green;
        int red;
        if (lp > 4000) {
            green = 255;
            red = (int) Math.floor((8000.0 - lp) / 4000.0 * 255.0);
        } else {
            red = 255;
            green = (int) Math.floor((lp) / 4000.0 * 255.0);
        }
        return Color.rgb(red, green, 0);
    }

    public void click(MouseEvent mouseEvent) {
        Address address = duel.duelAddresses.get(duel.duelZones.get("hand", duel.getCurrentPlayer()), 0);
        String id = ((ImageView) mouseEvent.getSource()).getId();
        if (id.charAt(0) == 'h') {
            address = getAddress(id, "hand");
        } else if (id.charAt(0) == 'm') {
            address = getAddress(id, "monster");
        } else if (id.charAt(0) == 's') {
            address = getAddress(id, "magic");
        }
        if (id.charAt(2) == 'b' && id.charAt(0) == 'm' && mouseEvent.isShiftDown()) {
            System.out.println(duelController.attack(address).getContent());
            update();
            return;
        }
        duelController.select(address);
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (mouseEvent.isShiftDown())
                System.out.println(duelController.activeMagic().getContent());
            else
                System.out.println(duelController.changePosition().getContent());
        } else if (mouseEvent.isAltDown())
            System.out.println(duelController.summon().getContent());
        else if (mouseEvent.isControlDown())
            System.out.println(duelController.set().getContent());
        update();
    }

    public Address getAddress(String id, String zone) {
        Address address;
        if (id.charAt(2) == 'a')
            address = duel.duelAddresses.get(duel.duelZones.get(zone, duel.getCurrentPlayer()), Character.getNumericValue(id.charAt(1)) - 1);
        else
            address = duel.duelAddresses.get(duel.duelZones.get(zone, duel.getOpponent()), Character.getNumericValue(id.charAt(1)) - 1);
        return address;
    }




    public void nextPhase() {
        System.out.println(duelController.nextPhase().getContent());
        phase.setText(duel.getCurrentPhase().name());
        checkPhase();
    }

    public void checkPhase() {
        if (duelController.getDuel().getCurrentPhase() == Duel.Phase.DRAW) {
            System.out.println(duelController.draw().getContent());
            nextPhase();
            update();
            phase.setText(duel.getCurrentPhase().name());
            return;
        } else if (duelController.getDuel().getCurrentPhase() == Duel.Phase.STANDBY) {
            duelController.updateAutomaticEffect();
            nextPhase();
            update();
            phase.setText(duel.getCurrentPhase().name());
            return;
        } else if (duelController.getDuel().getCurrentPhase() == Duel.Phase.END) {
            nextPhase();
        }
    }

    public void directAttack(MouseEvent mouseEvent) {
        if (mouseEvent.isShiftDown()) {
            System.out.println(duelController.directAttack().getContent());
            update();
        }
    }

    public void end(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void showSetting() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), menu);
        tt.setByY(50);
        tt.setCycleCount(1);
        tt.play();
    }

    public void hideStting(MouseEvent mouseEvent) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), menu);
        tt.setByY(-50);
        tt.setCycleCount(1);
        tt.play();
    }

    public void showGraveyard(MouseEvent mouseEvent) {
        List<Card> cards = new ArrayList<>();
        List<CardHolder> cardHolders = duel.getZone(duel.duelZones.get("graveyard", duel.getCurrentPlayer()));
        for (CardHolder cardHolder : cardHolders) {
            cards.add(cardHolder.getCard());
        }
        CardsPresenter cardsPresenter = new CardsPresenter(cards);
        popOver = new PopOver(cardsPresenter);
        popOver.show(graveYard);
    }

    public void surrender(){
        duelController.surrender();
    }
}
