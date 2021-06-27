package view;

import model.user.User;

import java.util.regex.Matcher;

public class ImportExportMenu extends Menu{
    private controller.ImportExportController importExportController = new controller.ImportExportController(user);
    public ImportExportMenu(User user){
        super(user);
    }
    public void run(){
        while(true) {
            String command = Global.nextLine();
            Matcher matcher1 = Global.getMatcher(command, "(?<=import card ).*");
            Matcher matcher2 = Global.getMatcher(command, "(?<=export card ).*");
            if (matcher1.find()){
                String cardName = matcher1.group();
                System.out.println(importExportController.importCard(cardName).getContent());
            }
            else if (matcher2.find()){
                String cardName = matcher2.group();
                System.out.println(importExportController.exportCard(cardName).getContent());
            }
            else if (command.equals("menu show-current")) {
                System.out.println("ImportExport Menu");
            }
            else if (checkMenuExit(command)) {
                exitMenu("Import/Export");
                return;
            }
            else if(checkEnterMenu(command)) {
                enterMenu("Import/Export", command );
            }
            else System.out.println("invalid command");
        }
    }
}
