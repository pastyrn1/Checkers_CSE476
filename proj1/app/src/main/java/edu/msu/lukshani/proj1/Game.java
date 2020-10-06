package edu.msu.lukshani.proj1;

import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.Shader;

public class Game {
    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;

    /**
     * * paint for the square tiles
     */
    private Paint fillPaintSec;

    //private BitmapShader shader;

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 1;
    
    public Game(Context context){

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xff006400);

        fillPaintSec = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaintSec.setColor(0xffDCDCDC);
        //fillPaint.setStyle(Paint.Style.FILL);

        //Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    public void draw(Canvas canvas){

        //int wid = 2;
        //int hit = 2;

       // int pixelSize = wid * hit;

        //Bitmap bitmap = Bitmap.createBitmap(pixelSize * 2, pixelSize * 2, Bitmap.Config.ARGB_8888);


        //BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        //Paint paint = new Paint();
        //paint.setShader(shader);

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();


        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int pixelSize = (int)(minDim * SCALE_IN_VIEW);

        //new Canvas(bitmap);
        //Rect rect = new Rect(0, 0, pixelSize, pixelSize);
        //canvas.drawRect(rect, fillPaint);
        //rect.offset(pixelSize, pixelSize);
        //canvas.drawRect(rect, fillPaint);



        // Compute the margins so we center the puzzle
        int marginX = (wid - pixelSize) / 2;
        int marginY = (hit - pixelSize) / 2;

        int sec_wid = 100;
        int sec_hit = 100;

        int offsetX = (sec_wid - pixelSize) / 2;
        int offsetY = (sec_hit - pixelSize) / 2;


        //
        // Draw the outline of the puzzle
        //

        canvas.drawRect(marginX, marginY,
                 marginX+ pixelSize,  marginY + pixelSize, fillPaint);

        canvas.drawRect(offsetX, offsetY, sec_hit,  sec_wid, fillPaintSec);

       // return paint;

    }
}
