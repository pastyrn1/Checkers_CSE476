package edu.msu.lukshani.proj1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Toast;
import java.util.ArrayList;

public class Game {
    private Context context;
    /**
     * win condition (1 - player 1, 2 - player 2, 0 - Neither)
     */
    private int win = 0;

    /**
     * Paint for filling the area the checkerboard is in
     */
    private Paint fillPaint;

    /**
     * Percentage of the display width or height that
     * is occupied by the checkerboard.
     */
    final static float SCALE_IN_VIEW = 1;

    /**
     * The size of the board in pixels
     */
    private int pixelSize;

    /**
     * We consider a piece to be in a valid location if within
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
     * Most recent X pos of dragging
     */
    private float preX;

    /**
     * Most recent Y pos of dragging
     */
    private float preY;

    /**
     * Valid locations of checker squares
     */
    private float[] valid = {.0625f, .1875f, .3125f, .4375f, .5625f, .6875f, .8125f, .9375f};

    /**
     * A boolean for whose turn it is
     */
    private boolean isTurnPlayer1 = true;

    /**
     * A boolean for if a valid turn has been completed
     */
    private boolean isTurnComplete = false;

    /**
     * This variable is set to a piece capable of a second or third jump. If
     * we are not multi jumping, the variable is null.
     */
    private CheckerPiece jumper = null;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private CheckerPiece dragging = null;

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
     * Collection of checker pieces for each player
     */
    public ArrayList<CheckerPiece> player1_pieces = new ArrayList<CheckerPiece>();
    public ArrayList<CheckerPiece> player2_pieces = new ArrayList<CheckerPiece>();

    public Game(Context context){
        this.context = context;

        // Create paint for filling the game board
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xff006400);

        //create lower pieces (player 1)
        for(int i = 5; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {
                    player1_pieces.add(new CheckerPiece(context, R.drawable.green, R.drawable.spartan_green, valid, j, i, -1));
                }
            }
        }

        //create upper pieces (player 2)
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 8; j++){
                if(i % 2 !=  j % 2) {
                    player2_pieces.add(new CheckerPiece(context, R.drawable.white, R.drawable.spartan_white, valid, j, i, 1));
                }
            }
        }
    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        int [] player1_x_locations = {};
        int [] player1_y_locations = {};
        boolean [] player1_kings = {};

        if(player1_pieces.size() != 0){
            player1_x_locations = new int[player1_pieces.size()];
            player1_y_locations = new int[player1_pieces.size()];
            player1_kings = new boolean[player1_pieces.size()];
        }

        int [] player2_x_locations = {};
        int [] player2_y_locations = {};
        boolean [] player2_kings = {};

        if(player2_pieces.size() != 0){
            player2_x_locations = new int[player2_pieces.size()];
            player2_y_locations = new int[player2_pieces.size()];
            player2_kings = new boolean[player2_pieces.size()];
        }

        if(!player1_pieces.isEmpty()){
            for(int i=0;  i<player1_pieces.size(); i++) {
                CheckerPiece piece = player1_pieces.get(i);
                player1_x_locations[i] = piece.getXIdx();
                player1_y_locations[i] = piece.getYIdx();
                player1_kings[i] = piece.getKing();
            }
        }

        if(!player2_pieces.isEmpty()) {
            for (int i = 0; i < player2_pieces.size(); i++) {
                CheckerPiece piece = player2_pieces.get(i);
                player2_x_locations[i] = piece.getXIdx();
                player2_y_locations[i] = piece.getYIdx();
                player2_kings[i] = piece.getKing();
            }
        }

        bundle.putIntArray(P1LOCATIONS, player1_x_locations);
        bundle.putIntArray(P2LOCATIONS, player2_x_locations);

        bundle.putBooleanArray(P1KINGS, player1_kings);
        bundle.putBooleanArray(P2KINGS, player2_kings);

        bundle.putIntArray(P1YLOCATIONS, player1_y_locations);
        bundle.putIntArray(P2YLOCATIONS, player2_y_locations);

        bundle.putBoolean(COMPLETE, isTurnComplete);
    }

    /**
     * Read the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        int [] player1_x_locations = bundle.getIntArray(P1LOCATIONS);
        int [] player2_x_locations = bundle.getIntArray(P2LOCATIONS);

        boolean [] player1_kings = bundle.getBooleanArray(P1KINGS);
        boolean [] player2_kings = bundle.getBooleanArray(P2KINGS);

        int [] player1_y_locations = bundle.getIntArray(P1YLOCATIONS);
        int [] player2_y_locations = bundle.getIntArray(P2YLOCATIONS);

        isTurnComplete = bundle.getBoolean(COMPLETE);

        //TODO: clean up piece removal
        // (only two int arrays necessary now - maybe save this for project 2)

        int size = player1_pieces.size() - player1_x_locations.length;
        for(int i=0;  i<size; i++) {
            player1_pieces.remove(0);
        }

        if(!player1_pieces.isEmpty() && player1_pieces.size() == player1_x_locations.length){
            for(int i=0;  i<player1_x_locations.length; i++) {
                CheckerPiece piece = player1_pieces.get(i);
                piece.setIdx(player1_x_locations[i], player1_y_locations[i]);
                piece.setPos(valid[player1_x_locations[i]], valid[player1_y_locations[i]]);

                if(player1_kings[i]){
                    piece.king();
                }

            }
        }

        size = player2_pieces.size() - player2_x_locations.length;
        for(int i=0;  i<size; i++) {
            player2_pieces.remove(0);
        }

        if(!player2_pieces.isEmpty() && player2_pieces.size() == player2_x_locations.length) {
            for (int i = 0; i < player2_x_locations.length; i++) {
                CheckerPiece piece = player2_pieces.get(i);
                piece.setIdx(player2_x_locations[i], player2_y_locations[i]);
                piece.setPos(valid[player2_x_locations[i]], valid[player2_y_locations[i]]);

                if(player2_kings[i]){
                    piece.king();
                }
            }
        }

        checkWin();

    }

    /**
     * Draw the checker game
     * @param canvas Canvas we are drawing on
     */
    public void draw(Canvas canvas){

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        pixelSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the game board
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

        if(isTurnPlayer1) {
            for (CheckerPiece piece : player2_pieces) {
                piece.draw(canvas, marginX, marginY, pixelSize);

            }

            for (CheckerPiece piece : player1_pieces) {
                piece.draw(canvas, marginX, marginY, pixelSize);
            }
        } else {
            for (CheckerPiece piece : player1_pieces) {
                piece.draw(canvas, marginX, marginY, pixelSize);
            }

            for (CheckerPiece piece : player2_pieces) {
                piece.draw(canvas, marginX, marginY, pixelSize);
            }
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
        // game board.
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
                    view.invalidate();
                    return true;
                }
                break;
        }
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
        // We do this in reverse order so we find the pieces in front

        if(isTurnPlayer1 && !isTurnComplete){
            for(int p = player1_pieces.size()-1; p>=0;  p--) {
                if(player1_pieces.get(p).hit(x, y, pixelSize)) {
                    // We hit player 1 piece!
                    if(jumper == null || player1_pieces.get(p) == jumper) {
                        dragging = player1_pieces.get(p);
                        preX = dragging.getX();
                        preY = dragging.getY();
                        player1_pieces.set(p, player1_pieces.get(player1_pieces.size() - 1));
                        player1_pieces.set(player1_pieces.size() - 1, dragging);
                        lastRelX = x;
                        lastRelY = y;
                        return true;
                    }
                }

            }
        } else if(!isTurnComplete) {
            for (int p = player2_pieces.size() - 1; p >= 0; p--) {
                if (player2_pieces.get(p).hit(x, y, pixelSize)) {
                    // We hit player2 piece!
                    if(jumper == null || player2_pieces.get(p) == jumper) {
                        dragging = player2_pieces.get(p);
                        preX = dragging.getX();
                        preY = dragging.getY();
                        player2_pieces.set(p, player2_pieces.get(player2_pieces.size() - 1));
                        player2_pieces.set(player2_pieces.size() - 1, dragging);
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
        if(dragging != null) {
            int v = isValid();

            if(v == 1) {
                //The movement is valid
                jumper = null;
                isTurnComplete = true;
                view.invalidate();
            } else if(v == -1){
                //The movement is valid, multi jump has begun
                jumper = dragging;
                view.invalidate();
            } else {
                dragging.setPos(preX, preY);
                showToast("Invalid Move");
                view.invalidate();
            }
            dragging = null;
            checkWin();
            return true;
        }

        return false;
    }

    /**
     * Check if a movement is valid
     * @return result of the movement
     */
    public int isValid(){

        float x = dragging.getX();
        float y = dragging.getY();

        int xIdx = dragging.getXIdx();
        int yIdx = dragging.getYIdx();

        int d = dragging.getDirection();

        boolean king = dragging.getKing();

        Opponents o = new Opponents(d, xIdx, yIdx, player1_pieces, king);
        Opponents a = new Opponents(d, xIdx, yIdx, player2_pieces, king);

        if(isTurnPlayer1) {
            o = a;
            a = new Opponents(d, xIdx, yIdx, player1_pieces, king);
        }

        boolean[] opponent = o.getOpponent();
        boolean[] ally = a.getOpponent();
        CheckerPiece[] deletable = o.getDeletable();

        //move one space
        if(oneSpace(x, y, xIdx, yIdx, d, -1, opponent[0] || ally[0]) ||
                oneSpace(x, y, xIdx, yIdx, d, 1, opponent[1] || ally[1]) ||
                (dragging.getKing() && (oneSpace(x, y, xIdx, yIdx, -1 * d, -1, opponent[4] || ally[4]) ||
                        oneSpace(x, y, xIdx, yIdx, -1 * d, 1, opponent[5] || ally[5])))){
            return 1;
        }

        //move two spaces
        if(twoSpace(x, y, xIdx, yIdx, d, -1, opponent[2] || ally[2], opponent[0], deletable[0]) ||
                twoSpace(x, y, xIdx, yIdx, d, 1, opponent[3] || ally[3], opponent[1], deletable[1])
                || (dragging.getKing() && (twoSpace(x, y, xIdx, yIdx, -1 * d, -1, opponent[6] || ally[6], opponent[4], deletable[2]) ||
                twoSpace(x, y, xIdx, yIdx, -1 * d, 1, opponent[7] || ally[7], opponent[5], deletable[3])))){

            xIdx = dragging.getXIdx();
            yIdx = dragging.getYIdx();

            king = dragging.getKing();

            o.reset();
            a.reset();

            //check for multi jump
            if (!isTurnPlayer1) {
                o.survey(xIdx, yIdx, player1_pieces, king);
                a.survey(xIdx, yIdx, player2_pieces, king);
            } else {
                o.survey(xIdx, yIdx, player2_pieces, king);
                a.survey(xIdx, yIdx, player1_pieces, king);
            }

            opponent = o.getOpponent();
            ally = a.getOpponent();

            if(canJump(xIdx, yIdx, d, -1, opponent[2] || ally[2], opponent[0]) ||
                    canJump(xIdx, yIdx, d, 1, opponent[3] || ally[3], opponent[1]) ||
                    (dragging.getKing() && (canJump(xIdx, yIdx, -1 * d, -1, opponent[6] || ally[6], opponent[4]) ||
                            canJump(xIdx, yIdx, -1 * d, 1, opponent[7] || ally[7], opponent[3])))){
                return -1;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Check if a piece should be kinged
     * @param d direction of movement
     * @param xIdx x index of piece
     * @param yIdx y index of piece
     */
    private void king_check(int d, int xIdx, int yIdx){
        if(((d > 0 && yIdx >= 7) || (d < 0 && yIdx <= 0)) && !dragging.getKing()){
            dragging.king();
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

    /**
     * Attempt an one space movement
     * @param x x position of piece after movement
     * @param y y position of piece after movement
     * @param xIdx x index of piece before movement
     * @param yIdx y index of piece before movement
     * @param yD direction piece is moving on y axis
     * @param xD direction piece is moving on x axis
     * @param opponent is opponent on tile
     * @return is successful?
     */
    public boolean oneSpace(float x, float y, int xIdx, int yIdx, int yD, int xD, boolean opponent){
        if (xIdx + xD < 0 || xIdx + xD > 7 || yIdx + yD < 0 ||  yIdx + yD > 7){
            return false;
        }

        if(Math.abs(x - valid[xIdx + xD]) < SNAP_DISTANCE &&
                Math.abs(y - valid[yIdx + yD]) < SNAP_DISTANCE && !opponent) {
            dragging.setPos(valid[xIdx + xD], valid[yIdx + yD]);
            dragging.setIdx(xIdx + xD, yIdx + yD);

            king_check(yD, xIdx + xD, yIdx + yD);

            return true;
        }
        return false;
    }

    /**
     * Attempt a jump
     * @param x x position of piece after movement
     * @param y y position of piece after movement
     * @param xIdx x index of piece before movement
     * @param yIdx y index of piece before movement
     * @param yD direction piece is moving on y axis
     * @param xD direction piece is moving on x axis
     * @param opponent is opponent on tile
     * @param victim is victim on jumped tile
     * @param deletable checker piece
     * @return is successful?
     */
    public boolean twoSpace(float x, float y, int xIdx, int yIdx, int yD, int xD, boolean opponent, boolean victim, CheckerPiece deletable){
        if (xIdx + xD * 2 < 0 || xIdx + xD * 2 > 7 || yIdx + yD * 2 < 0 ||  yIdx + yD * 2 > 7){
            return false;
        }

        if(Math.abs(x - valid[xIdx + xD * 2]) < SNAP_DISTANCE &&
                Math.abs(y - valid[yIdx + yD * 2]) < SNAP_DISTANCE && !opponent && victim) {

            if (!isTurnPlayer1) {
                player1_pieces.remove(deletable);
            } else {
                player2_pieces.remove(deletable);
            }
            dragging.setPos(valid[xIdx + xD * 2], valid[yIdx + yD * 2]);
            dragging.setIdx(xIdx + xD * 2, yIdx + yD * 2);

            king_check(yD, xIdx + xD * 2, yIdx + yD * 2);

            return true;
        }
        return false;
    }

    /**
     * Verify if another jump is possible
     * @param xIdx x index of piece
     * @param yIdx y index of piece
     * @param yD direction piece is moving on y axis
     * @param xD direction piece is moving on x axis
     * @param opponent is opponent on tile
     * @param victim is victim on jumped tile
     * @return is possible?
     */
    public boolean canJump(int xIdx, int yIdx, int yD, int xD, boolean opponent, boolean victim){
        if (xIdx + xD * 2 < 0 || xIdx + xD * 2 > 7 || yIdx + yD * 2 < 0 ||  yIdx + yD * 2 > 7){
            return false;
        }

        if(!opponent && victim){
            return true;
        }

        return false;
    }

    /**
     * return whose turn it is
     */
    public boolean getTurnPlayer1() { return isTurnPlayer1; }

    /**
     * set whose turn it is
     */
    public void setTurnPlayer1(boolean isTurnPlayer1) {
        this.isTurnPlayer1 = isTurnPlayer1;
    }

    /**
     * set a turn as completed (no further movement possible)
     */
    public void setTurnComplete(boolean isTurnComplete){
        this.isTurnComplete = isTurnComplete;
    }

    /**
     * return win status
     */
    public int getWin() {
        return win;
    }

    /**
     * set win status
     */
    public void setWin(int win) {
        this.win = win;
    }

    /**
     * check if a player has won
     */
    public void checkWin(){
        if (player2_pieces.isEmpty()){
            win = 1;
        } else if (player1_pieces.isEmpty()){
            win = 2;
        }
    }

    /**
     * Show toast message
     * @param message the message shown
     */
    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}