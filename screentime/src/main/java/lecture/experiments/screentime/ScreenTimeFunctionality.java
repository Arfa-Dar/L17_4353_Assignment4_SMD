package lecture.experiments.screentime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import lecture.experiments.screentime.receiver.ScreenReceiver;

public class ScreenTimeFunctionality extends Activity {
    ScreenReceiver screenReceiver;
    IntentFilter intentFilter;

    public ScreenTimeFunctionality(){
        intentFilter    = new IntentFilter();
        screenReceiver  = new ScreenReceiver();
    }

    public void registerEvents(Context context){
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(screenReceiver, intentFilter);
    }

    public void unregisterEvents(Context context){
        try {
            context.unregisterReceiver(screenReceiver);
        }catch (Exception e){
            Log.e("ScreenTimeFunctionality", "Some Exception: "+e.getMessage());
        }
    }

}
