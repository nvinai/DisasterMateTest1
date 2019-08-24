package com.example.calorietracker_v02;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GoogleMapsRESTClient {

        final static String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

        public static String getParkJSON(String latitude, String longitude, String PROXIMITY_RADIUS, String nearbyPlace) throws JSONException {
            final String methodPath = "location=" + latitude + "," + longitude + "&radius="+ PROXIMITY_RADIUS +"&type="+ nearbyPlace+"&key=AIzaSyDBFFyUnADAPHlK2yNx4VeEFtANfwEcbLA";

            URL url = null;
            HttpURLConnection conn = null;
            String textResult = "";

            //Making HTTP request
            try {
                url = new URL(BASE_URL + methodPath);
                conn = (HttpURLConnection) url.openConnection();
                //set the timeout
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                //set the connection method to GET
                conn.setRequestMethod("GET");
                //add http headers to set your response type to json
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                //Read the response
                Scanner inStream = new Scanner(conn.getInputStream());
                //read the input stream and store it as string
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;
        }


    /*To extract the snippet information from the entire JSON response*/
    public static List<Park> getParks(String result) {

        String snippet = null;
        List<Park> parks = new LinkedList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            int i = 0;


                while (jsonArray!=null && jsonArray.length() > 0){

                Object location = (JSONObject) jsonArray.getJSONObject(i).get("geometry");
                Object location1 = ((JSONObject) location).get("location");
                String lat =   ((JSONObject) location1).getString("lat");
                String lon = ((JSONObject) location1).getString("lng");
                String name = jsonArray.getJSONObject(i).getString("name");
                String vicinity = jsonArray.getJSONObject(i).getString("vicinity");

                double lat1 = Double.parseDouble(lat);
                double lon1 = Double.parseDouble(lon);

                parks.add(new Park(lon1,lat1,name,vicinity));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return parks;
    }


}



