package com.example.calorietracker_v02;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NalFoodSearchAPI {


    private static final String API_KEY = "mYKJJ400Y65Yg6uHSVfPTCMh28as3MtWeoH1VaTh";


    public static String search(String keyword, String[] params, String[] values) {

        keyword = keyword.replace(" ", "+");

        URL url = null;

        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";

        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q=" + keyword + "&api_key=" + API_KEY + query_parameter);
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
    public static String getSnippet(String result) {
        String snippet = null;
        String name = null;
        String ndbno = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("list");
            JSONArray jsonArray = jsonObject2.getJSONArray("item");

            if (jsonArray != null && jsonArray.length() > 0) {
                 name = jsonArray.getJSONObject(0).getString("name");
                 ndbno = jsonArray.getJSONObject(0).getString("ndbno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return name;
    }


    /*To extract the snippet information from the entire JSON response*/
    public static String getNDBNO(String result) {
        String ndbno = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("list");
            JSONArray jsonArray = jsonObject2.getJSONArray("item");

            if (jsonArray != null && jsonArray.length() > 0) {
                ndbno = jsonArray.getJSONObject(0).getString("ndbno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ndbno = "NO INFO FOUND";
        }
        return ndbno;
    }


}
