package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OpenActivity extends AppCompatActivity {

    EditText username;
    EditText password ;
    Button createBtn;
    Button loginBtn;
    Button instructButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        createBtn = findViewById(R.id.createButton);
        loginBtn = findViewById(R.id.loginBttn);
        username = findViewById(R.id.userID);
        password = findViewById(R.id.passWord);
        instructButton = findViewById(R.id.dialogueButton);
        instructButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate inputs
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(OpenActivity.this, "Missing UserId or Password", Toast.LENGTH_LONG).show();
                }
                //else if (correct username and password)
//                  startActivity(new Intent(OpenActivity.this, DummyActivity.class));
                else {
                    Toast.makeText(OpenActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenActivity.this, CreateActivity.class));
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
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
        });*/

//        nameInput.setText(R.string.player1text);
//        nameInput2.setText(R.string.player2text);

    }

    /**
     * Button Instruction pop up dialogue
     */
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