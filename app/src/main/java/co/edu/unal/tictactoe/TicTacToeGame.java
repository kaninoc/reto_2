package co.edu.unal.tictactoe;


import android.view.Menu;

import java.util.Random;
public class TicTacToeGame {

    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    private char[] mBoard;
    private Random mRand;
    private final int BOARD_SIZE = 9;

    public TicTacToeGame() {

        // Seed the random number generator
        mBoard = new char[9];
        mRand = new Random();
        clearBoard();

    }


    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            mBoard[i] = OPEN_SPOT;
        }
        for (int i = 0; i < mBoard.length; i++) {
            System.out.println("Elemento " + i + ": " + mBoard[i]);
        }
    }

    public void setMove(char player, int location) {
        if (location >= 0 && location < 9 && mBoard[location] == OPEN_SPOT) {
            mBoard[location] = player;
        }
    }

    public int getComputerMove() {
        int move;
        do {
            move = mRand.nextInt(9);
        } while (mBoard[move] != OPEN_SPOT);
        return move;
    }

    public int checkForWinner() {
        for (int i = 0; i < 8; i++) {
            int winCombo[] = {0, 0, 0};
            switch (i) {
                case 0:
                    winCombo = new int[]{0, 1, 2};
                    break;
                case 1:
                    winCombo = new int[]{3, 4, 5};
                    break;
                case 2:
                    winCombo = new int[]{6, 7, 8};
                    break;
                case 3:
                    winCombo = new int[]{0, 3, 6};
                    break;
                case 4:
                    winCombo = new int[]{1, 4, 7};
                    break;
                case 5:
                    winCombo = new int[]{2, 5, 8};
                    break;
                case 6:
                    winCombo = new int[]{0, 4, 8};
                    break;
                case 7:
                    winCombo = new int[]{2, 4,6};
                    break;
            }

            if (mBoard[winCombo[0]] == HUMAN_PLAYER &&
                    mBoard[winCombo[1]] == HUMAN_PLAYER &&
                    mBoard[winCombo[2]] == HUMAN_PLAYER) {
                return 2;
            } else if (mBoard[winCombo[0]] == COMPUTER_PLAYER &&
                    mBoard[winCombo[1]] == COMPUTER_PLAYER &&
                    mBoard[winCombo[2]] == COMPUTER_PLAYER) {
                return 3;
            }
        }

        // Si no hay ganador, verifica si hay un empate o si el juego continúa
        for (int i = 0; i < 9; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                return 0; // El juego continúa
            }
        }
        return 1; // Empate
    }
}