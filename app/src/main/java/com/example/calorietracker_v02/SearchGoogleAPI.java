package com.example.calorietracker_v02;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyAbjpwZ5ftHrIC9pJYp0zaJslWSOq8mT3Y";
    private static final String SEARCH_ID_cx = "010164075712876805716:i3sqw6ua_bi";


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
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + "AIzaSyAbjpwZ5ftHrIC9pJYp0zaJslWSOq8mT3Y" + "&cx=" + "010164075712876805716:i3sqw6ua_bi" + "&q=" + keyword + query_parameter);
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
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("snippet");
                snippet = snippet.replaceAll("[...]","");

            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    /*To extract the image link information from the entire JSON response*/
    public static String getImageLink(String result) {
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonlinkArray = jsonObject.getJSONArray("items");

            if (jsonlinkArray != null && jsonlinkArray.length() > 0) {
                snippet = jsonlinkArray.getJSONObject(0).get("link").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

}