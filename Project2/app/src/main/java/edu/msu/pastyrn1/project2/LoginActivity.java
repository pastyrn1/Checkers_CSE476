package edu.msu.pastyrn1.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.msu.pastyrn1.project2.Cloud.Cloud;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password ;
    Button loginBtn;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBttn1);
        username = findViewById(R.id.userID1);
        password = findViewById(R.id.passWord1);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.checkBox2);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()|password.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Login Failed: Username or Password not provided",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                login(username.getText().toString(), password.getText().toString());
            }
        });

    }

    /**
     * Saves input information if box is checked
     * @param name username
     * @param pw password
     */
    private void saveCheckBox(String name, String pw){
        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", name);
            loginPrefsEditor.putString("password", pw);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }


    /**
     * Attempt a login with input information
     * @param name username
     * @param pw password
     */
    private void login(final String name, final String pw) {

        final View view = (View) findViewById(R.id.relativeLayout);//TODO: check this works

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.checkExistence(name, pw);
                if(!ok) {
                    //If we fail to find the user, display a toast
                    view.post(new Runnable(){
                        public void run() {
                            Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {//check if available: game, else: waiting
                    saveCheckBox(name, pw);
//                    if (saveLoginCheckBox.isChecked()) {
//                        loginPrefsEditor.putBoolean("saveLogin", true);
//                        loginPrefsEditor.putString("username", username.getText().toString());
//                        loginPrefsEditor.putString("password", password.getText().toString());
//                        loginPrefsEditor.commit();
//                    } else {
//                        loginPrefsEditor.clear();
//                        loginPrefsEditor.commit();
//                    }

            ////////    CHECKING IF GAME EXISTS     ////////////

//                    final boolean game = cloud.checkgame()

//                    if(game exists){
//                        Intent i = new Intent(LoginActivity.this, GameActivity.class);
////                        i.putExtra("name", name);
//                        startActivity(i);
//                    }
//
//                    else{
//                        Intent intent = new Intent(LoginActivity.this, WaitingActivity.class);
////                        intent.putExtra("name", name);
//                        startActivity(intent);
//                    }

            ////////    CHECKING IF GAME EXISTS/>     ////////////

                        Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);

                }

            }
        }).start();
    }
}