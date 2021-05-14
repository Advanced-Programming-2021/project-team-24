import java.util.HashMap;
import java.util.Map;

import model.card.Card;
import model.user.User;
import view.LoginMenu;




public class App {
    public static void main(String[] args) throws Exception {
        Card.intialize();
        HashMap<String, Integer> hm = 
                     new HashMap<String, Integer>();
  
        // Adding mappings to HashMap
        hm.put("GeeksforGeeks", 54);
        hm.put("A computer portal", 80);
        hm.put("For geeks", 82);
  
        // Printing the HashMap
        System.out.println("Created hashmap is" + hm);
  
        // Loop through the hashmap
        System.out.println("HashMap after adding bonus marks:");
  
        // Using for-each loop
        for (Map.Entry mapElement : hm.entrySet()) {
            String key = (String)mapElement.getKey();
  
            // Add some bonus marks
            // to all the students and print it
            int value = ((int)mapElement.getValue() + 10);
  
            System.out.println(key + " : " + value);
        }
        User.initialize();
        new LoginMenu().run();
    }
    

}
