package co.edu.unal.tictactoe;

import java.util.Random;
public class TicTacToeGame {

    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    private char[] mBoard;
    private Random mRand;
    private final int BOARD_SIZE = 9;

    // Los niveles de dificultad de la computadora
    public enum DifficultyLevel { Easy, Hard, Expert };
    // Nivel de dificultad actual
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Easy;

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }
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
    public char getBoardOccupant(int location) {
        return mBoard[location];
    }
    public int getComputerMove() {
        int move = -1;
        if (mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();
        else if (mDifficultyLevel == DifficultyLevel.Hard) {
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        }
        else if (mDifficultyLevel == DifficultyLevel.Expert) {
            // Intenta ganar, pero si eso no es posible, bloquea.
            // Si eso no es posible, mueve en cualquier lugar.
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }
        return move;
    }
    public int getRandomMove() {
        int move;
        do {
            move = mRand.nextInt(9);
        } while (mBoard[move] != OPEN_SPOT);
        return move;
    }

    public int getWinningMove() {
        // Buscar una jugada ganadora para la computadora ('O')
        for (int i = 0; i < mBoard.length; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                // Guardar el valor original de la casilla
                char originalValue = mBoard[i];

                // Simular un movimiento de la computadora
                mBoard[i] = COMPUTER_PLAYER;

                // Verificar si este movimiento resulta en una victoria de la computadora
                if (checkForWinner() == 3) {
                    mBoard[i] = originalValue; // Restaurar el estado original
                    return i;
                }

                // Restaurar el estado original
                mBoard[i] = originalValue;
            }
        }

        // Si no hay movimientos ganadores, devolver -1
        return -1;
    }

    public int getBlockingMove() {
        // Buscar una jugada para bloquear al jugador humano ('X')
        for (int i = 0; i < mBoard.length; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                // Guardar el valor original de la casilla
                char originalValue = mBoard[i];

                // Simular un movimiento del jugador humano
                mBoard[i] = HUMAN_PLAYER;

                // Verificar si este movimiento bloquea al jugador humano
                if (checkForWinner() == 2) {
                    mBoard[i] = originalValue; // Restaurar el estado original
                    return i;
                }

                // Restaurar el estado original
                mBoard[i] = originalValue;
            }
        }

        // Si no hay movimientos de bloqueo, devolver -1
        return -1;
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