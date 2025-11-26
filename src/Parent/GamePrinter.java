package Parent;

import java.util.Scanner;

public class GamePrinter {
//    Class to handle the general input and printing logic
    Scanner sc=new Scanner(System.in);

    /*
    Input: 1D array containing string of input messages
    Outputs: Value after taking the input from user
    Function: Function to get input
     */
    public String getInput(String[] inputMsgs) {
        for (String msg : inputMsgs) {
            System.out.println(msg);
        }
        String input=sc.nextLine();
        return input.trim();
    }

    /*
    Input: 1D array containing string of messages to display
    Outputs: N/A
    Function: Function to display messages to the user
     */
    public void displayMsgs(String[] msgs) {
        for (String msg : msgs) {
            System.out.println(msg);
        }
    }

    /*
    Input: 1D array containing string of messages to display
    Outputs: N/A
    Function: Function to display messages to the user (where no new line is needed while printing)
    */
    public void displayMsgNoSp(String[] msgs) {
        for (String msg : msgs) {
            System.out.print(msg+" ");
        }
        System.out.println();
    }

}
