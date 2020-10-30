package edu.msu.lukshani.proj1;

import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
//import android.graphics.Rect;
//import android.graphics.Shader;

public class Game {
    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 1;

    /**
     * The size of the puzzle in pixels
     */
    private int pixelSize;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Completed puzzle bitmap
     */
    private Bitmap GameComplete;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

    /**
     * Collection of puzzle pieces
     */
    public ArrayList<CheckerPiece> pieces = new ArrayList<CheckerPiece>();
    
    public Game(Context context){

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xff006400);

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

         pixelSize = (int)(minDim * SCALE_IN_VIEW);

        //new Canvas(bitmap);
        //Rect rect = new Rect(0, 0, pixelSize, pixelSize);
        //canvas.drawRect(rect, fillPaint);
        //rect.offset(pixelSize, pixelSize);
        //canvas.drawRect(rect, fillPaint);



        // Compute the margins so we center the puzzle
        marginX = (wid - pixelSize) / 2;
        marginY = (hit - pixelSize) / 2;

        int sq_wid = minDim/8;
        int sq_hit = minDim/8;

        //falg
        //for (i<6) - x asix
            //for(j<6) - y axis
//            canvas.drawRect(offsetX, offsetY, sec_hit, sec_wid, fillPaintSec);
        // Draw the outline of the puzzle
        //

        canvas.save();
        canvas.translate(marginX, marginY);

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
                        offset_x + sq_wid, offset_y + sq_hit, fillPaint);


            }

        }
        canvas.restore();

        //canvas.drawRect(offsetX, offsetY, sec_hit,  sec_wid, fillPaintSec);
        //scaleFactor = (float)pixelSize / (float)GameComplete.getWidth();

       // return paint;

    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / pixelSize;
        float relY = (event.getY() - marginY) / pixelSize;
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view, relX, relY);

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    //view.invalidate();
                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p =pieces.size()-1; p>=0;  p--) {
            if(pieces.get(p).hit(x, y, pixelSize, scaleFactor)) {
                // We hit a piece!
                dragging = pieces.get(p);
                lastRelX = x;
                lastRelY = y;
                return true;
            }
        }

        return false;
    }
    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch release, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onReleased(View view, float x, float y) {
        dragging.maybeSnap();
        if(dragging != null) {
            if(dragging.maybeSnap()) {
                // We have snapped into place
                view.invalidate();
            }
            dragging = null;
            return true;
        }

        return false;
    }


}
