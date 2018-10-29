package org.redout.solwx;

import android.content.Context;

import org.redout.solwx.weather.BarometerDataDAO;
import org.redout.solwx.weather.BarometerData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BarometerData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract BarometerDataDAO barometerDataDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE==null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
