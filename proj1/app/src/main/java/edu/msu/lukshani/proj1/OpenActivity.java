package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OpenActivity extends AppCompatActivity {

    String name;
    String name2;
    EditText nameInput;
    EditText nameInput2;
    Button button;
    Button instructButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        button = findViewById(R.id.startButton);
        nameInput = findViewById(R.id.player1name);
        nameInput2 = findViewById(R.id.player2name);
        instructButton = findViewById(R.id.dialogueButton);
        instructButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OpenActivity.this, GameActivity.class);
                name = nameInput.getText().toString();
                i.putExtra("player", name);
                name2 =nameInput2.getText().toString();
                i.putExtra("player2", name2);
                startActivity(i);
                finish();
            }
        });

        nameInput.setText(R.string.player1text);
        nameInput2.setText(R.string.player2text);

    }
    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instructions");
            builder.setMessage("One player has the green pieces; the other has the white pieces. " +
                    "Players alternate turns. A player may not move an opponent's piece. " +
                    "A move consists of moving a piece diagonally to an adjacent unoccupied square. " +
                    "If the adjacent square contains an opponent's piece, and the square " +
                    "immediately beyond it is vacant, the piece may be captured " +
                    "(and removed from the game) by jumping over it.");
            builder.setPositiveButton(android.R.string.ok, null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }

}