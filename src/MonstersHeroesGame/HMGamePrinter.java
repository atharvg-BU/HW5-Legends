package MonstersHeroesGame;

import Parent.GamePiece;
import Parent.GamePrinter;
import Parent.GameSpace;

import java.util.Arrays;
import java.util.List;

public class HMGamePrinter extends GamePrinter {
    public void printBoard(GameSpace[][] board){
        for(int row=0;row<board.length;row++){
            System.out.print("+");
            for(int col=0;col<board[row].length;col++){
                System.out.print("---------+");
            }
            System.out.println();
            for(int col=0;col<board[row].length;col++){
                if(board[row][col] != null){
                    GameSpace gs=board[row][col];
                    List<GamePiece> pieces=gs.getPieces();
                    if(pieces.size()==1){
                        System.out.print("|    "+(((GameSpace)board[row][col]).getFirstPiece()).icon+"    ");
                    }
                    if(pieces.size()==2){
                        System.out.print("|   ");
                        for(GamePiece p:pieces){
                            System.out.print(p.icon+" ");
                        }
                        System.out.print("  ");
                    }
                    if(pieces.size()==3){
                        System.out.print("| ");
                        for(GamePiece p:pieces){
                            System.out.print(p.icon+" ");
                        }
                    }

                }
                else {
                    System.out.print("|         ");
                }
            }
            System.out.println("|");
        }
        for(int row=0;row<board.length;row++){
            System.out.print("+");
            for(int col=0;col<board[row].length;col++){
                System.out.print("---------+");
            }
            System.out.println();
            break;
        }

    }

}
