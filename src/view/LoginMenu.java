package view;


import static Global.regexFind;

import java.util.HashMap;

import model.user.User;
import controller.Message;
import controller.TypeMessage;

public class LoginMenu {
   

    private static final String REGEX_ENTER_MENU = "menu enter (\\w+)";
    
    public HashMap<String, String> parseDashString(String command)
    {
        HashMap<String, String> data = new HashMap<String , String>();
        String[] parts = command.split(" ");
        for(int i = 0; i < parts.length - 1; i++)
        {
            if(Global.regexFind(parts[i + 1], "-") == false)
            {
                if(parts[i].substring(0, 2).compareTo("--") == 0 && parts[i].charAt(2) != '-')
                {
                    data.put(parts[i].substring(2, parts[i].length()), parts[i + 1]);
                }
                else
                if(parts[i].charAt(1) != '-')
                {
                    if(parts[i].compareTo("-p") == 0)
                        data.put("password", parts[i + 1]);
                    else
                    if(parts[i].compareTo("-u") == 0)
                        data.put("username", parts[i + 1]);
                    else
                    if(parts[i].compareTo("-n") == 0)
                        data.put("nickname", parts[i + 1]);
                }
            }
        }
        return data;
    }

    public void register(String command)
    {   
        HashMap<String , String> data = parseDashString(command);
        if(data.get("username") != null && data.get("password") != null && data.get("nickname") != null)
        {
            Message message = User.register(data.get("username"), data.get("password"), data.get("nickname"));
            System.out.println(message.getContent());            
        }
        else
            System.out.println("invalid command");
    }
    public void login(String command)
    {
        HashMap<String , String> data = parseDashString(command);
        if(data.get("username") != null && data.get("password") != null)
        {
            Message message = User.login(data.get("username"), data.get("password"));
            System.out.println(message.getContent());
            if(message.getTypeMessage() == TypeMessage.SUCCESSFUL)
            {   
                //TODO pass to main menu
            }
        }
        else
            System.out.println("invalid command");

    }
    
    public void run()
    {
        while(true)
        {
            String command = Global.nextLine();
            if(command.compareToIgnoreCase("menu exit") == 0)
            {
                return ;            
            }
            else
            if(Global.regexFind(command , "menu show-current"))
            {
                System.out.println("Login Menu");                
            }
            else
            if(Global.regexFind(command, REGEX_ENTER_MENU))
            {
                System.out.println("please login first");
            }
            else
            if(command.substring(0, 10).compareTo("user login") == 0 && command.split(" ").length == 6)
            {
                login(command);
            }
            else
            if(command.split(" ").length == 8 && command.substring(0, 11).compareTo("user create") == 0)
            {   
                register(command);
            }
            else            
            {
                System.out.println("invalid command");
            }
        }
    }
    public static void main(String[] args) {
        new LoginMenu().run();   
    }
}
