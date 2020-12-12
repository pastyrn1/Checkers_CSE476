package edu.msu.pastyrn1.project2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.fragment.app.DialogFragment;

import edu.msu.pastyrn1.project2.Cloud.Cloud;

import static android.content.Context.MODE_PRIVATE;

public class LoginDlg extends DialogFragment {
    /**
     *  The login dialog box
     */
    AlertDialog dlg;

    // Declare Context variable at class level in Fragment
    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set title
        builder.setTitle(R.string.login);

        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Pass null as parent view
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.user_login, null);
        builder.setView(view);

        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel closes the dialog box
            }
        });

        // Add an ok button
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText editName = (EditText)dlg.findViewById(R.id.editName);
                EditText editPw = (EditText)dlg.findViewById(R.id.editPw);

                ////CHECKBOX//ONCREATE////
                saveLoginCheckBox = (CheckBox)dlg.findViewById(R.id.checkBox);
                loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
//                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                loginPrefsEditor = loginPreferences.edit();
                saveLogin = loginPreferences.getBoolean("saveLogin", false);
                if (saveLogin) {
                    editName.setText(loginPreferences.getString("username", ""));
                    editPw.setText(loginPreferences.getString("password", ""));
                    saveLoginCheckBox.setChecked(true);
                }
                ////CHECKBOX//ONCREATE////

                login(editName.getText().toString(), editPw.getText().toString());
            }
        });


        // Create the dialog box
        dlg = builder.create();
        return dlg;
    }

    /**
     * Attempt a login with input information
     * @param name username
     * @param pw password
     */
    private void login(final String name, final String pw) {

        if (!(getActivity() instanceof StartActivity)) {
            return;
        }

        final StartActivity activity = (StartActivity) getActivity();
        final View view = (View) activity.findViewById(R.id.relativeLayout);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok = cloud.checkExistence(name, pw);

                //cloud.setBoard(); //TODO:remove this tester code

                if(!ok) {
                    //If we fail to find the user, display a toast
                    view.post(new Runnable(){
                        public void run() {
                            Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else { //check if available: game, else: waiting

                    ////CHECKBOX//ONCLICK//
                    if (saveLoginCheckBox.isChecked()) {
                        Log.d("if", "run: checked");
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", name);
                        loginPrefsEditor.putString("password", pw);
                        loginPrefsEditor.commit();
                    } else if (!saveLoginCheckBox.isChecked()){
                        Log.d("else if", "run: not checked");
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
//                    if (saveLoginCheckBox.isChecked()) {
//                        loginPrefsEditor.putBoolean("saveLogin", true);
//                        loginPrefsEditor.putString("username", name);
//                        loginPrefsEditor.putString("password", pw);
//                        loginPrefsEditor.commit();
//                    } else {
//                        loginPrefsEditor.clear();
//                        loginPrefsEditor.commit();
//                    }
                    ////CHECKBOX//ONCLICK//

                    Log.d("TAG101", "run: DummyActivity1");
                    //TODO: Start Dummy Activity here
//                    if (mContext != null) {
//                        Intent intent = new Intent(getContext(), DummyActivity.class);
//                        intent.putExtra("name", name);
//                        startActivity(intent);
//                    }

                }
            }
        }).start();
    }


}
