package Parent;

public abstract class Starter {
//    Abstract class specifying method in order to run the game
    public void run(){
        startGame();
        playGame();
        endGame();
    }
    public abstract void startGame();
    public abstract void playGame();
    public abstract void endGame();
}