package edu.msu.lukshani.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    TextView tv;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tv = findViewById(R.id.textView);

        st = getIntent().getExtras().getString("player");
        tv.setText(st);
    }

    public void onResign(View View) {
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }
}