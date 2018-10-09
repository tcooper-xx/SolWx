package org.redout.solwx.weather.darksky;

import org.redout.solwx.weather.darksky.generated.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDarkSkyDataService {
    @GET("forecast/{apikey}/{lat},{lon}")
    Call<WeatherData> getForecast(@Path("lat") String lat, @Path("lon") String lon, @Path("apikey") String apikey);
}
