package edu.msu.pastyrn1.project2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class CheckerPiece {
    /**
     * The image for the checker piece.
     */
    private Bitmap piece;

    /**
     * The image for a kinged piece.
     */
    private Bitmap k_piece;

    /**
     * x index
     */
    private int x_idx = 0;

    /**
     * y index
     */
    private int y_idx = 0;

    /**
     * integer representing piece default movement direction
     */
    private int direction;

    /**
     * whether the piece is a king or not
     */
    private boolean isKing = false;

    /**
     * Valid locations of checker squares
     */
    private float[] valid = {.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f};

    public CheckerPiece(Context context, int piece, int k_piece, int posX, int posY, int direction) {
        this.x_idx = posX;
        this.y_idx = posY;

        this.direction = direction;

        this.piece = BitmapFactory.decodeResource(context.getResources(), piece);
        this.k_piece = BitmapFactory.decodeResource(context.getResources(), k_piece);
    }

    /**
     * king the piece
     */
    public void king(){
        isKing = true;
        piece = k_piece;
    }

    /**
     * return piece king status
     */
    public boolean getKing() {
        return isKing;
    }

    /**
     * Move the checker piece to new_idx, new_idy
     * @param new_idx
     * @param new_idy
     */
    public void setIdx(int new_idx, int new_idy) {
        x_idx = new_idx;
        y_idx = new_idy;
    }

    /**
     * return piece x index
     */
    public int getXIdx() {
        return x_idx;
    }

    /**
     * return piece y index
     */
    public int getYIdx() {
        return y_idx;
    }

    /**
     * return piece direction
     */
    public int getDirection() {
        return direction;
    }


    /**
     * Draw the checker piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param gameSize Size we draw the checker board in pixels
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int gameSize) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + valid[x_idx] * gameSize,
                marginY + valid[y_idx] * gameSize);

        // Center of the piece at 0, 0
        canvas.translate(-(gameSize/8) / 2f, -(gameSize/8) / 2f);

        // Draw the bitmap
        canvas.drawBitmap(Bitmap.createScaledBitmap(piece, gameSize/8,
                gameSize/8, false), 0, 0, null);

        canvas.restore();
    }

    /**
     * Test to see if we have touched a checker piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param gameSize the size of the checkerboard in pixels
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int gameSize) {
        // Make relative to the location and size to the [piece] size
        int pX = (int)((testX - valid[x_idx]) * gameSize) +
                (gameSize/8 / 2);
        int pY = (int)((testY - valid[y_idx]) * gameSize) +
                (gameSize/8 / 2);

        // Are we within the rectangle of the tile
        return pX >= 0 && pX < gameSize / 8 && pY >= 0 && pY < gameSize / 8;

        // We are within the rectangle of the piece.
        // Are we touching actual picture? - commented this out,
        // since it further narrows an already tight hit-box
        //return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Checks if the indexes of two pieces are equal
     * @param idx index of non "this" piece
     * @return
     */
    public boolean equals(int[] idx) {
        if (idx[0] != x_idx || idx[1] != y_idx) {
            return false;
        }
        return true;
    }

}
