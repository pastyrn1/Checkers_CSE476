package edu.msu.pastyrn1.project2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private class DataUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(StartActivity.REFRESH_DATA_INTENT)) {
                // Do stuff - maybe update my view based on the changed DB contents
                String from = intent.getStringExtra(MESSAGE_FROM);
                String body = intent.getStringExtra(MESSAGE_BODY);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView textFrom = (TextView) layout.findViewById(R.id.textView2);
                textFrom.setText(from);
                TextView textBody = (TextView) layout.findViewById(R.id.textView4);
                textBody.setText(body);

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }
    }
    public static String REFRESH_DATA_INTENT = "edu.msu.pastryn1.fcm.refreshdata";
    public static String MESSAGE_FROM = "edu.msu.pastryn1.fcm.messagefrom";
    public static String MESSAGE_BODY = "edu.msu.pastryn1.fcm.messagebody";


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