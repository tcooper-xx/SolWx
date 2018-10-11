package org.redout.solwx;

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

import org.redout.solwx.weather.darksky.DarkSkyRetrofitInstance;
import org.redout.solwx.weather.darksky.GetDarkSkyDataService;
import org.redout.solwx.weather.darksky.generated.WeatherData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DailyForecastAdapter dailyForecastAdapter;
    private RecyclerView dailyForecastRecyclerView;
    private HourlyForecastAdapter hourlyForecastAdapter;
    private RecyclerView hourlyForecastRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultInit();
        setupTabs();
        getDarkSkyData(41.216932,-96.1678416,"7d6f4a8df38879f75828056048610703");



    }

    public void getDarkSkyData(double lat, double lon, String apikey) {
        GetDarkSkyDataService service = DarkSkyRetrofitInstance.getDarkSkyRetofitInstance().create(GetDarkSkyDataService.class);
        Call<WeatherData> call = service.getForecast(Double.toString(lat), Double.toString(lon), apikey);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                System.out.println("URL :" +call.request().url());
                WeatherData weatherData = response.body();
                TextView currentTemp = findViewById(R.id.currentTemp);
                currentTemp.setText(response.body().getCurrently().getTemperature().toString());

                dailyForecastAdapter = new DailyForecastAdapter(weatherData.getDaily().getData());
                RecyclerView.LayoutManager dailyForecastLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                dailyForecastRecyclerView = findViewById(R.id.dailyForecastRecycler);
                dailyForecastRecyclerView.setLayoutManager(dailyForecastLayoutManager);
                dailyForecastRecyclerView.setItemAnimator(new DefaultItemAnimator());
                dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);

                hourlyForecastAdapter = new HourlyForecastAdapter(weatherData.getHourly().getData());
                RecyclerView.LayoutManager hourlyForecastLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                hourlyForecastRecyclerView = findViewById(R.id.hourlyForecastRecycler);
                hourlyForecastRecyclerView.setLayoutManager(hourlyForecastLayoutManager);
                hourlyForecastRecyclerView.setItemAnimator(new DefaultItemAnimator());
                hourlyForecastRecyclerView.setAdapter(hourlyForecastAdapter);


            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("Error getting Current Conditions : ", t.getMessage());
            }
        });

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
}
