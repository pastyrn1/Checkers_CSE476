package edu.msu.pastyrn1.project2;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    static final String userID = "2 user:Dougie password:Dougie";

    public MyFirebaseMessagingService() {

    }

    public static final String Register_URL = "https://webdev.cse.msu.edu/~pastyrn1/cse476/project2/";

    @Override
    public void onNewToken(@NonNull String token) {

        super.onNewToken(token);
        Log.d("TAG", "Refreshed Token" + token);


        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String refreshedToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendToServer(refreshedToken);
            }
        }).start();
    }

    private boolean sendToServer(String token) {
        String postDataStr = "";
        try {
            postDataStr = "user" + userID + "&fcmid=" + URLEncoder.encode(token, "utf-8");


        } catch (UnsupportedEncodingException e) {
            Log.d("FCM", "URLEcoder; invalid Encoding");
        }

        /*
         * Send data
         */
        byte[] postData = postDataStr.getBytes();

        InputStream stream = null;
        try {

            URL url = new URL(Register_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setUseCaches(false);

            OutputStream out = conn.getOutputStream();
            out.write(postData);
            out.close();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }
            stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("response", line);
            }

        } catch (MalformedURLException e) {
            return false;
        } catch (IOException ex) {
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    // Fail silently
                }
            }
        }
        return true;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("FCM Message", "From Player: " + remoteMessage.getFrom());
        Log.d("FCM Message", "Notification Message Body: Your Turn! " + remoteMessage.getNotification().getBody());
        Intent intent;
        // Do stuff that alters the content of my local SQLite Database
        intent = new Intent(StartActivity.REFRESH_DATA_INTENT);
        intent.putExtra(StartActivity.MESSAGE_FROM, remoteMessage.getFrom());
        intent.putExtra(StartActivity.MESSAGE_BODY, remoteMessage.getNotification().getBody());
        sendBroadcast(intent);

        
    }
}

