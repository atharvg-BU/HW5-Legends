package Parent;

import MonstersHeroesGame.HMGameStarter;

public class SuperStarter extends Starter{
    private final GamePrinter printer;
    public SuperStarter() {
        this.printer = new GamePrinter();
    }
    public void displayWelcomeMessage() {
        String[] welcomeMsgs={"Welcome to my Gaming Zone!",
                "We currently just have one game - Heroes and Monsters"};

        printer.displayMsgs(welcomeMsgs);
    }
    public void startGame() {
        displayWelcomeMessage();
    }

    public String getChoice(){
        String[] choice={"Enter 1 to play Heroes and Monsters","Enter 0/q to Quit"};
        return printer.getInput(choice);
    }
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

    public void endGame() {
        String[] endmMsg={"Thank You for Playing! Hope to see you soon"};
        printer.displayMsgs(endmMsg);
    }
}
