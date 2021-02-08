package com.example.system_services;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID ="MyChannel1" ;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    int notID = 0;
    Intent IntentService;
    Intent IntentServiceNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckUserPermissions();

        if(!myService.IsRunning){
            myService.IsRunning = true;
            IntentService = new Intent(this, myService.class);
            startService(IntentService);
        }
        if(!LocalService.ServiceIsRunning){
            LocalService.ServiceIsRunning = true;
            IntentServiceNotification = new Intent(this, LocalService.class);
            startService(IntentServiceNotification);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.example.Broadcast");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
    // Create Broadcast Receiver Class
    private BroadcastReceiver receiver = new myReciever();


    public void buSendBroadcast(View view) {
        Intent intent = new Intent("com.example.Broadcast");
        intent.putExtra("msg", "Hello from activity");
        sendBroadcast(intent);
        Toast.makeText(this, "msg send", Toast.LENGTH_LONG).show();

        //stop service
        if(!myService.IsRunning){
            myService.IsRunning = false;
            stopService(IntentService);
        }
    }
    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                            PackageManager.PERMISSION_GRANTED&&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
                                Manifest.permission.SEND_SMS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // Permission Denied
                    Toast.makeText(this, "you denied contact access", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public int id = 0;
    public void buNotify(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My channel ID", "My Nptification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        String message = "This is a notification example";
        long[] n = {1,2,3};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My channel ID")
                .setSmallIcon(R.drawable.ic_baseline_message)
                .setContentTitle("New notification")
                .setContentText(message + id)
                .setAutoCancel(false)
                .setVibrate(n);

        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id,builder.build());
        id++;
    }

    public void buNotifyAgain(View view) {
        long[] n = {1,2,3};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My channel ID", "My Nptification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My channel ID");
        builder.setSmallIcon(R.drawable.ic_baseline_message).setContentTitle("My Title")
                .setContentText("Hello from Scratches").setAutoCancel(false).setVibrate(n);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(id, builder.build());
        id++;
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}