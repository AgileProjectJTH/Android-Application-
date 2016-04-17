package agileproject.corridorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button availabilityBtn;
    Button setTimeBtn;
    Button setDateBtn;
    TextView availabilityText;
    RelativeLayout lLayout;
    CheckBox availableCheckBox;
    DatePicker unavailableDatePicker;

    boolean IsAvailable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        availabilityBtn = (Button)findViewById(R.id.availabilityBtn);
        setTimeBtn = (Button)findViewById(R.id.setTimeBtn);
        setDateBtn = (Button)findViewById(R.id.setDateBtn);

        availabilityText = (TextView)findViewById(R.id.availabilityText);

        availableCheckBox = (CheckBox)findViewById(R.id.availableCheckBox);
        unavailableDatePicker = (DatePicker)findViewById(R.id.unavailableDatePicker);

        lLayout = (RelativeLayout)findViewById(R.id.main_layout);
        if(IsAvailable)
        {
            availabilityBtn.setText(R.string.unavailable_activity_main);
            availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnUnavailable));
            availabilityText.setText(R.string.available_text_activity_main);
            lLayout.setBackgroundColor((getResources().getColor(R.color.colorAvailable)));

            availableCheckBox.setVisibility(View.INVISIBLE);
            unavailableDatePicker.setVisibility(View.VISIBLE);

            setTimeBtn.setVisibility(View.INVISIBLE);
            setDateBtn.setVisibility(View.VISIBLE);

            IsAvailable = false;
        }
        else
        {
            availabilityBtn.setText(R.string.available_activity_main);
            availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnAvailable));
            availabilityText.setText(R.string.unavailable_text_activity_main);
            lLayout.setBackgroundColor((getResources().getColor(R.color.colorUnavailable)));

            availableCheckBox.setVisibility(View.VISIBLE);
            unavailableDatePicker.setVisibility(View.INVISIBLE);

            setTimeBtn.setVisibility(View.VISIBLE);
            setDateBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
        }
        else if (id == R.id.action_login) {
            Intent i = new Intent(this, LoginActivity.class);
            this.startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void availabilityBtn_OnClick(View v){
        if(IsAvailable)
        {
            availabilityBtn.setText(R.string.unavailable_activity_main);
            availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnUnavailable));
            availabilityText.setText(R.string.available_text_activity_main);
            lLayout.setBackgroundColor((getResources().getColor(R.color.colorAvailable)));

            availableCheckBox.setVisibility(View.INVISIBLE);
            unavailableDatePicker.setVisibility(View.VISIBLE);

            setTimeBtn.setVisibility(View.INVISIBLE);
            setDateBtn.setVisibility(View.VISIBLE);

            IsAvailable = false;
        }
        else
        {
            availabilityBtn.setText(R.string.available_activity_main);
            availabilityBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnAvailable));
            availabilityText.setText(R.string.unavailable_text_activity_main);
            lLayout.setBackgroundColor((getResources().getColor(R.color.colorUnavailable)));

            availableCheckBox.setVisibility(View.VISIBLE);
            unavailableDatePicker.setVisibility(View.INVISIBLE);

            setTimeBtn.setVisibility(View.VISIBLE);
            setDateBtn.setVisibility(View.INVISIBLE);

            IsAvailable = true;
        }
    }

    public void setSettingsBtn_OnClick(View v){

    }
}
