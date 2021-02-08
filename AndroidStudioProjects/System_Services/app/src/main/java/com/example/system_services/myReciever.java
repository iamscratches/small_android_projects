package com.example.system_services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class myReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        if(intent.getAction().equalsIgnoreCase("com.example.Broadcast")){
            String msg = b.getString("msg");
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            buNotifyAgain(msg, context);

        }
        if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")) {
            // sms received
            String message = "";
            String senderNum = "";
            if (b != null) {

                final Object[] pdusObj = (Object[]) b.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                for (int i = 0; i < messages.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = b.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
                    // SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    senderNum = messages[i].getOriginatingAddress();
                    message = messages[i].getMessageBody();//
                }
                Toast.makeText(context, senderNum + " : " + message, Toast.LENGTH_LONG).show();
                SmsManager smsManagersend = SmsManager.getDefault();
                String sendData = "Hey this is scratches!! I will let my master know u texted him.\nThank you";
                smsManagersend.sendTextMessage(senderNum, null, sendData, null, null);
            }
        }
    }

    public void buNotifyAgain(String msg, Context context) {
        long[] n = {1,2,3};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My channel ID", "My Notification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "My channel ID");
        builder.setSmallIcon(R.drawable.ic_baseline_message).setContentTitle("My Title from service")
                .setContentText(msg).setAutoCancel(false).setVibrate(n);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(0, builder.build());
    }
}
