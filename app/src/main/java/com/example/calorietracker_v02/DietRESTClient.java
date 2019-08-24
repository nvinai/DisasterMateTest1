package com.example.calorietracker_v02;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DietRESTClient {


    //This is my home wifi address
    //118.139.82.27 for monash
//118.138.161.157
//10.0.2.2 for mobile
    final static String BASE_URL = "http://10.0.2.2:20307/FIT5046_CalorieTracker/webresources/";

    public static String getAllCategories() throws JSONException {
        final String methodPath = "fit5046_package.food/findAllCategories/";
        //initialise
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


    public static String getFoodFromCategories(String keyword) throws JSONException {
        final String methodPath = "fit5046_package.food/findByCategory/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + keyword);
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


    public static String getDescriptionFromFood(String keyword) throws JSONException {
        final String methodPath = "fit5046_package.food/findByName/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + keyword);
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
    public static List<String> getFoodNameSnippet(String result) {
        String name = null;
        String calories = null;
        String fat = null;
        try {
            JSONArray jsonArray = new JSONArray(result);
            final int numberOfItems = jsonArray.length();


//            JSONArray jsonArray = jsonObject;
//            JSONObject jsonObject = new JSONObject(result);
//            JSONObject jsonObject2 = jsonObject.getJSONObject("list");
//
//            int i = 0;
            List<String> categories = new ArrayList<>();
//            while (jsonArray != null && jsonArray.length() > 0)
            for (int i = 0; i < numberOfItems; i++) {

                name = jsonArray.getJSONObject(i).getString("fname");
//                calories = jsonArray.getJSONObject(i).getString("calorieamount");
//                fat = jsonArray.getJSONObject(i).getString("fat");
                categories.add(name);
                i++;
            }
            return categories;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*To extract the snippet information from the entire JSON response*/
    public static List<String> getSnippet(String result) {
        String snippet = null;
        String name = null;
        String ndbno = null;
        try {
            JSONArray jsonArray = new JSONArray(result);
            final int numberOfItems = jsonArray.length();


//            JSONArray jsonArray = jsonObject;
//            JSONObject jsonObject = new JSONObject(result);
//            JSONObject jsonObject2 = jsonObject.getJSONObject("list");
//
//            int i = 0;
            List<String> categories = new ArrayList<>();
//            while (jsonArray != null && jsonArray.length() > 0)
            for (int i = 0; i < numberOfItems; i++) {

                name = jsonArray.getJSONObject(i).getString("Category");
                categories.add(name);
                i++;

            }
            return categories;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return null;
    }

    public static List<String> getDescriptionFromFoodSnippet(String result) {
        String name = null;
        String calories = null;
        String fat = null;
        try {
            JSONArray jsonArray = new JSONArray(result);
            final int numberOfItems = jsonArray.length();


//            JSONArray jsonArray = jsonObject;
//            JSONObject jsonObject = new JSONObject(result);
//            JSONObject jsonObject2 = jsonObject.getJSONObject("list");
//
            List<String> description = new ArrayList<>();
            if (jsonArray != null && jsonArray.length() > 0) {

                name = jsonArray.getJSONObject(0).getString("fname");
                calories = jsonArray.getJSONObject(0).getString("calorieamount");
                fat = jsonArray.getJSONObject(0).getString("fat");
                description.add(name);
                description.add(calories);
                description.add(fat);

                return description;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
//
        return null;
    }

    public static void PostFood(Food food) {

        final String methodPath = "fit5046_package.food/";
        //initialise URL url = null;
        URL url = null;
        HttpURLConnection conn = null;
        try {
            //not posting..
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(food);

            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static Integer generateFoodId() {
        final String methodPath = "fit5046_package.food/findByFoodid/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        Random rand = new Random();
        int n = rand.nextInt(50) + 1;

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + String.valueOf(n));
//            url = new URL(BASE_URL + methodPath + 1);

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
            textResult = "ERROR";
        } finally {
            conn.disconnect();
        }

        if (textResult != "[]")
            return n;
        else if (textResult == "ERROR") {
            return null;
        } else
            return generateFoodId();
    }
}

