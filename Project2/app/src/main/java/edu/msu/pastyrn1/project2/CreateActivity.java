package edu.msu.pastyrn1.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.msu.pastyrn1.project2.Cloud.Cloud;

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
                String user = username.getText().toString();
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();

                if(!pass1.equals(pass2)){
                    password2.setError("Passwords do not not match");
                    //Toast.makeText(CreateActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                    return;
                } else { // both passwords are the same
                    //String pass = pass1;
                    if(TextUtils.isEmpty(user)){
                        username.setError("Please provide username");
                        return;
//                        Toast.makeText(CreateActivity.this, "Missing UserId or Password", Toast.LENGTH_LONG).show();
                    }else if(TextUtils.isEmpty(pass1)){
                        password1.setError("Please provide password");
                        return;
                    }else if(TextUtils.isEmpty(pass2)){
                        password2.setError("Please confirm password");
                        return;
                    }//create and save
                    create(user,pass1);
                }

            }
        });

    }

    /**
     * Attempt a login with input information
     * @param name username
     * @param pw password
     */
    private void create(final String name, final String pw) {

        final View view = (View) findViewById(R.id.CreateLayout);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.createUser(name, pw);
                if(!ok) {
                    //If we fail to find the user, display a toast
                    view.post(new Runnable(){
                        public void run() {
                            Toast.makeText(view.getContext(), "User Creation Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    startActivity(new Intent(CreateActivity.this, DummyActivity.class));
                }
            }
        }).start();

    }
}