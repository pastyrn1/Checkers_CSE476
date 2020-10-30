package edu.msu.lukshani.proj1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Custom view class for our Game.
 */
public class GameView extends View {

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    //final static float SCALE_IN_VIEW = 0.9f;

    /**
     * The game
     */
    private Game game;

    /**
     * Player 1
     */
    private Player player1;

    /**
     * Player 2
     */
    private Player player2;


    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        game = new Game(getContext());
        //player1 = new Player(getContext(), false);
        //player2 = new Player(getContext(), true);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.onTouchEvent(this, event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.draw(canvas);
        //player1.draw(canvas);
        //player2.draw(canvas);

    }


}