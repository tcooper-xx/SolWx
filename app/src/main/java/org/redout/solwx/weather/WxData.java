package org.redout.solwx.weather;

import java.util.List;

public class WxData {
    private List<WxDailyForecast> dailyForcastList;
    private List<WxHourlyForecast> hourlyForecastList;
    private Integer currentTemp;
    private Integer humidity;
    private Integer barometer;
    private String wxIcon;

}
