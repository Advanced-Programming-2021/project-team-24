package view;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public static Random random = new Random();
    public static List<Integer> delListFromList(List<Integer> first, List<Integer> second)
    {
        List<Integer> ans = new ArrayList<Integer>();
        for(int i = 0; i < first.size(); i++)
            {   
                int flag = 0;
                for(int j = 0; j < second.size(); j++)
                {
                    if(first.get(i) == second.get(j))
                    {
                        flag = 1;
                    }
                }
                if(flag == 0)
                {
                    ans.add(first.get(i));
                }
            }
        return ans;
    }
    public static void main(String[] args) {
    
    }
}
