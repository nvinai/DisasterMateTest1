package com.example.calorietracker_v02;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieTrackerClient {


    final static String BASE_URL = "http://10.0.2.2:20307/FIT5046_CalorieTracker/webresources/";

    public static String GetCaloriesJSON(String userid, String date) throws JSONException {
        final String methodPath = "fit5046_package.report/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "getCalorieReport/" + userid + "/" + date);
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

    public static List<String> getCalories(String result){
        List<String> report = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(result);

            report.add((jsonObject.getString("TotalCalorieConsumption")));
            report.add(jsonObject.getString("TotalCaloriesBurned"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return report;
    }


}
