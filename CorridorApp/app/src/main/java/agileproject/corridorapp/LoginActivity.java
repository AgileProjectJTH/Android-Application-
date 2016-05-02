package agileproject.corridorapp;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawrtoggle;
    private DrawerLayout mDrawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hej: ", view.toString() + " " + position);
                if(position == 1)
                {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }
            }
        });
        addDrawerItems();
        DrawerSetup();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private void addDrawerItems() {
        String[] items = { "Main", "Main" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mDrawerList.setAdapter(mAdapter);
    }
    private void DrawerSetup(){
        mDrawrtoggle.setDrawerIndicatorEnabled(true);
        mDrawerlayout.setDrawerListener(mDrawrtoggle);
    }
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

        if(mDrawrtoggle.onOptionsItemSelected(item)){
            return true;
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_home) {
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            return true;
        }
        else if (id == R.id.action_login) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
