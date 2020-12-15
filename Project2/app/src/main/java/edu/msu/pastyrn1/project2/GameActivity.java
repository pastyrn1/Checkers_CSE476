package edu.msu.pastyrn1.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Toast.makeText(View.getContext(), "You Resigned. Opponent won", Toast.LENGTH_LONG).show();
        startActivity(new Intent(GameActivity.this, StartActivity.class));
    }

    /**
     * Done Button to switch turns
     */
    public void onDone(View v){
        //updatePiece?

    }


}
