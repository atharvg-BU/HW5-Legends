package Parent;

public class GameBoard {
    protected int sizex;
    protected int sizey;
    protected GameSpace boardArray[][];

    public GameBoard(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.boardArray = new GameSpace[sizex][sizey];
    }

    public int getSizex() {
        return sizex;
    }
    public int getSizey() {
        return sizey;
    }

    public GameSpace[][]  getBoardArray() {
        return boardArray;
    }
}
