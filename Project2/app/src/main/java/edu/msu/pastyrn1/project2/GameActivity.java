package edu.msu.pastyrn1.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.msu.pastyrn1.project2.Cloud.Cloud;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Board();
    }

    /**
     * Button Resigns the game
     */
    public void onResign(View View) {
        Toast.makeText(View.getContext(), "You Resigned. Opponent won", Toast.LENGTH_LONG).show();
        startActivity(new Intent(GameActivity.this, EndActivity.class));
    }

    /**
     * Done Button to switch turns
     */
    public void onDone(View v){
        //updatePiece?

    }

    private void Board(){
        final View view = (View) findViewById(R.id.gameLayout);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.setBoard();
                if(!ok) {
                    //If we fail to find the user, display a toast
                    view.post(new Runnable(){
                        public void run() {
                            Toast.makeText(view.getContext(), "Set Board Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
