package com.example.calorietracker_v02;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*TODO:Alarm Manager*/
//        Log.i(" Starting in Activity ", "Setting alarm!!");
//        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmIntent = new Intent(this, AlarmManager.class);
//        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
//        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
//
//        SharedPreferences spMyUnits = getSharedPreferences("service", Context.MODE_PRIVATE);
//        String message = spMyUnits.getString("service", null);
//        textView.setText(message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calorie Tracker Home");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch (id) {
            case R.id.nav_homescreen:
                nextFragment = new MainFragment();
                getSupportActionBar().setTitle("Calorie Tracker Home");
                break;
            case R.id.nav_step_tracker:
                nextFragment = new StepsScreenFragment();
                getSupportActionBar().setTitle("Steps Tracker");
                break;
            case R.id.nav_cal_tracker:
                nextFragment = new CalorieTrackerFragment();
                getSupportActionBar().setTitle("Calories");
                break;
            case R.id.nav_report:
                nextFragment = new PieChartFragment();
                getSupportActionBar().setTitle("Pie Chart");
                break;
            case R.id.nav_Bar:
                nextFragment = new BarChartFragment();
                getSupportActionBar().setTitle("Bar Chart");
                break;
            case R.id.nav_dietScreen:
                nextFragment = new DietScreen();
                getSupportActionBar().setTitle("My Daily Diet");
                break;
            case R.id.nav_maps:
                nextFragment = new MapsFragment();
                getSupportActionBar().setTitle("Places and Parks");
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
