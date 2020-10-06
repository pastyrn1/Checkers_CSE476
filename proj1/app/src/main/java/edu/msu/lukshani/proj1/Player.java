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
     * Collection of player's checker pieces
     */
    public ArrayList<CheckerPiece> pieces = new ArrayList<CheckerPiece>();

    public Player(Context context, boolean flip) {
        if (flip){
            pieces.add(new CheckerPiece(context, R.drawable.white, new float[] {0.666f}, new float[] {0.158f})); //TODO: add actual location values for valid squares
        } else {
            pieces.add(new CheckerPiece(context, R.drawable.green, new float[] {0.666f}, new float[] {0.158f})); //TODO: add actual location values for valid squares
        }

    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int) (minDim); //TODO: multiply minDim by SCALE_IN_VIEW

        //scaleFactor = (float)boardSize / (float)puzzleComplete.getWidth();

        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

        for (CheckerPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, boardSize, .3f);//TODO: change scaleFactor so that it isn't hard-coded
        }
    }
}
