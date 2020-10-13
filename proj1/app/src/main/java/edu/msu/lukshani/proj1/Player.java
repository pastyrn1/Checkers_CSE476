package edu.msu.lukshani.proj1;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Player {
    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * How much we scale the checker pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * The size of the board in pixels
     */
    private int boardSize;

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 1;

    /**
     * Collection of player's checker pieces
     */
    public ArrayList<CheckerPiece> pieces = new ArrayList<CheckerPiece>();

    public Player(Context context, boolean flip) {
        if (flip){
            //create upper pieces
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 8; j++){
                    if(i % 2 !=  j % 2) {
                        pieces.add(new CheckerPiece(context, R.drawable.white, new float[]{.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f}, j, i));
                    }
                }
            }

        } else {
            //create lower pieces
            for(int i = 5; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(i % 2 !=  j % 2) {
                        pieces.add(new CheckerPiece(context, R.drawable.green, new float[]{.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f}, j, i));
                    }
                }
            }

        }

    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int) (minDim); //TODO: multiply minDim by SCALE_IN_VIEW if decided we want it smaller than tot wid/hit

        marginX = (wid - boardSize) / 2;
        marginY = (hit - boardSize) / 2;

        for (CheckerPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, boardSize, scaleFactor);
        }
        
    }
}
