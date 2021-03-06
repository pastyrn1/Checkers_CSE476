package edu.msu.pastyrn1.project2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.msu.pastyrn1.project2.Cloud.Cloud;

public class CreateDlg extends DialogFragment {
    /**
     *  The login dialog box
     */
    AlertDialog dlg;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set title
        builder.setTitle(R.string.new_user);

        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Pass null as parent view
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.user_login, null);//TODO: actual create user screen, this borrows since it's temporary
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
                create(editName.getText().toString(), editPw.getText().toString());
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
    private void create(final String name, final String pw) {

        if (!(getActivity() instanceof StartActivity)) {
            return;
        }

        final StartActivity activity = (StartActivity) getActivity();
        final View view = (View) activity.findViewById(R.id.relativeLayout);//TODO: check this works

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

                }
            }
        }).start();
    }
}
