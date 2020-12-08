package edu.msu.pastyrn1.project2.Cloud;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

import edu.msu.pastyrn1.project2.Cloud.Models.CreateResult;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@SuppressWarnings("deprecation")
public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~pastyrn1/cse476/project2/";
    public static final String CREATE_PATH = "create-player.php";
    public static final String LOAD_PATH = "load-player.php";
    public static final String SET_PATH = "set-board.php";

    //private static final String MAGIC = "NechAtHa6RuzeR8x"; //TODO: alter this?

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

            Response<CreateResult> response = service.loginUser().execute();//, password).execute();

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("LoginUser", "Failed to login, response code is = " + response.code());
                return false;
            }

            //check if status == "yes"
            CreateResult result = response.body();
            if (result.getStatus().equals("yes")) {
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
     * Create a user
     * @param username name of user
     * @param password password of user
     * @return true if successful
     */
    public boolean createUser(String username, String password){
        /*username = username.trim();
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
            CreateResult result = service.createUser(xmlStr).execute().body();
            if (result.getStatus() == null || !result.getStatus().equals("yes")) {
                //Log.e("CreateUser", "Failed to create, message = '" + result.getMessage() + "'");
                return false;
            }
        } catch (IOException e){
            //Log.e("CreateUser", "Exception occurred while trying to create a new user.", e);
            return false;
        }*/

        return true;
    }
}
