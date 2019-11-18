package com.example.location_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lecture.experiments.roomdatabase.database.DatabaseClass;
import lecture.experiments.roomdatabase.location_tracking;
import lecture.experiments.roomdatabase.repository.ScreenTimeRepo;

public class Location_Main extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = Location_Main.class.getSimpleName();
    GoogleApiClient mLocationClient;
    FusedLocationProviderClient mFusedClient;
    @SuppressLint("RestrictedApi")
    LocationRequest mLocationRequest = new LocationRequest();
    PendingIntent pendingIntent;
    LocationCallback mLocationCallback;

    public static final String ACTION_LOCATION_BROADCAST = Location_Main.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String EXTRA_ACCURACY= "extra_accuracy";
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000;

    @Override
    public void onCreate() {
        super.onCreate();

        mLocationCallback= new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                Log.d(TAG, "in onlocation method");
                Location location= locationResult.getLastLocation();
                Log.d(TAG, "Location changed"+location.getLatitude());
                ScreenTimeRepo screenTimeRepo= new ScreenTimeRepo(getApplicationContext());
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                screenTimeRepo.insertLoc(location.getLatitude(),location.getLongitude(),location.getAccuracy(),date,currentTime);

            }
        };
    }

    @Override
    public void onDestroy()
    {
        StopUpdates();
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFusedClient= LocationServices.getFusedLocationProviderClient(this);
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)

                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setSmallestDisplacement(150f);
        Log.d(TAG, "Connected to Google API");

        mLocationClient.connect();


        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     *
     *
     */

    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }

        Log.d(TAG, "inonconnected");
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,mLocationCallback,null);
        Log.d(TAG, "Connected to Google API");
    }
    public void StopUpdates()
    {
        if (mLocationClient.isConnected()) {
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(mLocationCallback);
        }
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    //to get the location change


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void sendMessageToUI(String lat, String lng, String acc) {

        Log.d(TAG, "Sending info...");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");

    }
}