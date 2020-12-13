package edu.msu.pastyrn1.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    }

    /**
     * Button Resigns the game
     */
    public void onResign(View View) {

    }

    /**
     * Done Button to switch turns
     */
    public void onDone(View v){
        //updatePiece?

    }


}
