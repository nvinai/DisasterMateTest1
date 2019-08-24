package com.example.calorietracker_v02;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalorieTrackerFragment extends Fragment {
    View vCal;
    TextView goal;
    TextView totalSteps;
    TextView totalCalCon;
    TextView totalCalBurn;
    String id, goalresult;
    Calendar today = Calendar.getInstance();
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set Variables and listeners
        vCal = inflater.inflate(R.layout.activity_calorie_screen, container, false);

        goal = (TextView) vCal.findViewById(R.id.goal);
        totalSteps = (TextView) vCal.findViewById(R.id.totalSteps);
        totalCalCon = (TextView) vCal.findViewById(R.id.consume);
        totalCalBurn = (TextView) vCal.findViewById(R.id.burn);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("calories", Context.MODE_PRIVATE);
                goalresult = sharedPreferences.getString("goal", null);

                String totalStepsresult = sharedPreferences.getString("totalSteps", null);

                goal.setText("TODAY'S GOAL\n" + goalresult + " CALORIES");
                totalSteps.setText("TOTAL STEPS TAKEN\n" + totalStepsresult + " STEPS");

                SharedPreferences useridsharedPreferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                id = useridsharedPreferences.getString("id", null);



        CalorieTrackerReport calorieTrackerReport = new CalorieTrackerReport();
        calorieTrackerReport.execute();



        return vCal;
    }


    class CalorieTrackerReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = CalorieTrackerClient.GetCaloriesJSON(id,dateFormat.format(date));
                return result;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            List<String> report = CalorieTrackerClient.getCalories(result);

            totalCalCon.setText("TOTAL CALORIES CONSUMED:\n"+ report.get(0) + " CALORIES");
            totalCalBurn.setText("TOTAL CALORIES BURNED \n" + report.get(1) + " CALORIES");
        }
    }

}