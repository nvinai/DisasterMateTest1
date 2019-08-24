package com.example.calorietracker_v02;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MainFragment extends Fragment {
    View vMain;
    ImageView img;
    EditText goalCalories;
    TextView time;
    Button submitCalories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        goalCalories = (EditText) vMain.findViewById(R.id.et_enterCalories);
        time = (TextView) vMain.findViewById(R.id.et_time);
        submitCalories = (Button) vMain.findViewById(R.id.enterCalorieButton);

        final boolean keep = true;
        Thread t = new Thread() {
            @Override
            public void run(){

                    while(keep){

                        try{
                         Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // here you check the value of getActivity() and break up if needed
                        if(getActivity() == null)
                            return;


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time = vMain.findViewById(R.id.et_time);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = simpleDateFormat.format(date);
                                time.setText(dateString);
                            }
                        });
                    }
                }

        };


        t.start();

        goalCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalCalories.setText("");
            }
        });

        goalCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalCalories.setText("");
            }
        });


        submitCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(goalCalories.getText()!=null){
                SharedPreferences sharedPref = getActivity().getSharedPreferences("calories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("goal", String.valueOf(goalCalories.getText()));
                editor.apply();
                goalCalories.setText("");
                Toast.makeText(vMain.getContext(), "Goal saved", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(vMain.getContext(), "enter calories first", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return vMain;
        }
    }

