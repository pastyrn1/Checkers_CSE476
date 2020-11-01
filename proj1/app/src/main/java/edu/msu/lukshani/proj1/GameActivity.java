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
    EditText player2name;
    TextView endMessage;
    Game game;
//    private Game getGame() {return;}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playerTurn = findViewById(R.id.playerTurn);
        endMessage = findViewById(R.id.endMessage);

        player2name = findViewById(R.id.player2name);
        st = getIntent().getExtras().getString("player");
        playerTurn.setText(st);
    }

    public void onResign(View View) {
        //doResign();
        //View.onDraw();
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }

//    public void onDone(View v){
//        View gameView = findViewById(R.id.gameView);
//        if(gameView.getGame().getWin() == gameview.getPlayer1()){  //if win == 1
//            game.isTurnPlayer1() = false;
//            onResign(v);
//        } else if (gameView.getGame().getWin() == gameview.getPlayer2()){ //if win == 2
//            game.isTurnPlayer1() = true;
//            onResign(v);
//        }
//        doDone(); ///switching turns
//    }
//
//    private void doDone(){ //call doDone when clicking Done Button
//        if(isTurnPlayer1){
//            playerTurn.setText(player2name + "'s turn");
//            isTurnPlayer1 = false
//        }
//        else{
//            playerTurn.setText("Player 1 Turn"); //change player 1 to player1name
//            isTurnPlayer1 = true
//        }
//    }
//
//    private void doResign(){ // call doResign when clicking Resign Button
//        if(isTurnPlayer1){// player 1 resigns
//            endMessage.setText(player2name + "'s Wins"); //change player 2 to player2name
//        }
//        else{ //player 2 resigns
//            endMessage.setText("Player 1 Wins"); //change player 1 to player1name
//        }
//    }

}