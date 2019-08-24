package com.example.calorietracker_v02;

import android.util.Log;

import org.json.JSONArray;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.util.Random;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostToDB {

    final static String BASE_URL = "http://10.0.2.2:20307/FIT5046_CalorieTracker/webresources/";

    //Generates a user ID for the post method
    public static Integer generateUserId() {
        final String methodPath = "fit5046_package.appuser/findByUserid/";
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

        //Trying to check if the generated ID exists in the database, if empty we set this as the user id else we call the get method till we find an appropriate id

        if (textResult != "[]")
            return n;
        else if (textResult == "ERROR") {
            return null;
        }
        else
            return generateUserId();
    }


    public static void createUser(Appuser user) {

        final String methodPath = "fit5046_package.appuser/";
        //initialise URL url = null;
        URL url = null;
        HttpURLConnection conn = null;
        try {
        //not posting..
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(user);

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


    public static void createCredential(Credential credential) {

        final String methodPath = "fit5046_package.credential/";
        URL url = null;
        HttpURLConnection conn = null;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.XXX").create();
        String credentialJSON = gson.toJson(credential);

        try {

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
            conn.setFixedLengthStreamingMode(credentialJSON.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(credentialJSON);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }


    }
}



