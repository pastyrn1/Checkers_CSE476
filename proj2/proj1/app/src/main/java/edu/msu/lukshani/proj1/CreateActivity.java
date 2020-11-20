package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    EditText username;
    EditText password1;
    EditText password2;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        username = findViewById(R.id.userIDC1);
        password1 = findViewById(R.id.passWordC1);
        password2 = findViewById(R.id.passWordC2);
        createBtn = findViewById(R.id.registerBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateActivity.this, OpenActivity.class));
            }
        });

    }

}