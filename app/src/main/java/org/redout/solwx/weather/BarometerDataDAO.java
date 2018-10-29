package org.redout.solwx.weather;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BarometerDataDAO {

    @Query("SELECT * FROM barometer_data")
    List<BarometerData> getAll();

    @Insert
    void insertAll(BarometerData... barometerData);

    @Delete
    void delete(BarometerData barometerData);

    @Query("SELECT * FROM barometer_data WHERE observed_date > :observedDate")
    List<BarometerData> selectSinceDate(Long observedDate);


}
