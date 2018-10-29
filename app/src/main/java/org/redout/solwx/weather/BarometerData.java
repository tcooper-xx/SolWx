package org.redout.solwx.weather;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barometer_data")
public class BarometerData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "barometer")
    private Double barometer;

    @ColumnInfo(name="observed_date")
    private Long observedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBarometer() {
        return barometer;
    }

    public void setBarometer(Double barometer) {
        this.barometer = barometer;
    }

    public Long getObservedDate() {
        return observedDate;
    }

    public void setObservedDate(Long observedDate) {
        this.observedDate = observedDate;
    }
}
