package view;



import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {
    public static Scanner scanner = new Scanner(System.in);
    public static String nextLine()
    {
        String temp = scanner.nextLine().replaceAll(" +", " ");
        return temp.trim();
    }
    public static boolean regexFind(String content, String regex)
    {
        return Pattern.compile(regex).matcher(content).find();            
    }
    public static Matcher getMatcher(String content, String regex)
    {
        return Pattern.compile(regex).matcher(content);
    }
    public static void main(String[] args) {
    
    }
}
