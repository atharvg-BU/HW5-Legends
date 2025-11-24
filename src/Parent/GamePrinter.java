package Parent;

import java.util.Scanner;

public class GamePrinter {
    Scanner sc=new Scanner(System.in);
    public String getInput(String[] inputMsgs) {
        for (String msg : inputMsgs) {
            System.out.println(msg);
        }
        String input=sc.nextLine();
        return input.trim();
    }

    public void displayMsgs(String[] msgs) {
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }
    public void displayMsgNoSp(String[] msgs) {
        for (String msg : msgs) {
            System.out.print(msg+" ");
        }
        System.out.println();
    }

}
