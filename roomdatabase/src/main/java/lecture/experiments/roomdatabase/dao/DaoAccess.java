package lecture.experiments.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lecture.experiments.roomdatabase.ScreenTime;
import lecture.experiments.roomdatabase.location_tracking;
import lecture.experiments.roomdatabase.places_visits;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(ScreenTime screenTime);

    @Insert
    Long insertLocation(location_tracking location);

    @Insert
    Long insertPlaces(places_visits places_visits);

    @Query("SELECT * FROM location_tracking")
    List<location_tracking> fetchAllLoc();


    @Query("SELECT * FROM location_tracking WHERE id =:taskId")
    location_tracking getLoc(int taskId);

    @Query("SELECT * FROM ScreenTime")
    List<ScreenTime> fetchAllTasks();


    @Query("SELECT * FROM ScreenTime WHERE id =:taskId")
    ScreenTime getTask(int taskId);

    @Update
    void updateTask(ScreenTime screenTime);


    @Delete
    void deleteTask(ScreenTime screenTime);
}