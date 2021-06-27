package controller;


import com.google.gson.Gson;
import model.card.Card;
import model.card.magic.MagicCard;
import model.card.monster.MonsterCard;
import model.user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportExportController extends MainMenu{

    public ImportExportController(User user){
        super(user);
    }
    public Message importCard(String cardName){
        boolean isThereCard = false;
        //deserialize
        try {
            File directoryPathMonsterCard = new File("Cards/MonsterCards");
            File[] filesListMonsterCard = directoryPathMonsterCard.listFiles();
            File directoryPathMagicCard = new File("Cards/MagicCards");
            File[] filesListMagicCard = directoryPathMagicCard.listFiles();
            assert filesListMonsterCard != null;
            assert filesListMagicCard != null;
            for(File file : filesListMonsterCard) {

                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                MonsterCard card = new Gson().fromJson(json,MonsterCard.class);
                if (card.getName().equals(cardName)) {
                    isThereCard = true;
                    user.getCards().add(card);
                }
            }
            for(File file : filesListMagicCard) {
                String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
                MagicCard card = new Gson().fromJson(json,MagicCard.class);
                if (card.getName().equals(cardName)) {
                    isThereCard = true;
                    user.getCards().add(card);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (isThereCard)  return new Message(TypeMessage.SUCCESSFUL, "Import Successfully");
        return new Message(TypeMessage.ERROR, "There is no card with this name");
    }
    public Message exportCard(String cardName){
        //serialize
        if (Card.getCardByName(cardName) == null) return new Message(TypeMessage.ERROR, "There is no card with this name");
        try {
            Card card = Card.getCardByName(cardName);
            File file = new File("Cards/" + cardName + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(card));
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return new Message(TypeMessage.SUCCESSFUL,"Export Successfully");
    }
}
