package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    TextView playerTurn;
    String st;
    String st2;
    TextView endMessage;
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playerTurn = findViewById(R.id.playerTurn);
        endMessage = findViewById(R.id.endMessage);

        game = getGameView().getGame();
        st = getIntent().getExtras().getString("player");
        st2 = getIntent().getExtras().getString("player2");
        playerTurn.setText(st);

        if(savedInstanceState != null) {
            getGameView().loadInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        getGameView().saveInstanceState(bundle);
    }

    public void onResign(View View) {

        Intent intent = new Intent(this, EndActivity.class);
        doResign(intent);
        startActivity(intent);
    }

    private GameView getGameView() {
        return (GameView) this.findViewById(R.id.gameView);
    }

    public void onDone(View v){
        View gameView = findViewById(R.id.gameView);
        if(game.getWin() == 1){  //if win == 1
            game.setTurnPlayer1(false);
            onResign(v);
            Intent intent = new Intent(this, EndActivity.class);
            startActivity(intent);
        } else if (game.getWin() == 2){ //if win == 2
            game.setTurnPlayer1(true);
            onResign(v);
            Intent intent = new Intent(this, EndActivity.class);
            startActivity(intent);
        }

        doDone(); ///switching turns
    }

    private void doDone(){ //call doDone when clicking Done Button
        if(game.getTurnPlayer1()){
            playerTurn.setText(st2 + " make a move");
            game.setTurnPlayer1(false);
        }
        else{
            playerTurn.setText(st + " make a move"); //change player 1 to player1name
            game.setTurnPlayer1(true);
        }
        game.setTurnComplete(false);
    }

    private void doResign(Intent intent){ // call doResign when clicking Resign Button
        if(game.getTurnPlayer1()){// player 1 resigns
            intent.putExtra("winner", st2); //change player 2 to player2name
        }
        else{ //player 2 resigns
            intent.putExtra("winner", st); //change player 1 to player1name
        }
    }

}