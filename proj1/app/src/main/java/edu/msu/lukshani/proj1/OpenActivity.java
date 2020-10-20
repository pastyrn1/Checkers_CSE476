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
    EditText nameInput2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        button = findViewById(R.id.startButton);
        nameInput = findViewById(R.id.player1name);
        nameInput2 = findViewById(R.id.player2name);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OpenActivity.this, GameActivity.class);
                name = nameInput.getText().toString();
                i.putExtra("player", name);
                startActivity(i);
                finish();
            }
        });

        nameInput.setText(R.string.player1text);
        nameInput2.setText(R.string.player2text);

    }

//            public void onStart(View view) {
//                startActivity(new Intent(OpenActivity.this, GameActivity.class));
//            }

}