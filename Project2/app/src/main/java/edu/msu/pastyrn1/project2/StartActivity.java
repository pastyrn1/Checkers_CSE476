package edu.msu.pastyrn1.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, CreateActivity.class));
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LoginDlg dlg2 = new LoginDlg();
//                dlg2.show(getSupportFragmentManager(), "login");
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        Button instructButton = findViewById(R.id.instructionButton);
        instructButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

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