package edu.msu.pastyrn1.project2.Cloud;

import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import edu.msu.pastyrn1.project2.CheckerPiece;
import edu.msu.pastyrn1.project2.Cloud.Models.BoardResult;
import edu.msu.pastyrn1.project2.Cloud.Models.SimpleResult;
import edu.msu.pastyrn1.project2.Cloud.Models.TablePiece;
import edu.msu.pastyrn1.project2.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@SuppressWarnings("deprecation")
public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~pastyrn1/cse476/project2/";
    public static final String CREATE_PATH = "create-player.php";
    public static final String LOAD_P_PATH = "load-player.php";
    public static final String SET_PATH = "set-board.php";
    public static final String LOAD_B_PATH = "load-board.php";
    public static final String UPDATE_PATH = "update-piece.php";
    private static final String MAGIC = "NechAtHa6RuzeR8x"; //TODO: alter this?

    //current user/pw
    public String user;
    public String pw;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    /**
     * Check if a user is present in the cloud.
     * @param username name of user
     * @param password password of user
     * @return true if successful
     */
    public boolean checkExistence(final String username, final String password){

        CheckersService service = retrofit.create(CheckersService.class);
        try {

            //TODO: Create loginResult/userResult once more information needs to be retrieved

            Response<SimpleResult> response = service.loginUser(username, password).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("LoginUser", "Failed to login, response code is = " + response.code());
                return false;
            }

            //check if status == "yes"
            SimpleResult result = response.body();
            if (result.getStatus().equals("yes")) {
                user = username;
                pw = password;
                return true;
            }

            Log.e("LoginUser", "Failed to login, message is = '" + result.getMessage() + "'");
            return false;

        } catch (IOException e) {
            Log.e("LoginUser", "Exception occurred while logging in.", e);
            return false;
        }

    }

    /**
     * Set the checkerboard database
     * @return true if successful
     */
    public boolean setBoard(){

        CheckersService service = retrofit.create(CheckersService.class);
        try {
            Response<SimpleResult> response = service.setBoard().execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("SetBoard", "Failed to set the board, response code is = " + response.code());
                return false;
            }

            //check if status == "yes"
            SimpleResult result = response.body();
            if (result.getStatus().equals("yes")) {
                return true;
            }

            Log.e("SetBoard", "Failed to set the board, message is = '" + result.getMessage() + "'");
            return false;

        } catch (IOException e) {
            Log.e("SetBoard", "Exception occurred while logging in.", e);
            return false;
        }

    }

    /**
     * Create a user
     * @param username name of user
     * @param password password of user
     * @return true if successful
     */
    public boolean createUser(String username, String password){
        username = username.trim();
        if(username.length() == 0) {
            return false;
        }

        // Create an XML packet with the information about the current image
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try{
            xml.setOutput(writer);
            xml.startDocument("UTF-8", true);
            xml.startTag(null, "checkers");

            xml.attribute(null, "user", username);
            xml.attribute(null, "pw", password);
            xml.attribute(null, "magic", MAGIC);

            xml.endTag(null, "checkers");

            xml.endDocument();

        } catch (IOException e){
            return false;
        }

        CheckersService service = retrofit.create(CheckersService.class);
        final String xmlStr = writer.toString();

        try{
            SimpleResult result = service.createUser(xmlStr).execute().body();
            if (result.getStatus() == null || !result.getStatus().equals("yes")) {
                //Log.e("CreateUser", "Failed to create, message = '" + result.getMessage() + "'");
                return false;
            }
        } catch (IOException e){
            //Log.e("CreateUser", "Exception occurred while trying to create a new user.", e);
            return false;
        }

        user = username;
        pw = password;
        return true;
    }

    /**
     * Load the current board layout
     * @return piece locations and identities
     */
    public List<TablePiece> loadBoard(){

        try {
            CheckersService service = retrofit.create(CheckersService.class);
            BoardResult board = service.loadBoard().execute().body();

            if (board.getStatus().equals("no")) {
                String msg = "Loading board returned status 'no'! Message is = '" + board.getMessage() + "'";
                throw new Exception(msg);
            }

            return board.getPieces();

        } catch (Exception e) {
            // Error condition! Something went wrong
            Log.e("LoadBoard", "Something went wrong when loading the board", e);

        }

        return new ArrayList<TablePiece>();
    }

    /**
     * Commit the results of a moved piece to the board
     * This should be run in a thread.
     * @param piece checker piece moved
     * @param killed checker piece casualties
     * @return true if successful
     */
    public boolean updatePiece(CheckerPiece piece, @Nullable CheckerPiece killed) {

        // Create an XML packet with the information about the results of the player's turn
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            xml.setOutput(writer);

            xml.startDocument("UTF-8", true);

            xml.startTag(null, "checkers");

            xml.attribute(null, "user", user);
            xml.attribute(null, "pw", pw);
            xml.attribute(null, "magic", MAGIC);

            // Add moved piece to the xml
            xml.startTag(null, "checkertable");

            xml.attribute(null, "xidx", String.valueOf(piece.getXIdx()));
            xml.attribute(null, "yidx", String.valueOf(piece.getYIdx()));
            xml.attribute(null, "king", String.valueOf(piece.getKing()));

            xml.endTag(null, "checkertable");

            // Add casualties to the xml
            if(killed != null){
                xml.startTag(null, "checkertable");

                xml.attribute(null, "xidx", String.valueOf(killed.getXIdx()));
                xml.attribute(null, "yidx", String.valueOf(killed.getYIdx()));
                xml.attribute(null, "king", String.valueOf(killed.getKing()));
                xml.endTag(null, "checkertable");
            }

            xml.endTag(null, "checkers");

            xml.endDocument();

        } catch (IOException e) {
            return false;
        }

        CheckersService service = retrofit.create(CheckersService.class);
        final String xmlStr = writer.toString();
        try {
            SimpleResult result = service.updatePiece(xmlStr).execute().body();
            if (result.getStatus() != null && result.getStatus().equals("yes")) {
                return true;
            }
            Log.e("UpdatePiece", "Failed to update, message = '" + result.getMessage() + "'");
            return false;
        } catch (IOException e) {
            Log.e("UpdatePiece", "Exception occurred while trying to update piece", e);
            return false;
        }

    }

//    /**
//     * An adapter so that the board can show current checker pieces from the cloud.
//     */
//    public static class BoardAdapter extends BaseAdapter {
//        /**
//         * The checker pieces active in the current game. Initially this is
//         * null until we get checkertable information from the server.
//         */
//        private BoardResult board = new BoardResult("", new ArrayList(), "");
//
//        private String user;
//        private String pw;
//
//        /**
//         * Constructor
//         */
//        public BoardAdapter(final View view, String user, String pw) {
//
//            this.user = user;
//            this.pw = pw;
//
//            // Create a thread to load the board
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        board = getBoard();
//
//                        if (board.getStatus().equals("no")) {
//                            String msg = "Loading board returned status 'no'! Message is = '" + board.getMessage() + "'";
//                            throw new Exception(msg);
//                        }
//
//                        view.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                // Tell the adapter the data set has been changed
//                                notifyDataSetChanged();
//                            }
//
//                        });
//                    } catch (Exception e) {
//                        // Error condition! Something went wrong
//                        Log.e("BoardAdapter", "Something went wrong when loading the board", e);
//                        view.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Creates a toast in the event there is an error
//                                Toast.makeText(view.getContext(), R.string.board_fail, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }).start();
//        }
//
//        public BoardResult getBoard() throws IOException{
//            CheckersService service = retrofit.create(CheckersService.class);
//            return service.loadBoard(user, pw).execute().body();
//        }
//
//        @Override
//        public int getCount() {
//            return board.getPieces().size();
//        }
//
//        @Override
//        public TablePiece getItem(int position) {
//            return board.getPieces().get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            //apply view changes and call set game here
//
//            return view;
//        }
//
//    }


}