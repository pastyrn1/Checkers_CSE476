package edu.msu.pastyrn1.project2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Game {
    /**
     * Paint for filling the area the checkerboard is in
     */
    private Paint fillPaint;

    /**
     * The size of the board in pixels
     */
    private int gameSize;

    /**
     * We consider a piece to be on a valid tile location if within
     * this distance.
     */
    final static float SNAP_DISTANCE = 0.1f;

    /**
     * Left margin in pixels
     */
    private int marginX;

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
     * win condition (1 - player 1, 2 - player 2, 0 - Neither)
     */
    private int win = 0;

    /**
     * A boolean for whose turn it is
     */
    private boolean myTurn = true;

    /**
     * Indices of dragging before move attempt
     */
    private int startXIdx, startYIdx;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

    /**
     * This variable is set to a piece capable of a second or third jump. If
     * the player is not multi jumping, the variable is null.
     */
    private CheckerPiece jumper = null;

    /**
     * Collection of checker pieces for each player
     */
    public ArrayList<CheckerPiece> userPieces = new ArrayList<CheckerPiece>();
    public ArrayList<CheckerPiece> enemyPieces = new ArrayList<CheckerPiece>();

    private Context context;




    //TODO: makes these variables obsolete
    /**
     * Valid locations of checker squares
     */
    private float[] valid = {.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f};

    /**
     * A boolean for if a valid turn has been completed
     */
    private boolean isTurnComplete = false;

    /**
     * The name of the bundle keys to save the game
     */
    private final static String COMPLETE = "Game.complete";
    private final static String P1LOCATIONS = "Game.p1locations";
    private final static String P2LOCATIONS = "Game.p2locations";
    private final static String P1KINGS = "Game.p1kings";
    private final static String P2KINGS = "Game.p2kings";
    private final static String P1YLOCATIONS = "Game.p1ylocations";
    private final static String P2YLOCATIONS = "Game.p2ylocations";

    /**
     * player number of user
     */
    private int player;


    public Game(Context context, int player){
        this.context = context;

        // Create paint for filling the game board
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //TODO: check this isn't necessary here: fillPaint.setColor(0xff006400);


        //TODO: set player
        //int player = 1;
        this.player = player;

        // Create lower pieces (user)
        for(int i = 5; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {

                    if(player == 1){
                        userPieces.add(new CheckerPiece(context, R.drawable.green, R.drawable.spartan_green, j, i, -1));
                    } else {
                        userPieces.add(new CheckerPiece(context, R.drawable.white, R.drawable.spartan_white, j, i, 1));
                    }

                }
            }
        }

        // Create upper pieces (opponent)
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {

                    if(player == 1){
                        enemyPieces.add(new CheckerPiece(context, R.drawable.white, R.drawable.spartan_white, j, i, 1));
                    } else {
                        enemyPieces.add(new CheckerPiece(context, R.drawable.green, R.drawable.spartan_green, j, i, -1));
                    }

                }
            }
        }
    }

    /**
     * Draw the checker game
     * @param canvas Canvas we are drawing on
     */
    public void draw(Canvas canvas){
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        gameSize = wid < hit ? wid : hit;

        // Compute the margins so we center the game board
        marginX = (wid - gameSize) / 2;
        marginY = (hit - gameSize) / 2;

        int sq_wid = gameSize/8;
        int sq_hit = gameSize/8;

        canvas.save();
        canvas.translate(marginX, marginY);

        // Draw the board
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j ++)
            {
                int offset_x = sq_wid * j;
                int offset_y = sq_hit * i;

                if ((i % 2) == (j % 2)) { fillPaint.setColor(0xff006400);}
                else { fillPaint.setColor(0xffDCDCDC);}

                canvas.drawRect(offset_x, offset_y,
                        offset_x + sq_wid, offset_y + sq_hit, fillPaint);

            }
        }
        canvas.restore();

        // Draw the pieces, user player first
        for (CheckerPiece piece : userPieces) {
            piece.draw(canvas, marginX, marginY, gameSize);
        }

        for (CheckerPiece piece : enemyPieces) {
            piece.draw(canvas, marginX, marginY, gameSize);
        }
    }

    /**
     * Show toast message
     * @param message the message shown
     */
    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        // Convert an x,y location to a relative location in the game board
        /*float relX = (event.getX() - marginX) / gameSize;
        float relY = (event.getY() - marginY) / gameSize;
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
                    view.invalidate();
                    return true;
                }
                break;
        }*/
        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the game board - 0 to 1 over the game board
     * @param y y location for the touch, relative to the game board - 0 to 1 over the game board
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order for reasons
        if(myTurn && !isTurnComplete){
            for(int p = userPieces.size()-1; p>=0;  p--) {
                if(userPieces.get(p).hit(x, y, gameSize)) {
                    // We hit current player piece!
                    if(jumper == null || userPieces.get(p) == jumper) {
                        // Set dragging to touched piece
                        dragging = userPieces.get(p);

                        // Record piece's start index
                        startXIdx = dragging.getXIdx();
                        startYIdx = dragging.getYIdx();

                        // Move dragged piece to top of ArrayList
                        userPieces.set(p, userPieces.get(userPieces.size() - 1));
                        userPieces.set(userPieces.size() - 1, dragging);

                        // Record relative position for calculating visual movement of piece
                        lastRelX = x;
                        lastRelY = y;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the game board - 0 to 1 over the game board
     * @param y y location for the touch release, relative to the game board - 0 to 1 over the game board
     * @return true if the touch is handled
     */
    private boolean onReleased(View view, float x, float y) {
        /*if(dragging != null) {
            int v = isValid();
            if(v == 1) {
                //The movement is valid
                jumper = null;
                isTurnComplete = true;
                view.invalidate();
            } else if(v == -1){
                //The movement is valid, a multi jump has begun
                jumper = dragging;
                view.invalidate();
            } else {
                dragging.setIdx(startXIdx, startYIdx);
                showToast("Invalid Move");
                view.invalidate();
            }
            dragging = null;
            checkWin();
            return true;
        }*/

        return false;
    }

    /** TODO: modify this for p2
     * return turn status
     */
    public boolean getTurn() { return myTurn; }

    /** TODO: modify this for p2
     * set whose turn it is
     */
    public void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /** TODO: modify this for p2
     * set a turn as completed (no further movement possible)
     */
    public void setTurnComplete(boolean isTurnComplete){
        this.isTurnComplete = isTurnComplete;
    }

    /** TODO: modify this for p2
     * return win status
     */
    public int getWin() {
        return win;
    }

    /** TODO: modify this for p2
     * set win status
     */
    public void setWin(int win) {
        this.win = win;
    }

    /**
     * check if a player has won
     */
    public void checkWin(){
        if (enemyPieces.isEmpty()){
            win = 1;
        } else if (userPieces.isEmpty()){
            win = 2;
        }
    }

    private class Opponents {
        /**
         * If there are opponents at indexes:
         *   2       3
         *     0   1
         *       X
         *     4   5
         *   6       7
         */
        boolean[] opponent = {false, false, false, false, false, false, false, false};

        /**
         * Current potential victims
         *   0   1
         *     X
         *   2   3
         */
        CheckerPiece[] deletable = {null, null, null, null};

        /**
         * Current direction
         */
        int dir;

        /**
         * return opponent locations as a list of boolean values
         */
        public boolean[] getOpponent(){
            return opponent;
        }

        /**
         * return deletable checker pieces
         */
        public CheckerPiece[] getDeletable(){
            return deletable;
        }


        public Opponents(int d, int xIdx, int yIdx, ArrayList<CheckerPiece> player_pieces, boolean king){
            dir = d;
            survey(xIdx, yIdx, player_pieces, king);
        }

        /**
         * Reset the opponent and deletable values to their default
         */
        public void reset(){
            opponent = new boolean[]{false, false, false, false, false, false, false, false};
            deletable = new CheckerPiece[]{null, null, null, null};
        }

        /**
         * Assess the location of pieces surrounding a particular piece on the board
         * @param xIdx x index of piece
         * @param yIdx y index of piece
         * @param player_pieces pieces to be assessed
         * @param king whether the piece is a king
         */
        public void survey(int xIdx, int yIdx, ArrayList<CheckerPiece> player_pieces, boolean king){
            for(CheckerPiece piece: player_pieces){

                if (xIdx - 1 >= 0 && yIdx + dir >= 0 && yIdx + dir <= 7 && piece.equals(new int[]{xIdx - 1, yIdx + dir})){
                    opponent[0] = true;
                    deletable[0] = piece;
                } else if (xIdx + 1 <= 7 && yIdx + dir >= 0 && yIdx + dir <= 7 && piece.equals(new int[]{xIdx + 1, yIdx + dir})){
                    opponent[1] = true;
                    deletable[1] = piece;
                } else if (xIdx - 2 >= 0 && yIdx + dir * 2 >= 0 && yIdx + dir * 2 <= 7 && piece.equals(new int[]{xIdx - 2, yIdx + dir * 2})){
                    opponent[2] = true;
                } else if (xIdx + 2 <= 7 && yIdx + dir * 2 >= 0 && yIdx + dir * 2 <= 7 && piece.equals(new int[]{xIdx + 2, yIdx + dir * 2})){
                    opponent[3] = true;
                }
                if(king){
                    if (xIdx - 1 >= 0 && yIdx - dir >= 0 && yIdx - dir <= 7 && piece.equals(new int[]{xIdx - 1, yIdx - dir})){
                        opponent[4] = true;
                        deletable[2] = piece;
                    } else if (xIdx + 1 <= 7 && yIdx - dir >= 0 && yIdx - dir <= 7 && piece.equals(new int[]{xIdx + 1, yIdx - dir})){
                        opponent[5] = true;
                        deletable[3] = piece;
                    } else if (xIdx - 2 >= 0 && yIdx - dir * 2 >= 0 && yIdx - dir * 2 <= 7 && piece.equals(new int[]{xIdx - 2, yIdx - dir * 2})){
                        opponent[6] = true;
                    } else if (xIdx + 2 <= 7 && yIdx - dir * 2 >= 0 && yIdx - dir * 2 <= 7 && piece.equals(new int[]{xIdx + 2, yIdx - dir * 2})){
                        opponent[7] = true;
                    }
                }
            }
        }

    }

}