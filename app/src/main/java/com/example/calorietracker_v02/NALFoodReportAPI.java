package com.example.calorietracker_v02;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NALFoodReportAPI {


    private static final String API_KEY = "mYKJJ400Y65Yg6uHSVfPTCMh28as3MtWeoH1VaTh";


    public static String search(String keyword, String[] params, String[] values) {

        keyword = keyword.replace(" ", "+");

        URL url = null;

        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        String ndbno = NalFoodSearchAPI.getNDBNO(NalFoodSearchAPI.search(keyword, new String[]{"max","ds"}, new String[]{"1","Standard Reference"}));

        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://api.nal.usda.gov/ndb/V2/reports?ndbno=" + ndbno + "&format=json" + "&api_key=" + API_KEY + query_parameter);

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");

            //content is received and transmitted via JSON
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }


    /*To extract the snippet information from the entire JSON response*/
    public static String getCaloriesAndFat(String result) {


        String calories = null;
        String caloriesUnit = null;
        String fat = null;
        String fatUnit = null;

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonObject1 = jsonObject.getJSONArray("foods");

            JSONObject food = jsonObject1.getJSONObject(0);

            JSONObject obj = food.getJSONObject("food");

            JSONArray nutrients = obj.getJSONArray("nutrients");

            if (nutrients != null && nutrients.length() > 0) {
                calories = nutrients.getJSONObject(1).getString("value");
                caloriesUnit = nutrients.getJSONObject(1).getString("unit");
                fat = nutrients.getJSONObject(3).getString("value");
                fatUnit = nutrients.getJSONObject(1).getString("unit");

            }

        } catch (Exception e) {
            e.printStackTrace();
            calories = "NO INFO FOUND";
        }
        return calories + " " + caloriesUnit + " " + fat + " " + fatUnit;
    }
}
