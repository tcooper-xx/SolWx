package org.redout.solwx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.redout.solwx.weather.darksky.generated.HourlyData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.MyViewHolder> {
    List <HourlyData> hourlyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fcstHigh, fcstDay;
        public ImageView fcstIcon;

        public MyViewHolder(View view) {
            super(view);

            fcstDay = view.findViewById(R.id.fcstDay);
            fcstHigh = view.findViewById(R.id.fcstHigh);
            fcstIcon = view.findViewById(R.id.fcstIcon);
        }

    }

    public HourlyForecastAdapter(List<HourlyData> hourlyList) {
        this.hourlyList = hourlyList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourlyforecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HourlyData forecastListItem = hourlyList.get(position);
        Date fcDate = new Date(forecastListItem.getTime()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE ha");
        holder.fcstDay.setText(sdf.format(fcDate).toUpperCase());
        holder.fcstHigh.setText(Math.round(forecastListItem.getTemperature()) + "°");
        String iconName = forecastListItem.getIcon().replace('-', '_');
        int iconId = holder.fcstIcon.getContext().getResources().getIdentifier(iconName, "drawable", holder.fcstIcon.getContext().getPackageName() );
        holder.fcstIcon.setImageResource(iconId);
    }

    public int getItemCount() {
        return hourlyList.size();
    }

}
