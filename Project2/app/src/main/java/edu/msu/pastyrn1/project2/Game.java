package edu.msu.pastyrn1.project2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.msu.pastyrn1.project2.Cloud.Cloud;
import edu.msu.pastyrn1.project2.Cloud.Models.TablePiece;

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
     * Most recent X pos of dragging
     */
    private float preX;

    /**
     * Most recent Y pos of dragging
     */
    private float preY;

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
        this.player = player;

        // Create lower pieces (user)
        /*for(int i = 5; i < 8; i++){
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
        }*/
        reload();
    }

    /**
     * reload the contents of the checkertable
     */
    public void reload(){
        userPieces.clear();
        enemyPieces.clear();

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final List<TablePiece> board = cloud.loadBoard();

                for(TablePiece t: board) {
                    if (player == 1) {
                        if (t.getPlayer() == 1) {
                            userPieces.add(new CheckerPiece(context, R.drawable.green, R.drawable.spartan_green, t.getXIdx(), t.getYIdx(), -1));
                        } else {
                            enemyPieces.add(new CheckerPiece(context, R.drawable.white, R.drawable.spartan_white, t.getXIdx(), t.getYIdx(), 1));
                        }
                    } else {
                        if (t.getPlayer() == 1) {
                            enemyPieces.add(new CheckerPiece(context, R.drawable.green, R.drawable.spartan_green, 7 - t.getXIdx(), 7 - t.getYIdx(), 1));
                        } else {
                            userPieces.add(new CheckerPiece(context, R.drawable.white, R.drawable.spartan_white, 7 - t.getXIdx(), 7 - t.getYIdx(), -1));
                        }
                    }
                }
            }
        }).start();

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




    }