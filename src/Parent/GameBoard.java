package Parent;

public class GameBoard {
//    Parent class to manage the state of gameboard
    protected int sizex;
    protected int sizey;
    protected GameSpace boardArray[][];

    public GameBoard(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.boardArray = new GameSpace[sizex][sizey];
    }
    /*
    Input: N/A
    Outputs: N/A
    Function: Getter Function to get the number of rows in the array
     */
    public int getSizex() {
        return sizex;
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Getter Function to get the number of columns in the array
     */
    public int getSizey() {
        return sizey;
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Getter Function to get the board array
     */
    public GameSpace[][]  getBoardArray() {
        return boardArray;
    }
}
