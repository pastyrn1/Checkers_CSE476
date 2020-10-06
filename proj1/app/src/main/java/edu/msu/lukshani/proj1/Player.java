package edu.msu.lukshani.proj1;

import android.content.Context;

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
     * Collection of player's checker pieces
     */
    public ArrayList<CheckerPiece> pieces = new ArrayList<CheckerPiece>();

    public Player(Context context) {
        pieces.add(new CheckerPiece(context, R.drawable.green, new float[] {0.666f}, new float[] {0.158f})); //TODO: add actual location values for valid squares
    }
}
