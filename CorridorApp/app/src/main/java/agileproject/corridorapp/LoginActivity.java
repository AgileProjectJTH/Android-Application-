package agileproject.corridorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbar = (ProgressBar)findViewById(R.id.Login_progressBar);
    }

    public void Login_func(View v){
        TextView u = (TextView)findViewById(R.id.Username_txt);
        TextView p = (TextView)findViewById(R.id.Password_txt);
        String msg = "grant_type=password&username="+u.getText()+"&password="+p.getText();
        pbar.setVisibility(View.VISIBLE);
        ApiRequest.LoginRequest(msg, new ApiRequest.Callback() {
            @Override
            public void Response(String result, int code) {
                try {
                    JSONObject o = new JSONObject(result);
                    String Token = o.getString("token_type")+" "+o.getString("access_token");
                    SharedPreferences sp = getSharedPreferences("Data", MODE_PRIVATE);
                    SharedPreferences.Editor e = sp.edit();
                    e.putString("Token", Token);
                    e.apply();
                    Log.d("Result: ", sp.getString("Token", null));
                    pbar.setVisibility(View.GONE);
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Failed to login.",Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            }
        });
    }

}
