package com.example.system_services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class myService extends IntentService {

    public static boolean IsRunning = false;
    public myService(){
        super("myService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //background code running
        while(IsRunning){
            Intent BroadcastIntent = new Intent();
            BroadcastIntent.setAction("com.example.Broadcast");
            BroadcastIntent.putExtra("msg", "hello from service");
            sendBroadcast(BroadcastIntent);
            try{
                Thread.sleep(8000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}