package com.example.snf.nasamarsrover;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by SNF on 13-3-2018.
 */

public class ListItemTask extends AsyncTask<String, Void, String> {
    private OnListItemAvailable listener = null;

    private static final String TAG = "ListItemTask";
    public ListItemTask(OnListItemAvailable listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, " calls doInBackground ");

        InputStream inputStream = null;
        int responsCode = -1;

        String itemUrl = params[0];
        String response = "";

        try {
            URL url = new URL(itemUrl);
            URLConnection urlConnection = url.openConnection();

            if(!(urlConnection instanceof HttpURLConnection)){
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            httpConnection.connect();

            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            } else {
                Log.e(TAG, "Error, invalid response");
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }

        // Hier eindigt deze methode.
        // Het resultaat gaat naar de onPostExecute methode.
        return response;
    }


    @Override
    protected void onPostExecute(String response) {
        Log.i(TAG, "onPostExecute " + response);

        // Check of er een response is
        if(response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        // Het resultaat is in ons geval een stuk tekst in JSON formaat.
        // Daar moeten we de info die we willen tonen uit filteren (parsen).
        // Dat kan met een JSONObject.
        JSONObject jsonObject;
        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            // Get all items and start looping
            JSONArray items = jsonObject.getJSONArray("photos");
            for(int idx = 0; idx < items.length(); idx++) {
                // array level objects and get user
                JSONObject item = items.getJSONObject(idx);

                int id = item.getInt("id");
                // camera object
                JSONObject camera = item.getJSONObject("camera");
                String name = camera.getString("name");
                String fullName = camera.getString("full_name");

                String imgURL = item.getString("img_src");
                Log.i(TAG, "Got item " + name + " " + fullName + " " + imgURL);


                // Create new ListItem object
                ListItem listItem = new ListItem(id, imgURL, fullName);
                //
                // call back with new person data
                //

                listener.OnListItemAvailable(listItem);
            }
        } catch( JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }

        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();
        }

}
