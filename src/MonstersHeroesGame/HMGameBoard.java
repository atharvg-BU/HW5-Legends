package MonstersHeroesGame;

import Parent.GameBoard;
import Parent.GamePiece;
import Parent.GameSpace;

import java.sql.SQLOutput;
import java.util.*;

public class HMGameBoard extends GameBoard {
    public HMGameBoard(int sizex, int sizey) {
        super(sizex, sizey);
    }

    /*
    Input: Number of market spaces and inaccessible spaces to be placed, hero initials to place in the starting block of player and player names list to associate the hero pieces placed
    Outputs: N/A
    Function: Function Set initial pieces on board
     */
    public void setInitialPieces(int marketNum, int inaccNum, String[] heroInitials, List<String> playerNames){
        Random rand = new Random();
        // Place markets
        do {
            boardArray=new GameSpace[sizex][sizey];
            placeRandomTiles('$', marketNum, rand);

            // Place inaccessible spaces
            placeRandomTiles('X', inaccNum, rand);

            // Step 3: Ensure no row or column is completely inaccessible
            fixInaccessibleRowsAndCols(rand);

            boolean trapped=false;
            for (int i = 0; i < playerNames.size(); i++) {
                int r = rand.nextInt(sizex);
                int c = rand.nextInt(sizey);
                if (boardArray[r][c] == null) {
                    GameSpace obj = new GameSpace();
                    for(int h=0;h<heroInitials.length;h++){
                        HeroPiece hero = new HeroPiece(heroInitials[h]);
                        hero.setOwner(playerNames.get(0));
                        obj.addPiece(hero);
                    }

                    boardArray[r][c] = obj;
                    if(isPlayerTrapped(r,c)){
                        System.out.println("Sorry Generated a bad board! Regenerating");
                        trapped=true;
                        break;
                    }
                } else {
                    i--;
                }
            }
            if(!trapped){
                break;
            }
        }while (true);

    }

    /*
    Input: Tile to be placed, number of tiles of that type and Random class variable to generate random integers
    Outputs: N/A
    Function: Function to set the given pieces randomly on board
     */
    public void placeRandomTiles(char tile, int count, Random rand) {
        int size = boardArray.length;
        while (count > 0) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (boardArray[r][c] == null) {
                GameSpace obj=new GameSpace();
                if(tile=='$'){
                    MarketPlacePiece ob=new MarketPlacePiece("$");
                    obj.addPiece(ob);
                }
                else if(tile=='X'){
                    ImmovableSpacePiece ob=new ImmovableSpacePiece("X");
                    obj.addPiece(ob);
                }
                boardArray[r][c] = obj;
                count--;
            }
        }
    }

    /*
    Input: Random class variable to generate random integers
    Outputs: N/A
    Function: Function to fix inaccessible/blocked spaces (if any)
     */
    public void fixInaccessibleRowsAndCols(Random rand) {
        int size = boardArray.length;

        // Fix rows
        for (int i = 0; i < sizex; i++) {
            boolean fullX = true;
            for (int j = 0; j < sizey; j++) {
                if ((boardArray[i][j]!=null) && !((boardArray[i][j].getFirstPiece()) instanceof ImmovableSpacePiece)) {
                    fullX = false;
                    break;
                }
            }
            if (fullX) {
                int col = rand.nextInt(sizey);
                boardArray[i][col] =null;
            }
        }

        // Fix columns
        for (int j = 0; j < size; j++) {
            boolean fullX = true;
            for (int i = 0; i < size; i++) {
                if ((boardArray[i][j]!=null) && !((boardArray[i][j].getFirstPiece()) instanceof ImmovableSpacePiece)) {
                    fullX = false;
                    break;
                }
            }
            if (fullX) {
                int row = rand.nextInt(size);
                boardArray[row][j] =null;
            }
        }
    }

    /*
    Input: Row and column start location of player
    Outputs: Boolean value on basis of whether player is trapped or not
    Function: Function to fix inaccessible/blocked spaces (if any)
     */
    private boolean isPlayerTrapped(int sr, int sc) {
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        boolean[][] visited = new boolean[sizex][sizey];
        Queue<int[]> q = new LinkedList<>();

        // BFS start from player position
        visited[sr][sc] = true;
        q.add(new int[]{sr, sc});

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];

                // Out of bounds
                if (nr < 0 || nc < 0 || nr >= sizex || nc >= sizey)
                    continue;

                // Skip Immovable (X)
                if (boardArray[nr][nc] != null &&
                        boardArray[nr][nc].getFirstPiece() instanceof ImmovableSpacePiece)
                    continue;

                if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        // Now verify: every non-X space is reachable
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {

                // Skip inaccessible tiles
                if (boardArray[i][j] != null &&
                        boardArray[i][j].getFirstPiece() instanceof ImmovableSpacePiece)
                    continue;

                // If it's a walkable tile (null, S, or $) and not visited → trapped
                if (!visited[i][j]) {
                    return true; // Player cannot reach this walkable tile
                }
            }
        }

        return false; // Player can reach all walkable tiles
    }

    /*
    Input: HMGameMove having information on player move
    Outputs: Integer array with the current row and column of the player
    Function: Function to find the location of player on the board GameMove state
     */
    public int[] playerLoc(HMGameMove move){
        int[] loc=new int[2];
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                if(boardArray[i][j]!=null){
                    boolean flag=false;
                    for(GamePiece pp:boardArray[i][j].getPieces()){
                        if(pp instanceof HeroPiece){
                            loc[0] = i;
                            loc[1] = j;
                            flag=true;
                            break;
                        }
                    }
                    if(flag){
                        break;
                    }
                }
            }
        }

        return loc;
    }

    /*
    Input: HMGameMove having information on player move
    Outputs: Integer array with the current row and column of the player
    Function: Function to find the location of player on player name
     */
    public int[] playerLoc(String playerName){
        int[] loc=new int[2];
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                if(boardArray[i][j]!=null && ((boardArray[i][j].getFirstPiece()) instanceof HeroPiece)){
                    if((boardArray[i][j].getFirstPiece().getOwner()).equals(playerName)) {
                        loc[0] = i;
                        loc[1] = j;
                        break;
                    }
                }
            }
        }
        return loc;
    }

    /*
    Input: HMGameMove having information on player move
    Outputs: boolean value conveying whether the move was successfully made
    Function: Function to make move on board
     */
    public boolean makeMove(HMGameMove move) {
        return applyMove(move);
    }

    /*
    Input: HMGameMove having information on player move
    Outputs: boolean value conveying whether the move was successfully applied
    Function: Function to apply the move on board
     */
    public boolean applyMove(HMGameMove move) {
        int[] locArr = playerLoc(move);
        int gotoRow=0, gotoCol=0;
        if(move.direction.equalsIgnoreCase("Left")) {
            gotoRow = locArr[0];
            gotoCol = locArr[1] - 1;
            if (gotoCol < 0) {
                return false;
            }
        }
        else if(move.direction.equalsIgnoreCase("Right")) {
            gotoRow = locArr[0];
            gotoCol = locArr[1] + 1;
            if (gotoCol > sizey - 1) {
                return false;
            }
        }
        else if(move.direction.equalsIgnoreCase("Up")) {
            gotoRow = locArr[0]-1;
            gotoCol = locArr[1];
            if(gotoRow < 0) {
                return false;
            }
        }
        else{ //Down
            gotoRow = locArr[0]+1;
            gotoCol = locArr[1];
            if(gotoRow > sizex - 1) {
                return false;
            }
        }


        if (boardArray[gotoRow][gotoCol] != null) {
            if (boardArray[gotoRow][gotoCol].getFirstPiece() instanceof ImmovableSpacePiece) {
                System.out.println("You cannot go to an immovable space");
                return false;
            }
        }
        List<GamePiece> pieces=boardArray[locArr[0]][locArr[1]].getPieces();
        int size=pieces.size();
//        HeroPiece pp=(HeroPiece) boardArray[locArr[0]][locArr[1]].popLastPiece();
        if(boardArray[gotoRow][gotoCol]==null) {
            GameSpace obj1 = new GameSpace();
            int k=0;
            for(int i=0;i<size;i++){
                if(pieces.get(k) instanceof HeroPiece){
                    GamePiece pp=boardArray[locArr[0]][locArr[1]].popIndex(k);
                    obj1.addPiece(pp);
                }
                else {
                    k++;
                }
            }
            boardArray[gotoRow][gotoCol] = obj1;
        }
        else {
            if(boardArray[gotoRow][gotoCol].getFirstPiece() instanceof MarketPlacePiece) {
                move.mktArr = true;
//                List<GamePiece> piecesAll=boardArray[locArr[0]][locArr[1]].getPieces();
                int k=0;
                for(int i=0;i<size;i++){
                    if(pieces.get(k) instanceof HeroPiece){
                        GamePiece pp=boardArray[locArr[0]][locArr[1]].popIndex(k);
                        ((boardArray[gotoRow][gotoCol])).addPiece(pp);
                    }
                    else {
                        k++;
                    }
                }
            }
        }
        if(boardArray[locArr[0]][locArr[1]].getPieces().size()==0){
            boardArray[locArr[0]][locArr[1]]=null;
        }
        return true;
    }
}
