package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OpenActivity extends AppCompatActivity {

    String name;
    EditText nameInput;

    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);


        nameInput = (EditText)findViewById(R.id.player1name);

//        startButton = (Button)findViewById(R.id.startButton);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name = nameInput.getText().toString();
//            }
//        });


    }


            public void onStart(View view) {
                startActivity(new Intent(OpenActivity.this, GameActivity.class));
            }

}