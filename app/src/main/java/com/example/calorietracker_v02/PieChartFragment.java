package com.example.calorietracker_v02;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;





/*TODO: Pie chart to show the total cal, burned cals and remaining cals for a date by calling the REST method*/
public class PieChartFragment extends Fragment  implements DatePickerDialog.OnDateSetListener {
    //need to use date picker
    //show labels and percentages in the pie chart.

    private View vPie;
    private static String TAG = "PieChartFragment";
    private Button enterDate;
    private Button generateReport;

    //float values of y Data
//    private float[] yData = {11f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    public float[] yData = null;
    //    private String[] xData = {"Mitch", "Jessica", "Mohammad", "Kelsey", "Sam", "Robert", "Ashley"};
    public String[] xData = {"TotalCaloriesConsumed","TotalCaloriesBurned","TotalCaloriesLeft"};
    PieChart pieChart;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        vPie = inflater.inflate(R.layout.activity_pie_chart, container, false);

        Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) vPie.findViewById(R.id.idPieChart);
        enterDate = (Button) vPie.findViewById(R.id.enterDate);
        generateReport = (Button) vPie.findViewById(R.id.generateReport);

        Description description = new Description();
        description.setText("Calories Report");

        pieChart.setDescription(description);
//        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
//        pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Calorie Report");
        pieChart.setCenterTextSize(20);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!





        //we call the async function here.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("id", null);


//        enterDate.setText("2019-04-04");


//        final String dateString = enterDate.getText().toString();

        generateReport.setVisibility(View.GONE);


        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDetails reportDetails = new ReportDetails();
                reportDetails.execute(id,enterDate.getText().toString());
            }
        });



        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(vPie.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                enterDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                generateReport.setVisibility(View.VISIBLE);
            }

        });


//        addDataSet();




        return vPie;
    }

    public void addDataSet(float[] yData) {
        Log.d(TAG, "addDataSet started");

        //This is where we call the rest methods to get the necessary data.
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
//        ArrayList<String> xEntrys = new ArrayList<>();



        if(yData == null){
            Toast.makeText(vPie.getContext(), "No Data to Show", Toast.LENGTH_SHORT).show();
        } else
        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i] ));
        }


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Calorie Report");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(5);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


    }


    private class ReportDetails extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            String result = GetReportClient.GetReportJSON(params[0],params[1]);
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //null
        Report report = GetReportClient.getReport(result);
        float getCaloriesConsumed = (float) report.getTotalCaloriesConsumed();
        float getCaloriesBurned = (float) report.getTotalCaloriesBurned();
        float getCaloriesRemaining = (float) report.getTotalCaloriesLeft();
        float[] calories = {getCaloriesConsumed,getCaloriesBurned,getCaloriesRemaining};
        addDataSet(calories);
    }

}
}