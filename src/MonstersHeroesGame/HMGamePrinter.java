package MonstersHeroesGame;

import Parent.GamePiece;
import Parent.GamePrinter;
import Parent.GameSpace;

import java.util.Arrays;
import java.util.List;

public class HMGamePrinter extends GamePrinter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";


    public void printBoard(GameSpace[][] board) {
        for (int row = 0; row < board.length; row++) {
            System.out.print(ANSI_RESET + "+");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(ANSI_RESET + "---------+");
            }
            System.out.println(ANSI_RESET);
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    GameSpace gs = board[row][col];
                    List<GamePiece> pieces = gs.getPieces();
                    if (pieces.size() == 1) {
                        System.out.print(ANSI_RESET + "|    ");
                        if (pieces.get(0) instanceof ImmovableSpacePiece) {
                            System.out.print(ANSI_RED + (((GameSpace) board[row][col]).getFirstPiece()).icon + "    ");
                        } else if (pieces.get(0) instanceof MarketPlacePiece) {
                            System.out.print(ANSI_YELLOW + (((GameSpace) board[row][col]).getFirstPiece()).icon + "    ");
                        } else {
                            System.out.print(ANSI_BLUE + (((GameSpace) board[row][col]).getFirstPiece()).icon + "    ");
                        }
                    }
                    if (pieces.size() == 2) {
                        System.out.print("|   ");
                        for (GamePiece p : pieces) {
                            if (p instanceof MarketPlacePiece) {
                                System.out.print((ANSI_YELLOW + p.icon) + " ");
                            } else {
                                System.out.print((ANSI_BLUE + p.icon) + " ");
                            }
                        }
                        System.out.print(ANSI_RESET + "  ");
                    }
                    if (pieces.size() == 3) {
                        System.out.print(ANSI_RESET + "|  ");
                        for (GamePiece p : pieces) {
                            if (p instanceof MarketPlacePiece) {
                                System.out.print((ANSI_YELLOW + p.icon) + " ");
                            } else {
                                System.out.print((ANSI_BLUE + p.icon) + " ");
                            }
                        }
                        System.out.print(ANSI_RESET + " ");
                    }

                    if (pieces.size() == 4) {
                        System.out.print(ANSI_RESET + "| ");
                        for (GamePiece p : pieces) {
                            if (p instanceof MarketPlacePiece) {
                                System.out.print((ANSI_YELLOW + p.icon) + " ");
                            } else {
                                System.out.print((ANSI_BLUE + p.icon) + " ");
                            }
                        }
                    }

                } else {
                    System.out.print(ANSI_RESET+"|         ");
                }
            }
            System.out.println(ANSI_RESET+"|");
        }
        for (int row = 0; row < board.length; row++) {
            System.out.print(ANSI_RESET+"+");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(ANSI_RESET+"---------+");
            }
            System.out.println(ANSI_RESET);
            break;
        }
    }

}








