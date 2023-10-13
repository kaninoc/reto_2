package co.edu.unal.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BoardView extends View {

    //Variables de clase
    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;

    private Paint mPaint;


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

    public void initialize() {
        mHumanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xplayer);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.oplayer);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Establecer el tamaño deseado del Canvas (300x300)
        int canvasSize = 300;
        float GRID_WIDTH = 5.0f;
        setMeasuredDimension(canvasSize, canvasSize);

        // Determine el ancho y alto del View
        int boardWidth = canvasSize;
        int boardHeight = canvasSize;

        // Dibujar las líneas verticales
        int cellWidth = boardWidth / 3;
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(GRID_WIDTH);
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);

        // Dibujar las líneas horizontales
        int cellHeight = boardHeight / 3;
        canvas.drawLine(0, cellHeight, boardWidth, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);
    }

}
