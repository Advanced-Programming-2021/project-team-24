package controller.process;

import controller.Message;
import controller.TypeMessage;
import model.Response;
import model.Situation;
import model.user.User;

import java.util.regex.Matcher;

public class ImportExportMenu extends Menu{
    private controller.ImportExportController importExportController = new controller.ImportExportController(user);
    public ImportExportMenu(User user){
        super(user);
    }
    public synchronized Response process(String command){
        Matcher matcher1 = Global.getMatcher(command, "(?<=import card ).*");
        Matcher matcher2 = Global.getMatcher(command, "(?<=export card ).*");
        if (matcher1.find()){
            String cardName = matcher1.group();
            return new Response(importExportController.importCard(cardName), Situation.IMPORTEXPORT);
        }
        else if (matcher2.find()){
            String cardName = matcher2.group();
            return new Response(importExportController.exportCard(cardName), Situation.IMPORTEXPORT);
        }
        else if (command.equals("menu show-current")) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, "ImportExport Menu"), Situation.IMPORTEXPORT);
        }
        else if (checkMenuExit(command)) {
            return new Response(new Message(TypeMessage.SUCCESSFUL, ""), Situation.MAIN);

        }
        return new Response(new Message(TypeMessage.ERROR, "invalid command"), Situation.IMPORTEXPORT);
    }
}
