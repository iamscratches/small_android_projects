package com.example.system_services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.Random;

public class LocalService extends Service {

    public static boolean ServiceIsRunning = false;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}


//public class LocalService extends Service {
//
//    public static boolean ServiceIsRunning = false;
//    int fb;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        fb=10;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // background <code> //listen to firebase </code>
//        // fb.onClickListener
//
//
//
//        return START_NOT_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
