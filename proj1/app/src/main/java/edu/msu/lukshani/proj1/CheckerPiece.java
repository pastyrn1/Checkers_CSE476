package edu.msu.lukshani.proj1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class CheckerPiece {
    /**
     * The image for the actual piece.
     */
    private Bitmap piece;

    /**
     * We consider a piece to be in a valid location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.05f;

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the checkers piece.
     */
    private float x = 0;

    /**
     * y location
     */
    private float y = 0;

    /**
     * whether the piece is a king or not
     */
    private boolean isKing = false;

    /**
     * valid locations of checker squares
     */
    private float[] valid = {};

    public CheckerPiece(Context context, int id, float[] valid, int posX, int posY) {
        this.x = valid[posX];
        this.y = valid[posY];

        this.valid = valid;

        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * Move the puzzle piece by dx, dy
     *
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Move the puzzle piece to new_x, new_y
     *
     * @param new_x x amount to move
     * @param new_y y amount to move
     */
    public void setPos(float new_x, float new_y) {
        x = new_x;
        y = new_y;
    }

    /**
     * Draw the checker piece
     *
     * @param canvas      Canvas we are drawing on
     * @param marginX     Margin x value in pixels
     * @param marginY     Margin y value in pixels
     * @param boardSize   Size we draw the checker board in pixels
     * @param scaleFactor Amount we scale the checker pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Center of the piece at 0, 0
        canvas.translate(-(boardSize / 8) / 2f, -(boardSize / 8) / 2f);

        // Draw the bitmap
        canvas.drawBitmap(Bitmap.createScaledBitmap(piece, boardSize / 8, boardSize / 8, false), 0, 0, null);
        canvas.restore();
    }

    /**
     * Test to see if we have touched a puzzle piece
     *
     * @param testX       X location as a normalized coordinate (0 to 1)
     * @param testY       Y location as a normalized coordinate (0 to 1)
     * @param pixelSize   the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int pixelSize, int boardSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int) (testX - x) * (boardSize / 8) / 2;
        int pY = (int) (testY - y) * (boardSize / 8) / 2;

        if (pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }
/*
    public int isValid(){
        return 1;
    }

 */
}
