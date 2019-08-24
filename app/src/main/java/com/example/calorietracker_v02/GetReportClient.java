package com.example.calorietracker_v02;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetReportClient {

        final static String BASE_URL = "http://10.0.2.2:20307/FIT5046_CalorieTracker/webresources/";

        public static String GetReportJSON(String userid, String date) throws JSONException {
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



    public static String GetBarChartReportJSON(String userid, String startDate,String endDate) throws JSONException {
        final String methodPath = "fit5046_package.report/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + "getCalorieReportRange/" + userid + "/" + startDate + "/" + endDate);
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




    public static List<BarChartReport>  getCalorieReportList(String result){
            List<BarChartReport> reportList = new ArrayList<>();

        try {


            JSONArray jsonArray = new JSONArray(result);
//            JSONObject jsonObject = new JSONObject(result);
//            JSONArray jsonArray = new JSONArray(jsonObject);

            double totalcal,totalburn,totalrem;
            for(int i = 0; i < jsonArray.length(); i++){
                BarChartReport report = new BarChartReport(jsonArray.getJSONObject(i).getString("TotalCalorieConsumption"),jsonArray.getJSONObject(i).getString("TotalCaloriesBurned"),jsonArray.getJSONObject(i).getString("date"));

                reportList.add(report);
            }
            return reportList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

        public static Report getReport(String result){
            Report report = new Report();

            try {
                JSONObject jsonObject = new JSONObject(result);
                float totalCalorieConsumption = jsonObject.getLong("TotalCalorieConsumption");
                float totalCaloriesBurned = jsonObject.getLong("TotalCaloriesBurned");
                float totalCaloriesRemaining = totalCalorieConsumption - totalCaloriesBurned;
                report.setTotalCaloriesConsumed((float)totalCalorieConsumption);
                report.setTotalCaloriesBurned(totalCaloriesBurned);
                report.setTotalCaloriesLeft(totalCaloriesRemaining);

                return report;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

