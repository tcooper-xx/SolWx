package org.redout.solwx;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.redout.solwx.weather.BarometerData;
import org.redout.solwx.weather.GetWeatherTask;
import org.redout.solwx.weather.darksky.DarkSkyRetrofitInstance;
import org.redout.solwx.weather.darksky.DarkSkyServiceBundle;
import org.redout.solwx.weather.darksky.GetDarkSkyDataService;
import org.redout.solwx.weather.darksky.generated.WeatherData;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private SensorManager sensorManager;
    private Sensor pressureSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultInit();
        setupTabs();
        getDarkSkyData(41.216932,-96.1678416,"7d6f4a8df38879f75828056048610703");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);







    }
    public void capturePressureData(Context context, float pressure) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        BarometerData bdata = new BarometerData();
        bdata.setBarometer(Double.valueOf(pressure));
        bdata.setObservedDate(new Date().getTime());
        db.barometerDataDAO().insertAll(bdata);

        List<BarometerData> recordedData = db.barometerDataDAO().getAll();
        for (BarometerData b : recordedData) {
            System.out.println(b.getObservedDate() + " - " + b.getBarometer());
        }
    }

    public void getDarkSkyData(double lat, double lon, String apikey) {
        GetDarkSkyDataService service = DarkSkyRetrofitInstance.getDarkSkyRetofitInstance().create(GetDarkSkyDataService.class);
        DarkSkyServiceBundle serviceBundle= new DarkSkyServiceBundle();
        serviceBundle.setLat(lat);
        serviceBundle.setLon(lon);
        serviceBundle.setApiKey(apikey);
        serviceBundle.setService(service);

        GetWeatherTask getWeather = new GetWeatherTask(this);
        System.out.println("calling service");
        getWeather.execute(serviceBundle);

    }

    private void setupTabs() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new DailyForecastFragment(), "Daily Forecast");
        adapter.addFragment(new HourlyForecastFragment(), "Hourly Forecast");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void defaultInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getDarkSkyData(41.216932,-96.1678416,"7d6f4a8df38879f75828056048610703");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            //Update DB
            capturePressureData(getApplicationContext(), values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Registering Pressure Listener");
        sensorManager.unregisterListener(sensorEventListener);
        sensorManager.registerListener(sensorEventListener, pressureSensor,1000000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        sensorManager.registerListener(sensorEventListener, pressureSensor,30000000);
    }

}
