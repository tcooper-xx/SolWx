package org.redout.solwx.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.solwx.DailyForecastAdapter;
import org.redout.solwx.HourlyForecastAdapter;
import org.redout.solwx.R;
import org.redout.solwx.weather.darksky.DarkSkyRetrofitInstance;
import org.redout.solwx.weather.darksky.DarkSkyServiceBundle;
import org.redout.solwx.weather.darksky.GetDarkSkyDataService;
import org.redout.solwx.weather.darksky.generated.WeatherData;

import java.io.IOException;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

public class GetWeatherTask extends AsyncTask<DarkSkyServiceBundle, Void, WeatherData> {
    Activity mActivity;


    public GetWeatherTask(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected WeatherData doInBackground(DarkSkyServiceBundle... darkSkyServiceBundles) {
        System.out.println("getting weather data");
        WeatherData weatherData = null;

        for (DarkSkyServiceBundle bundle : darkSkyServiceBundles) {
            Call<WeatherData> call = bundle.getService().getForecast(Double.toString(bundle.getLat()), Double.toString(bundle.getLon()), bundle.getApiKey());
            Response<WeatherData> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                Log.e("Error getting DarkSky data: ", e.getMessage());
            }
            if(response!=null) {
                weatherData = response.body();
            }
        }
        return weatherData;
    }

    protected void onPostExecute(WeatherData weatherData) {
        DailyForecastAdapter dailyForecastAdapter;
        RecyclerView dailyForecastRecyclerView;
        HourlyForecastAdapter hourlyForecastAdapter;
        RecyclerView hourlyForecastRecyclerView;

        System.out.println("updating UI");
        TextView currentTemp = mActivity.findViewById(R.id.currentTemp);
        currentTemp.setText(Math.round(weatherData.getCurrently().getTemperature()) + "°");
        String iconName = weatherData.getCurrently().getIcon().replace('-', '_');
        ImageView currentWxIcon  = mActivity.findViewById(R.id.currentWxIcon);
        int iconId = currentWxIcon.getContext().getResources().getIdentifier(iconName, "drawable", currentWxIcon.getContext().getPackageName() );
        currentWxIcon.setImageResource(iconId);

        dailyForecastAdapter = new DailyForecastAdapter(weatherData.getDaily().getData());
        RecyclerView.LayoutManager dailyForecastLayoutManager = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        dailyForecastRecyclerView = mActivity.findViewById(R.id.dailyForecastRecycler);
        dailyForecastRecyclerView.setLayoutManager(dailyForecastLayoutManager);
        dailyForecastRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);

        hourlyForecastAdapter = new HourlyForecastAdapter(weatherData.getHourly().getData());
        RecyclerView.LayoutManager hourlyForecastLayoutManager = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        hourlyForecastRecyclerView = mActivity.findViewById(R.id.hourlyForecastRecycler);
        hourlyForecastRecyclerView.setLayoutManager(hourlyForecastLayoutManager);
        hourlyForecastRecyclerView.setItemAnimator(new DefaultItemAnimator());
        hourlyForecastRecyclerView.setAdapter(hourlyForecastAdapter);
    }
}
