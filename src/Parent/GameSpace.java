package Parent;

import java.util.ArrayList;
import java.util.List;

public class GameSpace {
    private List<GamePiece> pieces;

    public GameSpace() {
        pieces = new ArrayList<GamePiece>();
    }

    /*
    inputs: piece
    outputs: n/a
    function: adds input piece to list
     */
    public void addPiece(GamePiece p) {
        pieces.add(p);
    }

    public int numPieces() {
        return pieces.size();
    }

    /*
    inputs: n/a
    outputs: piece at start of list
    function: rests first piece in list
     */
    public GamePiece getFirstPiece() {
        return pieces.get(0);
    }

    /*
    Inputs: N/A
    Outputs: list of pieces
    Function: gets a shallow copy of pieces member
     */
    public List<GamePiece> getPieces() {
        return pieces;
    }

    /*
    inputs: pieces list
    outputs: N/a
    function: sets input list to pieces member
     */
    public void setPieces(List<GamePiece> pieces) {
        this.pieces = pieces;
    }

    /*
    inputs: N/A
    outputs: N/a
    function: clears pieces arr
     */
    public void clearSpace() {
        pieces.clear();
    }

    /*
    inputs: N/A
    outputs: piece that was removed
    function: 'pops' piece off the list
     */
    public GamePiece popPiece() {
        GamePiece p = getFirstPiece();
        pieces.remove(p);
        return p;
    }

    public GamePiece popLastPiece() {
        GamePiece p = pieces.get(pieces.size()-1);
        pieces.remove(p);
        return p;
    }


    /*
    inputs: N/A
    outputs: bool of if empty
    function: used to check for pieces
     */
    public boolean isEmpty() {
        return pieces.isEmpty();
    }

    public GamePiece popIndex(int index) {
        return pieces.remove(index);
    }
}

