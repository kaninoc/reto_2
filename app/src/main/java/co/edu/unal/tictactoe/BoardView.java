package co.edu.unal.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
        Log.d("BoardView", "onDraw called");
        super.onDraw(canvas);
        // Establecer el tamaño deseado del Canvas (300x300)
        float GRID_WIDTH = 1.0f;
        // Determine el ancho y alto del View
        int boardWidth = getWidth();
        int boardHeight = boardWidth;

        // Dibujar las líneas verticales
        int cellWidth = boardWidth / 3;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(GRID_WIDTH);
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);

        // Dibujar las líneas horizontales
        int cellHeight = boardWidth / 3;
        canvas.drawLine(0, cellHeight, boardWidth, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);

        /*int left = 2*cellWidth;
        int top = 0;
        int right = 3*cellWidth;
        int bottom = cellWidth;

        canvas.drawBitmap(mHumanBitmap,null,new Rect(left, top, right, bottom),null);

        Log.d("MyApp", "left: " + left);
        Log.d("MyApp", "top: " + top);
        Log.d("MyApp", "right: " + right);
        Log.d("MyApp", "bottom: " + bottom);*/
        if (mGame != null){
        for (int i = 0; i < mGame.BOARD_SIZE; i++) {
            int col = i % 3;
            int row = i / 3;
            char c = mGame.getBoardOccupant(i);
            Log.d("MyApp", "m value:  " + c);
        }
            /*if (mGame.getBoardOccupant(i)=='X'){
                Log.d("MyApp", "col: " + col);
                Log.d("MyApp", "row: " + row);
                Log.d("MyApp", "i: " + i);
            }*/

            //Log.d("MyApp", "row: " + row);

            // Define las coordenadas del rectángulo de destino para la imagen
            /*int left = col * cellWidth;
            int top = row * cellHeight;
            int right = left + cellWidth;
            int bottom = top + cellHeight;

            Log.d("BoardView", "onDraw called"+left);
            Log.d("BoardView", "onDraw called"+top);
            Log.d("BoardView", "onDraw called"+right);
            Log.d("BoardView", "onDraw called"+bottom);

            if (mGame != null) {
                if (mGame.getBoardOccupant(i) == TicTacToeGame.HUMAN_PLAYER) {
                    canvas.drawBitmap(mHumanBitmap, null, new Rect(left, top, right, bottom), null);
                } else if (mGame.getBoardOccupant(i) == TicTacToeGame.COMPUTER_PLAYER) {
                    canvas.drawBitmap(mComputerBitmap, null, new Rect(left, top, right, bottom), null);
                }
            }*/
        }else{
            Log.d("MyApp", "m value:  " + "Falso");
        }
    }

}
