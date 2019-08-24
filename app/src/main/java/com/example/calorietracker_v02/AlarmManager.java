package com.example.calorietracker_v02;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AlarmManager extends IntentService {
    static int counter = 0;

    public AlarmManager() {
        super("ScheduledIntentService");
    }

    @Override
    protected
    void onHandleIntent(Intent intent) {
        counter++;
        Date currentTime = Calendar.getInstance().getTime();
        String strTime = currentTime.toString();


        //TODO: set a time to fire the alarm manager
        //sets the alarm to at 11:59 PM everyday
//        Calendar scheduledTime = Calendar.getInstance();
//        scheduledTime.setTimeInMillis(System.currentTimeMillis());
//        scheduledTime.set(Calendar.HOUR_OF_DAY, 23);
//        scheduledTime.set(Calendar.MINUTE, 59);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("service", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("service", " " + counter + "  " + strTime);
        spEditor.apply();
        Log.i("message   ", "The number of runs:  " + counter + " times" + "  " + strTime);
    }

    @Override
    public
    int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}