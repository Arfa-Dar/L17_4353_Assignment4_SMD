package lecture.experiments.roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ScreenTime implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;
    private String time;

    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time=time;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
