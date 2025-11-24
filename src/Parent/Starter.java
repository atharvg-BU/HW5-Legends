package Parent;

public abstract class Starter {
    public void run(){
        startGame();
        playGame();
        endGame();
    }
    public abstract void startGame();
    public abstract void playGame();
    public abstract void endGame();
}