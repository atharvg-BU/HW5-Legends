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

    public void setInitialPieces(int marketNum, int inaccNum, String[] heroInitials, List<String> playerNames){
        Random rand = new Random();
        // Place markets
        do {
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
                    if(isPlayerTrapped(r,c,inaccNum)){
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

    private boolean isPlayerTrapped(int sr, int sc,int inaccNum) {
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        boolean[][] visited = new boolean[sizex][sizey];
        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];

                if (nr < 0 || nc < 0 || nr >= sizex || nc >= sizey)
                    continue;

                // Walkable = null or Market or another S
                if (boardArray[nr][nc] != null &&
                        boardArray[nr][nc].getFirstPiece() instanceof ImmovableSpacePiece)
                    continue;

                if (!visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        // If S has at least one walkable adjacent neighbor -> not trapped
        for (int[] d : dirs) {
            int nr = sr + d[0];
            int nc = sc + d[1];
            if (nr < 0 || nc < 0 || nr >= sizex || nc >= sizey) continue;
            if (visited[nr][nc]) return false;
        }
        int c=0;
        for(int i=0;i<visited.length;i++){
            for(int j=0;j<visited[i].length;j++){
                if(visited[i][j]){
                    c++;
                }
            }
            if(c==((sizex*sizey)-inaccNum)){
                return false;
            }
            else {
                return true;
            }
        }

        return true;  // Hero is blocked by X
    }


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

    public int[] playerLoc(String playerName){
        int[] loc=new int[2];
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                if(boardArray[i][j]!=null && ((boardArray[i][j].getFirstPiece()) instanceof HeroPiece)){
                    if((boardArray[i][j].getFirstPiece().getOwner()).equals(playerName)) {
                        System.out.println(playerName);
                        System.out.println(boardArray[i][j].getFirstPiece().getOwner());
                        loc[0] = i;
                        loc[1] = j;
                        break;
                    }
                }
            }
        }
        return loc;
    }

    public List<String> getPlayersOnSameTile(String name){
        int[] loc=playerLoc(name);
        List<String> pNames=new ArrayList<>();
        for (GamePiece p:boardArray[loc[0]][loc[1]].getPieces()){
            pNames.add(p.getOwner());
        }
        return pNames;
    }

    public boolean makeMove(HMGameMove move) {
        return applyMove(move);
    }

    public boolean applyMove(HMGameMove move) {
        int[] locArr = playerLoc(move);
        System.out.println(Arrays.toString(locArr));
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
        System.out.println(Arrays.toString(locArr));
        List<GamePiece> pieces=boardArray[locArr[0]][locArr[1]].getPieces();
        int size=pieces.size();
        System.out.println(pieces.size());
//        HeroPiece pp=(HeroPiece) boardArray[locArr[0]][locArr[1]].popLastPiece();
        if(boardArray[gotoRow][gotoCol]==null) {
            GameSpace obj1 = new GameSpace();
            int k=0;
            for(int i=0;i<size;i++){
                System.out.println(pieces.get(k) instanceof HeroPiece);
                if(pieces.get(k) instanceof HeroPiece){
                    GamePiece pp=boardArray[locArr[0]][locArr[1]].popIndex(k);
                    obj1.addPiece(pp);
                }
                else {
                    k++;
                }
            }
            boardArray[gotoRow][gotoCol] = obj1;
            System.out.println( boardArray[locArr[0]][locArr[1]]);
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
        System.out.println(boardArray[locArr[0]][locArr[1]].getPieces().size());
        if(boardArray[locArr[0]][locArr[1]].getPieces().size()==0){
            boardArray[locArr[0]][locArr[1]]=null;
        }
        return true;
    }
}
