package lecture.experiments.screentime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lecture.experiments.roomdatabase.repository.ScreenTimeRepo;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Screen is on, saving in database", Toast.LENGTH_SHORT).show();
        ScreenTimeRepo screenTimeRepo = new ScreenTimeRepo(context);
        String title = "Screen On Event";
        String description = "Time in Miliseconds: "+System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        screenTimeRepo.insertTask(title, description,date,currentTime);
        screenTimeRepo.getTasks();
    }
}
