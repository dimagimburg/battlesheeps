package com.example.dima.battlesheeps.UI.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Chronometer;

public class OrientationService extends Service implements SensorEventListener{

    private final String TAG = "OrientationService";

    private IBinder mBinder = new OrientationServiceBinder();
    private Chronometer mChronometer;
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private float currentOrientation;
    long timeDiff = 0;
    LocalBroadcastManager broadcaster;


    @Override
    public void onCreate() {
        super.onCreate();
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "in onBind");
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "in onRebind");
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "in onUnbind");
        //unregisterListeners();
        mSensorManager.unregisterListener(this);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "in onDestroy");
        mChronometer.stop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        if(elapsedMillis - timeDiff > 200){
            timeDiff = elapsedMillis;
            currentOrientation = event.values[1];
            sendOrientation(currentOrientation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class OrientationServiceBinder extends Binder {
        public OrientationService getService() {
            return OrientationService.this;
        }
    }

    public void sendOrientation(float orientation) {
        Intent intent = new Intent("orientation");
        intent.putExtra("orientation", orientation);
        broadcaster.sendBroadcast(intent);
    }
}