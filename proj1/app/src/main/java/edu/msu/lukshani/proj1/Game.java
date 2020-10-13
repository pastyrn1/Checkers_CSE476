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

        int sq_wid = minDim/8;
        int sq_hit = minDim/8;

        //falg
        //for (i<6) - x asix
            //for(j<6) - y axis
//            canvas.drawRect(offsetX, offsetY, sec_hit, sec_wid, fillPaintSec);
        // Draw the outline of the puzzle
        //

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j ++)
            {
                int offset_x = sq_wid * j;
                int offset_y = sq_hit * i;

                if ((i % 2) == (j % 2))
                {
                    fillPaint.setColor(0xff006400);
                }
                else
                {
                    fillPaint.setColor(0xffDCDCDC);
                }
                canvas.drawRect(offset_x, offset_y,
                        sq_wid,  sq_hit, fillPaint);
            }
        }



        //canvas.drawRect(offsetX, offsetY, sec_hit,  sec_wid, fillPaintSec);

       // return paint;

    }
}
