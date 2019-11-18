package lecture.experiments.roomdatabase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import lecture.experiments.roomdatabase.ScreenTime;
import lecture.experiments.roomdatabase.dao.DaoAccess;
import lecture.experiments.roomdatabase.location_tracking;
import lecture.experiments.roomdatabase.places_visits;

@Database(entities = {ScreenTime.class, location_tracking.class, places_visits.class}, version = 2, exportSchema = false)


public abstract class DatabaseClass extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
