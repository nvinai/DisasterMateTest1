package com.example.calorietracker_v02;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BarChartFragment extends Fragment {
//    View vLine;
    View vBar;
    private BarChart chart;
    List<BarEntry> totalCaloriesConsumedList;
    List<BarEntry> totalCaloriesBurnedList;
//    String[] years = new ArrayList;
    Button generate;
    EditText startDate,endDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         vBar = inflater.inflate(R.layout.activity_line_chart, container, false);
         generate = vBar.findViewById(R.id.generate);
         startDate = vBar.findViewById(R.id.start_date);
         endDate = vBar.findViewById(R.id.end_date);
         chart =(BarChart) vBar.findViewById(R.id.chart);


         totalCaloriesConsumedList = new ArrayList<BarEntry>();
         totalCaloriesBurnedList = new ArrayList<BarEntry>();

//         BarEntry b1 = new BarEntry(1f,100);
//         BarEntry b2 = new BarEntry(0.5f,90);
//         BarEntry b3 = new BarEntry(1f,70);

//         totalCaloriesConsumedList.add(b1);
//         totalCaloriesBurnedList.add(b2);



        // implementing IAxisValueFormatter interface to show year values not as float/decimal
//         years = new String[] { "2015", "2016", "2017", "2018","2019" };




        SharedPreferences useridsharedPreferences = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);

       final String id = useridsharedPreferences.getString("id", null);

            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String startDateString = startDate.getText().toString();
                    String endDateString = endDate.getText().toString();
                    CalorieReport calorieReport = new CalorieReport();
                    calorieReport.execute(id,startDateString,endDateString);
                }
            });





         return vBar;
    }


private class CalorieReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = GetReportClient.GetBarChartReportJSON(params[0],params[1],params[2]);
                return result;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            List<BarChartReport> reports = GetReportClient.getCalorieReportList(result);

          final   List<String> years = new ArrayList<>();

          float i=0;


            for (BarChartReport breport : reports) {
                BarEntry b1 = new BarEntry(i, Float.parseFloat(breport.getTotalCaloriesConsumed()));
                totalCaloriesConsumedList.add(b1);
                BarEntry b2 = new BarEntry((float)0.5*i, Float.parseFloat(breport.getTotalCaloriesBurned()));
                totalCaloriesBurnedList.add(b2);
                years.add(breport.getDate());
                i++ ;}
            final String[] years1 = years.toArray(new String[years.size()]);





            IAxisValueFormatter formatter = new IAxisValueFormatter()
                {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        //provide dates here
                        return years1[(int)value];
                    }
                };

                BarDataSet dataConsume = new BarDataSet(totalCaloriesConsumedList,"Calories Consumed");
                BarDataSet dataBurn = new BarDataSet(totalCaloriesBurnedList,"Calories Burned");

                dataConsume.setColor(Color.YELLOW);
                dataBurn.setColor(Color.RED);

                dataBurn.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataConsume.setAxisDependency(YAxis.AxisDependency.LEFT);

                BarData caldata = new BarData(dataConsume,dataBurn);

                chart.setData(caldata);

                XAxis xAxisFromChart = chart.getXAxis();
                xAxisFromChart.setDrawAxisLine(true);
                xAxisFromChart.setValueFormatter(formatter);
                // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
                xAxisFromChart.setGranularity(1f);
                xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);





            }




        }
    }

