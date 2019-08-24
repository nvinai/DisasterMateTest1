package com.example.calorietracker_v02;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepsScreenFragment extends Fragment {

    StepsDatabase db = null;
    private View vEnterSteps;

    //for the insert operation
    private EditText steps;
    private Button enterTime;
    private Button addButton;

    private int stepSum;


    //List for showing all records
    List<HashMap<String, String>> ListArray;
    private ListView listView;
    SimpleAdapter adapter;
    String[] colHEAD = new String[]{"STEPS", "TIME"};
    int[] dataCell = new int[]{R.id.steps_item, R.id.time_item};



    //for the update operation
    private Button updateButton;
    private int positionOfEntry;

    Calendar calendar = Calendar.getInstance();
    final int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    final int currentMinute = calendar.get(Calendar.MINUTE);

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)  {

        //view for the fragment
        vEnterSteps = inflater.inflate(R.layout.activity_enter_steps, container, false);
        //this creates the database
        db = Room.databaseBuilder(vEnterSteps.getContext(),StepsDatabase.class,"StepsDatabase").fallbackToDestructiveMigration().build();

         ReadDatabase readDatabase = new ReadDatabase();
         readDatabase.execute();

        listView = (ListView) vEnterSteps.findViewById(R.id.listViewSteps);
        ListArray = new ArrayList<HashMap<String, String>>();

        //this was a dummy in the list.
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("STEPS","200");
//        map.put("TIME","20:00");
//
//        ListArray.add(map);


//        final DatePickerDialog datePickerDialog = new DatePickerDialog(vEnterSteps.getContext(),android.R.style.Theme);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(vEnterSteps.getContext(), android.R.style.Theme_Black, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeset;
                if(hourOfDay <= 12){
                    timeset = "AM";
                }else {
                    timeset = "PM";
                }
                enterTime.setText(String.format("%02d:%02d",hourOfDay,minute) + timeset);
            }

        },currentHour,currentMinute,false);


        //to enter steps and time
        steps = (EditText) vEnterSteps.findViewById(R.id.steps);
        enterTime = vEnterSteps.findViewById(R.id.enterTime);
        //show entered steps and time
        enterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });


        //add to database
        addButton = (Button) vEnterSteps.findViewById(R.id.addButton);


        addButton.setOnClickListener(

                new Button.OnClickListener(){

            //including onClick() method
            public void onClick(View V){
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
            }

        });
        adapter = new SimpleAdapter(vEnterSteps.getContext(), ListArray, R.layout.steps_list_view, colHEAD, dataCell);
        listView.setAdapter(adapter);


        updateButton = (Button) vEnterSteps.findViewById(R.id.updateButton);
        updateButton.setVisibility(Button.GONE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object stepDetails = listView.getItemAtPosition(position);
                HashMap<String, String> temp = (HashMap<String, String>)stepDetails;
                String step = temp.get("STEPS");
                String time = temp.get("TIME");
                updateButton.setVisibility(Button.VISIBLE);
                addButton.setVisibility(Button.GONE);
                steps.setText(step);
                enterTime.setText(time);
                positionOfEntry = position;


            }
        });

        //TODO: Update Button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
                addButton.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.GONE);
            }
        });



        return vEnterSteps;
    }

    private class InsertDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            if (!(steps.getText().toString().isEmpty()) && !(enterTime.getText().toString().isEmpty())) {
                String step = steps.getText().toString();
                String time = enterTime.getText().toString();
                if (step != "" && time != "") {
                    Steps steps = new Steps(step, time);
                    long id = db.stepsDAO().insert(steps);
                    return (step + " " + time + " " );
                } else return "Error";
            } else return "Error";
        }

        @Override
        protected void onPostExecute(String details) {
            if (details =="Error"){
                Toast.makeText(vEnterSteps.getContext(),"Empty or invalid values",Toast.LENGTH_SHORT).show();
            }
            else {

                String[] detailsArray = details.split(" ");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("STEPS",detailsArray[0]);
                map.put("TIME",detailsArray[1]);
                Toast.makeText(vEnterSteps.getContext(),"STEPS Added",Toast.LENGTH_SHORT).show();
                addMap(map);
            }
        }
    }


    // we need to add this logic once the user goes out of the fragment and comes back
    private class ReadDatabase extends AsyncTask<Void, Void, List<Steps>> {
        @Override
        protected List<Steps> doInBackground(Void... params) {
            List<Steps> steps = db.stepsDAO().getAll();

            if (!(steps.isEmpty() || steps == null)) {
                return steps;

            } else return null;
        }

        @Override
        protected void onPostExecute(List<Steps> steps) {
            stepSum = 0;
            if (steps == null) {
                Toast.makeText(vEnterSteps.getContext(), "The list is Empty", Toast.LENGTH_SHORT).show();
            } else {
                if (!(steps.isEmpty() || steps == null)) {
                    for (Steps temp : steps) {
                        stepSum+= Integer.parseInt(temp.getSteps());
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("STEPS", temp.getSteps());
                        map.put("TIME", temp.getTime());
                        addMap(map);
                    }
                    Toast.makeText(vEnterSteps.getContext(), "STEPS loaded from SQLite", Toast.LENGTH_SHORT).show();
                    Toast.makeText(vEnterSteps.getContext(), "Total Steps" + stepSum, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("steps", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("totalSteps", String.valueOf(stepSum));
                    editor.apply();
                }


            }
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsDAO().deleteAll();
            return null;
        }

        protected void onPostExecute(Void param) {
            Toast.makeText(vEnterSteps.getContext(),"Previous day's steps list has been cleared at 11:59PM ",Toast.LENGTH_SHORT).show();

        }
    }


    //TODO: update database operation
    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Steps user = null;
            String step = steps.getText().toString();
            String time = enterTime.getText().toString();

            if (step != "" && time !="") {
                user = db.stepsDAO().findByID(positionOfEntry);
                user.setSteps(step);
                user.setTime(time);
            }
            if (user != null) {

                db.stepsDAO().updateUsers(user);
                return ( positionOfEntry + " " + step + " " + time);
            }
            return "error";
        }

        @Override
        protected void onPostExecute(String details) {
            String[] detailArray = details.split(" ");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("STEPS",detailArray[1]);
            map.put("TIME",detailArray[2]);
            updateMap(map);

        }
    }


    protected void addMap(HashMap map) {
        ListArray.add(map);
        adapter = new SimpleAdapter(vEnterSteps.getContext(), ListArray, R.layout.steps_list_view, colHEAD, dataCell);
        //adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }


    protected void updateMap(HashMap map){
        ListArray.get(positionOfEntry).replace("TIME",map.get("TIME").toString());
        ListArray.get(positionOfEntry).replace("STEPS",map.get("STEPS").toString());
        adapter = new SimpleAdapter(vEnterSteps.getContext(), ListArray, R.layout.steps_list_view, colHEAD, dataCell);
        //adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }





    }


