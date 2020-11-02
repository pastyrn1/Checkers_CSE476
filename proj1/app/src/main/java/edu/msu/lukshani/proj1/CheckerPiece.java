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
     * The image for the kinged piece.
     */
    private Bitmap kinged;

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
     * checker piece id
     */
    private int id;

    /**
     * x index
     */
    private int x_idx = 0;

    /**
     * y index
     */
    private int y_idx = 0;

    /**
     * whether the piece is a king or not
     */
    private boolean isKing = false;

    /**
     * integer representing piece default movement direction
     */
    private int direction;

    /**
     * valid locations of checker squares
     */
    private float[] valid = {};

    public CheckerPiece(Context context, int id, int k_id, float[] valid, int posX, int posY, int direction) {
        this.x = valid[posX];
        this.y = valid[posY];

        this.x_idx = posX;//TODO
        this.y_idx = posY;

        this.id = id;

        this.direction = direction;

        this.valid = valid;

        piece = BitmapFactory.decodeResource(context.getResources(), id);
        kinged = BitmapFactory.decodeResource(context.getResources(), k_id);
    }

    /**
     * king the piece
     */
    public void king(){
        isKing = true;
        piece = kinged;
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
     * Move the checker piece to new_x, new_y
     * @param new_x x to move to
     * @param new_y y to move to
     */
    public void setPos(float new_x, float new_y) {
        x = new_x;
        y = new_y;
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
     * return piece x position
     */
    public float getX() {
        return x;
    }

    /**
     * return piece y position
     */
    public float getY() {
        return y;
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
     * return piece id
     */
    public int getId() {
        return id;
    }

    /**
     * return piece direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * return piece kinghood
     */
    public boolean getKing() {
        return isKing;
    }

    /**
     * Draw the checker piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the checker board in pixels
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Center of the piece at 0, 0
        canvas.translate(-(boardSize/8) / 2f, -(boardSize/8) / 2f);

        // Draw the bitmap
        canvas.drawBitmap(Bitmap.createScaledBitmap(piece, boardSize/8, boardSize/8, false), 0, 0, null);
        canvas.restore();
    }

    /**
     * Test to see if we have touched a checker piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param pixelSize the size of the checkerboard in pixels
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int pixelSize) {

        //TODO: make this range slightly wider so it isn't so finicky

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * pixelSize) +
                (pixelSize/7 / 2)+11;
        int pY = (int)((testY - y) * pixelSize) +
                (pixelSize/7 / 2)+11;

        if(pX < 0 || pX >= pixelSize/8 ||
                pY < 0 || pY >= pixelSize/8) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    public boolean equals(int[] idx) {
        if (idx[0] != x_idx || idx[1] != y_idx) {
            return false;
        }
        return true;
    }


}