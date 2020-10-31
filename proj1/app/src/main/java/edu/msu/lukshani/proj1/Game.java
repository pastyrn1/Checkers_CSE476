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
     * We consider a piece to be in a valid location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.05f;

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
     * valid locations of checker squares
     */
    private float[] valid = {.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f};

    /**
     * boolean for whose turn it is
     */
    private boolean isTurnPlayer1 = true;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

    /**
     * Collection of puzzle pieces
     */
    public ArrayList<CheckerPiece> player1_pieces = new ArrayList<CheckerPiece>();
    public ArrayList<CheckerPiece> player2_pieces = new ArrayList<CheckerPiece>();
    
    public Game(Context context){

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xff006400);

        //create lower pieces
        for(int i = 5; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {
                    player1_pieces.add(new CheckerPiece(context, R.drawable.green, valid, j, i, -1));
                }
            }
        }

        //create upper pieces
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {
                    player2_pieces.add(new CheckerPiece(context, R.drawable.white, valid, j, i, 1));
                }
            }
        }
    }

    public void draw(Canvas canvas){

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

         pixelSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        marginX = (wid - pixelSize) / 2;
        marginY = (hit - pixelSize) / 2;

        int sq_wid = minDim/8;
        int sq_hit = minDim/8;

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

        for (CheckerPiece piece : player1_pieces) {
            piece.draw(canvas, marginX, marginY, pixelSize, scaleFactor);
        }

        for (CheckerPiece piece : player2_pieces) {
            piece.draw(canvas, marginX, marginY, pixelSize, scaleFactor);
        }

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
        for(int p1 = player1_pieces.size()-1; p1>=0;  p1--) {
            if(player1_pieces.get(p1).hit(x, y, pixelSize, scaleFactor)) {
                // We hit player 1 piece!
                dragging = player1_pieces.get(p1);
                lastRelX = x;
                lastRelY = y;
                return true;
            }

        }
        for(int p2 = player2_pieces.size()-1; p2>=0;  p2--) {
            if(player2_pieces.get(p2).hit(x, y, pixelSize, scaleFactor)) {
                // We hit player2 piece!
                dragging = player2_pieces.get(p2);
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
        isValid();
        if(dragging != null) {
            if(isValid() == 1) {
                //The movement is valid
                isTurnPlayer1 = !isTurnPlayer1;
                view.invalidate();
            } else if(isValid() == 0){
                dragging.setPos(lastRelX, lastRelY);
            } else if(isValid() == -1){
                //The movement is valid
                view.invalidate();
                //TODO:add double jump code
            }
            dragging = null;
            return true;
        }

        return false;
    }

    public int isValid(){

        float x = dragging.getX();
        float y = dragging.getY();

        int xIdx = dragging.getXIdx();
        int yIdx = dragging.getYIdx();

        int d = dragging.getDirection();

        boolean[] opponent = {false, false, false, false};
        CheckerPiece[] deletable = {null, null};

        if(!isTurnPlayer1){
            for(CheckerPiece piece: player1_pieces){
                if (piece.equals(new int[]{xIdx - 1, yIdx + d})){
                    opponent[0] = true;
                    deletable[0] = piece;
                } else if (piece.equals(new int[]{xIdx + 1, yIdx + d})){
                    opponent[1] = true;
                    deletable[1] = piece;
                } else if (piece.equals(new int[]{xIdx - 2, yIdx + d * 2})){
                    opponent[3] = true;
                } else if (piece.equals(new int[]{xIdx + 2, yIdx + d * 2})){
                    opponent[4] = true;
                }
            }
        } else {
            for(CheckerPiece piece: player2_pieces){
                if (piece.equals(new int[]{xIdx - 1, yIdx + d})){
                    opponent[0] = true;
                    deletable[0] = piece;
                } else if (piece.equals(new int[]{xIdx + 1, yIdx + d})){
                    opponent[1] = true;
                    deletable[1] = piece;
                } else if (piece.equals(new int[]{xIdx - 2, yIdx + d * 2})){
                    opponent[3] = true;
                } else if (piece.equals(new int[]{xIdx + 2, yIdx + d * 2})){
                    opponent[4] = true;
                }
            }
        }

        //TODO: add 'hypothetical' boolean to the function call to use for determining if a double jump is possible
        //move one space
        if(oneSpace(x, y, xIdx, yIdx, d, -1, opponent[0]) || oneSpace(x, y, xIdx, yIdx, d, 1, opponent[1])){
            return 1;
        }

        if(twoSpace(x, y, xIdx, yIdx, d, -1, opponent[2], opponent[0], deletable[0]) || twoSpace(x, y, xIdx, yIdx, d, 1, opponent[3], opponent[1], deletable[1])){
            return 1;
        }

        if(dragging.getKing()){
            if(oneSpace(x, y, xIdx, yIdx, -1 * d, -1, opponent[0]) || oneSpace(x, y, xIdx, yIdx, -1 * d, 1, opponent[1])){
                return 1;
            }
            if(twoSpace(x, y, xIdx, yIdx, -1 * d, -1, opponent[2], opponent[0], deletable[0]) || twoSpace(x, y, xIdx, yIdx, -1 * d, 1, opponent[3], opponent[1], deletable[1])){
                return 1;
            }
        }

        //TODO: check if is in final row, if so king

        //TODO: check if one array is empty - if so trigger win condition

        return 0;
    }

    public boolean oneSpace(float x, float y, int xIdx, int yIdx, int yD, int xD, boolean opponent){
        if (xIdx - xD < 0 || xIdx - xD > 7 || yIdx + yD < 0 ||  yIdx + yD > 7){
            return false;
        }

        if(Math.abs(x - valid[xIdx + xD]) < SNAP_DISTANCE &&
                Math.abs(y - valid[yIdx + yD]) < SNAP_DISTANCE && !opponent) {
            dragging.setPos(valid[xIdx + xD], valid[yIdx + yD]);
            return true;
        }
        return false;
    }

    public boolean twoSpace(float x, float y, int xIdx, int yIdx, int yD, int xD, boolean opponent, boolean victim, CheckerPiece deletable){
        if (xIdx - xD * 2 < 0 || xIdx - xD * 2 > 7 || yIdx + yD * 2 < 0 ||  yIdx + yD * 2 > 7){
            return false;
        }

        if(Math.abs(x - valid[xIdx - xD * 2]) < SNAP_DISTANCE &&
                Math.abs(y - valid[yIdx + yD * 2]) < SNAP_DISTANCE && !opponent && victim) {
            if (!isTurnPlayer1) {
                player1_pieces.remove(deletable);
            } else {
                player2_pieces.remove(deletable);
            }
            dragging.setPos(valid[xIdx - xD * 2], valid[yIdx + yD * 2]);
            return true;
        }
        return false;
    }


}
