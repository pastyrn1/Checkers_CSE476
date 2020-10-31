package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    TextView playerTurn;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playerTurn = findViewById(R.id.playerTurn);

        st = getIntent().getExtras().getString("player");
        playerTurn.setText(st);
    }

    public void onResign(View View) {
        //doResign();
        //View.onDraw();
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }
    /////////

//    TextView playerTurn;
//    playerTurn = findViewById(R.id.playerTurn);
//
//    player2name = findViewById(R.id.player2name);
//
//    private void doDone(){ //call doDone when clicking Done Button
//        if(isTurnPlayer1){
//            playerTurn.setText(player2name + "'s turn");
//        }
//        else{
//            playerTurn.setText("Player 1 Turn"); //change player 1 to player1name
//        }
//    }
//
//    TextView endMessage;
//    endMessage = findViewById(R.id.endMessage);
//
//    private void doResign(){ // call doResign when clicking Resign Button
//        if(isTurnPlayer1){// player 1 resigns
//            endMessage.setText("Player 2 Wins"); //change player 2 to player2name
//        }
//        else{ //player 2 resigns
//            endMessage.setText("Player 1 Wins"); //change player 1 to player1name
//        }
//    }

///////////
}