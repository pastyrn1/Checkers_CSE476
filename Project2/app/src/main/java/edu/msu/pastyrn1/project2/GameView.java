package edu.msu.pastyrn1.project2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom view class for our Game.
 */
public class GameView extends View {

    /**
     * The game
     */
    private Game game;

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
        game = new Game(getContext(),1);
    }

    //TODO:uncomment this
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.onTouchEvent(this, event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        game.draw(canvas);
    }

//    /**
//     * Save the game to a bundle
//     * @param bundle The bundle we save to
//     */
//    public void saveInstanceState(Bundle bundle) {
//        game.saveInstanceState(bundle);
//    }
//
//    /**
//     * Load the game from a bundle
//     * @param bundle The bundle we save to
//     */
//    public void loadInstanceState(Bundle bundle) {
//        game.loadInstanceState(bundle);
//    }

    public Game getGame() {
        return game;
    }

}
