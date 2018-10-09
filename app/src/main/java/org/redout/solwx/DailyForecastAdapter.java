package org.redout.solwx;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.solwx.weather.darksky.generated.DailyData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.MyViewHolder> {
    List<DailyData> forecastList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fcstHigh, fcstLow, fcstDay;
        public ImageView fcstIcon;

        public MyViewHolder(View view) {
            super(view);

            fcstDay = view.findViewById(R.id.fcstDay);
            fcstHigh = view.findViewById(R.id.fcstHigh);
            fcstLow = view.findViewById(R.id.fcstLow);
            fcstIcon = view.findViewById(R.id.fcstIcon);
        }

    }

    public DailyForecastAdapter( List<DailyData> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DailyData forecastListItem = forecastList.get(position);
        Date fcDate = new Date(forecastListItem.getTime()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        holder.fcstDay.setText(sdf.format(fcDate).toUpperCase());
        holder.fcstHigh.setText(Math.round(forecastListItem.getTemperatureHigh()) + "°");
        holder.fcstLow.setText(Math.round(forecastListItem.getTemperatureLow()) + "°");
        String iconName = forecastListItem.getIcon().replace('-', '_');
        int iconId = holder.fcstIcon.getContext().getResources().getIdentifier(iconName, "drawable", holder.fcstIcon.getContext().getPackageName() );
        holder.fcstIcon.setImageResource(iconId);

    }

    @Override
    public int getItemCount() {return forecastList.size();}
}
