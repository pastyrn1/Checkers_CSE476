package edu.msu.lukshani.proj1.Cloud;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

//import edu.msu.lukshani.proj1.CreateActivity;

//import edu.msu.lukshani.proj1.Cloud.R;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@SuppressWarnings("deprecation")
public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~pastyrn1/cse476/project2/";
    public static final String CREATE_PATH = "create-player.php";

    private static final String MAGIC = "NechAtHa6RuzeR8x";

    private static final String USER = "pastyrn1";
    private static final String PASSWORD = "ThankYOU";

    /*private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();*/
    public boolean saveToCloud(EditText username, EditText password){
        if(username.length() == 0){
            return false;
        }

        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try{
            xml.setOutput(writer);

            xml.attribute(null, "user", USER);
            xml.attribute(null, "pw", PASSWORD);
            xml.attribute(null, "magic", MAGIC);


            //toString.saveXml(name, xml);

            xml.endTag(null, "user");

            xml.endDocument();

        }catch (IOException e)
        {
           return false;
        }

        return true;
    }

    /**
     *
     *//*
    public static class UserAdapter extends BaseAdapter {

        *//**
         * Constructor
         *//*
        public UserAdapter(final View view) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Item getItem(int position) {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
           return view;
        }

    }*/

}
