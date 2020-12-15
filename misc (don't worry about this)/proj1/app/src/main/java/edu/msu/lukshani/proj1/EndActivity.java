package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        TextView endMessage = findViewById(R.id.endMessage);
        message = getIntent().getExtras().getString("winner");
        endMessage.setText(message + " has won");

//        int [] test= savedInstanceState.getIntArray("GameActivity.array");
//        int [] player_1_locations = savedInstanceState.getIntArray("Game.p1locations");

        configureRestartButton();
    }


    private void configureRestartButton(){
        Button restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EndActivity.this, OpenActivity.class));
            }
        });
    }
}