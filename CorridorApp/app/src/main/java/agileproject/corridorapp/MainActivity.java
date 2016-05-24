package agileproject.corridorapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button availabilityBtn;
    Button setSettingBtn;
    TextView availabilityText;
    TextView settingInfo;
    RelativeLayout lLayout;
    CheckBox availableCheckBox;
    private String todate;
    private ProgressBar pbar;

    Calendar calender = Calendar.getInstance();

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawrtoggle;
    private DrawerLayout mDrawerlayout;

    boolean IsAvailable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        availabilityBtn = (Button)findViewById(R.id.availabilityBtn);
        setSettingBtn = (Button)findViewById(R.id.setSettingBtn);

        availabilityText = (TextView)findViewById(R.id.availabilityText);
        settingInfo = (TextView)findViewById(R.id.settingInfo);

        pbar = (ProgressBar)findViewById(R.id.main_progressBar);
        availableCheckBox = (CheckBox)findViewById(R.id.availableCheckBox);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawrtoggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerOpened(View DrawerView){
                super.onDrawerOpened(DrawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view){
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle("CorridorApp");
                invalidateOptionsMenu();
            }
        };
        lLayout = (RelativeLayout)findViewById(R.id.main_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        String date = df.format(d).toString();
        Log.d("Date ", date);
        SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
        ApiRequest.SendRequest("GET", "api/staff?dateAndTime=" + date, null, sp.getString("Token", null), new ApiRequest.Callback() {
            @Override
            public void Response(String result) {
                if(result == null)
                    SetUnlogged();
                else{
                    mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("hej: ", view.toString() + " " + position);
                            if(position == 1)
                            {
                                SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
                                SharedPreferences.Editor e = sp.edit();
                                e.putString("Token", null);
                                e.apply();
                                mDrawerlayout.closeDrawer(mDrawerList);
                                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                            }
                        }
                    });
                    addDrawerItems(true);
                    DrawerSetup();
                    if(result.equals("true"))
                        SetAvailable();
                    else if(result.equals("false"))
                        SetUnavailable();
                }
            }
        });
    }
    private void SetUnlogged(){
        availabilityText.setText(R.string.Not_logged_in);
        availabilityBtn.setVisibility(View.GONE);
        setSettingBtn.setVisibility(View.GONE);
        availableCheckBox.setVisibility(View.GONE);
        settingInfo.setVisibility(View.GONE);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hej: ", view.toString() + " " + position);
                if(position == 1)
                {
                    mDrawerlayout.closeDrawer(mDrawerList);
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }
            }
        });
        addDrawerItems(false);
        DrawerSetup();
        IsAvailable = false;
    }

    private void SetAvailable(){
        availabilityBtn.setVisibility(View.VISIBLE);
        setSettingBtn.setVisibility(View.VISIBLE);
        settingInfo.setVisibility(View.VISIBLE);
        availabilityBtn.setText(R.string.unavailable_activity_main);
        availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnUnavailable));
        availabilityText.setText(R.string.available_text_activity_main);
        lLayout.setBackgroundColor((getResources().getColor(R.color.colorAvailable)));
        setSettingBtn.setText(R.string.setDate_activity_main);
        availableCheckBox.setVisibility(View.INVISIBLE);
        settingInfo.setText(R.string.setDateInfo_activity_main);
        IsAvailable = false;
    }
    private void SetUnavailable(){
        availabilityBtn.setVisibility(View.VISIBLE);
        setSettingBtn.setVisibility(View.VISIBLE);
        settingInfo.setVisibility(View.VISIBLE);
        availabilityBtn.setText(R.string.available_activity_main);
        availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnAvailable));
        availabilityText.setText(R.string.unavailable_text_activity_main);
        lLayout.setBackgroundColor((getResources().getColor(R.color.colorUnavailable)));
        availableCheckBox.setVisibility(View.VISIBLE);
        settingInfo.setText(R.string.setTimeInfo_activity_main);
        setSettingBtn.setText(R.string.setTime_activity_main);
        IsAvailable = true;
    }
    private void addDrawerItems(boolean Loggedin) {
        String[] items = new String[2];
        if(!Loggedin){
            items[0] = "Login"; items[1] = "Login";}
        else{
            items[0] = "Logout"; items[1] = "Logout";}
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mDrawerList.setAdapter(mAdapter);
    }
    private void DrawerSetup(){
        mDrawrtoggle.setDrawerIndicatorEnabled(true);
        mDrawerlayout.setDrawerListener(mDrawrtoggle);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawrtoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawrtoggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if(mDrawrtoggle.onOptionsItemSelected(item)){
            return true;
        }
        /*if (id == R.id.action_home) {
            return true;
        }
        else if (id == R.id.action_login) {
            Intent i = new Intent(this, LoginActivity.class);
            this.startActivity(i);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    public void availabilityBtn_OnClick(View v){
        if(IsAvailable)
        {
            Date from = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fromdate = df.format(from);
            String json = "{\"fromDateAndTime\":\""+from+"\",\"available\":\"false\"}";
            SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
            pbar.setVisibility(View.VISIBLE);
            ApiRequest.SendRequest("POST", "api/schedule", json, sp.getString("Token", null), new ApiRequest.Callback() {
                @Override
                public void Response(String result) {
                    if(result.equals("200")){
                        SetUnavailable();
                        IsAvailable = false;}
                    else
                        Toast.makeText(getApplicationContext(),"Could not change your status.",Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            Date from = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fromdate = df.format(from);
            String json = "{\"fromDateAndTime\":\""+from+"\",\"available\":\"true\"}";
            SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
            pbar.setVisibility(View.VISIBLE);
            ApiRequest.SendRequest("POST", "api/schedule", json, sp.getString("Token", null), new ApiRequest.Callback() {
                @Override
                public void Response(String result) {
                    if(result.equals("200")){
                        SetUnavailable();
                        IsAvailable = true;}
                    else
                        Toast.makeText(getApplicationContext(),"Could not change your status.",Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            });
        }
    }

    public void setSettingBtn_OnClick(View v){
        if(IsAvailable) {
            new TimePickerDialog(MainActivity.this, timeListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show();
        }
        else {
            new DatePickerDialog(MainActivity.this, dateListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void setTimeBtn_OnClick(View v){
        new TimePickerDialog(MainActivity.this, timeListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show();
    }

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Date fromdate = new Date();
            Date todate = new Date();
            todate.setHours(hourOfDay);
            todate.setMinutes(minute);
            Log.d("date: ", ""+fromdate.compareTo(todate));
            if(fromdate.compareTo(todate)>0){
                todate.setDate(todate.getDay()+1);
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String from = df.format(fromdate);
            String to = df.format(todate);
            String json = "{\"fromDateAndTime\":\""+from+"\",\"toDateAndTime\":\""+to+"\",\"available\":\"true\"}";
            SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
            pbar.setVisibility(View.VISIBLE);
            ApiRequest.SendRequest("POST", "api/schedule", json, sp.getString("Token", null), new ApiRequest.Callback() {
                @Override
                public void Response(String result) {
                    if(result.equals("200"))
                        SetUnavailable();
                    else
                        Toast.makeText(getApplicationContext(),"Could not change your status.",Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            });
        }
    };

    public void setDateBtn_OnClick(View v){
        new DatePickerDialog(MainActivity.this, dateListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String month = (monthOfYear < 10 ? "0" : "") + monthOfYear;
            String day = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
            todate = year+"-"+month+"-"+day+" 00:00:00";
            Date from = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fromdate = df.format(from);
            String json = "{\"fromDateAndTime\":\""+fromdate+"\",\"toDateAndTime\":\""+todate+"\",\"available\":\"false\"}";
            Log.d("This shit ", json);
            SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
            pbar.setVisibility(View.VISIBLE);
            ApiRequest.SendRequest("POST", "api/schedule", json, sp.getString("Token", null), new ApiRequest.Callback() {
                @Override
                public void Response(String result) {
                    if(result.equals("200"))
                        SetUnavailable();
                    else
                        Toast.makeText(getApplicationContext(),"Could not change your status.",Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            });
            //https://www.youtube.com/watch?v=Qx23fC4Wgpw for more info
        }
    };

}
