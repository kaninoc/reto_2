package co.edu.unal.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BoardView extends View {

    //Variables de clase
    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;

    private Paint mPaint;
    private TicTacToeGame mGame; // Declaración de la variable miembro mGame

    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void setGame(TicTacToeGame game) {
        mGame = game;
    }

    public int getBoardCellWidth() {
        return getWidth() / 3;
    }

    public int getBoardCellHeight() {
        return getHeight() / 3;
    }
    public void initialize() {
        mHumanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xplayer);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.oplayer);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Establecer el tamaño deseado del Canvas (300x300)
        float GRID_WIDTH = 5.0f;
        // Determine el ancho y alto del View
        int boardWidth = getWidth();
        int boardHeight = boardWidth;

        // Dibujar las líneas verticales
        int cellWidth = boardWidth / 3;
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(GRID_WIDTH);
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);

        // Dibujar las líneas horizontales
        int cellHeight = boardWidth / 3;
        canvas.drawLine(0, cellHeight, boardWidth, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);

        // Dibujar las imágenes "X" y "O" en el tablero

        for (int i = 0; i < 9; i++) {
            int col = i % 3;
            int row = i / 3;

            // Define las coordenadas del rectángulo de destino para la imagen
            int left = col * cellWidth;
            int top = row * cellHeight;
            int right = left + cellWidth;
            int bottom = top + cellHeight;

            if (mGame != null) {
                if (mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER) {
                    canvas.drawBitmap(mHumanBitmap, null, new Rect(left, top, right, bottom), null);
                } else if (mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER) {
                    canvas.drawBitmap(mComputerBitmap, null, new Rect(left, top, right, bottom), null);
                }
            }
        }
    }

}
