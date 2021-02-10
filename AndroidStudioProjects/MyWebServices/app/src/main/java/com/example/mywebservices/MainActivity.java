package com.example.mywebservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText etCity, etCountry;
    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = (EditText)findViewById(R.id.etCity);
        etCountry = (EditText)findViewById(R.id.etCountry);
        tvDisplay = (TextView)findViewById(R.id.tvDisplay);

//        String JsonFromURL = "{" +
//                "'info':{'name':'Subhankar', 'age':23}" +
//                "'jobs':" +
//                "{" +
//                "{'id':1, 'title:'developer', 'desc':'nyc}," +
//                "{'id':2, 'title:'developer', 'desc':'nyc}," +
//                "{'id':3, 'title:'developer', 'desc':'nyc}," +
//                "]" +
//                "}";
//        try{
//            JSONObject json = new JSONObject(JsonFromURL);
//            JSONObject info = json.getJSONObject("info");
//            String name = info.getString("name");
//            int age = info.getInt("age");
//
//            JSONArray jobs = json.getJSONArray("jobs");
//            for(int i=0; i<jobs.length(); i++){
//                JSONObject Jobs = jobs.getJSONObject(i);
//                String title = Jobs.getString("title");
//                String desc = Jobs.getString("desc");
//                int id = Jobs.getInt("id");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void buGet(View view) {
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
//        city = "London";
        String url = "https://community-open-weather-map.p.rapidapi.com/weather?q="+ city + "%2C" + country +"&units=%22metric%22";
        new MyAsyncTaskgetNews().execute(url, "news");

    }
    // get news from server
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "af2b23c2a6msh6a9fe9ee5b0b967p1bbbd7jsnac687a9a18b3");
                urlConnection.setRequestProperty("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");

                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                //display response data
//                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();
//                Log.d("SUBHANKARDEBUGS", progress[0]);
                tvDisplay.setText(progress[0]);

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }
    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }


}