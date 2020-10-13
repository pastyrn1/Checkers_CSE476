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
     * x locations of checker squares
     */
    private float[] validX = {};

    /**
     * y locations of checker squares, each corresponds to an x value at equal index in validX
     */
    private float[] validY = {};

    public CheckerPiece(Context context, int id, float[] validX, float[] validY, int posX, int posY) {
        this.x = validX[posX];
        this.y = validY[posY];

        this.validX = validX;
        this.validY = validY;

        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * Move the puzzle piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Draw the checker piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the checker board in pixels
     * @param scaleFactor Amount we scale the checker pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Scale it to the right size
        //canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        //canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }

}
