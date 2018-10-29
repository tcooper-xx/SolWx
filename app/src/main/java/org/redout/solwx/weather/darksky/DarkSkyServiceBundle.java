package org.redout.solwx.weather.darksky;

public class DarkSkyServiceBundle {
    private Double lat;
    private Double lon;
    private String apiKey;
    private GetDarkSkyDataService service;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public GetDarkSkyDataService getService() {
        return service;
    }

    public void setService(GetDarkSkyDataService service) {
        this.service = service;
    }
}
