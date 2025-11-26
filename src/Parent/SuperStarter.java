package Parent;

import MonstersHeroesGame.HMGameStarter;
//Atart the Game Requested by the user
public class SuperStarter extends Starter{
    private final GamePrinter printer;
    public SuperStarter() {
        this.printer = new GamePrinter();
    }
    /*
    Input: N/A
    Outputs: N/A
    Function: Displays the welcom message to the user entering the Gaming Zone
     */
    public void displayWelcomeMessage() {
        String[] welcomeMsgs={"Welcome to my Gaming Zone!",
                "We currently just have one game - Heroes and Monsters"};

        printer.displayMsgs(welcomeMsgs);
    }
    /*
    Input: N/A
    Outputs: N/A
    Function: Start the gaming zone for the user
     */
    public void startGame() {
        displayWelcomeMessage();
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Allows User to select the game they want to play
     */
    public String getChoice(){
        String[] choice={"Enter 1 to play Heroes and Monsters","Enter 0/q to Quit"};
        return printer.getInput(choice);
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Starts the game selected by the user
     */
    public void playGame() {
        String[] invalidMsgs={"Please Enter a Valid Input"};
        while(true) {
            String choice = getChoice();
            if(choice.equalsIgnoreCase("q") || choice.equals("0")) {
                break;
            }
            else if(choice.equalsIgnoreCase("1")) {
                    int c=Integer.parseInt(choice);
                    HMGameStarter hmg=new HMGameStarter();
                    hmg.run();
            }
            else{
                printer.displayMsgs(invalidMsgs);
            }
        }
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Executed when user exits the gaming zone
     */
    public void endGame() {
        String[] endmMsg={"Thank You for Playing! Hope to see you soon"};
        printer.displayMsgs(endmMsg);
    }
}
