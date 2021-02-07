package com.example.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class mediaPlayer extends AppCompatActivity {

    boolean isRunning;
    SeekBar sb1;
    TextView tvDisplay;
    Button buStart;
    EditText etTimer;
    myHandler handle;
    int MaxCounter = 1000;//in deci seconds
    int counterUp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        etTimer = (EditText)findViewById(R.id.etTimer);
        handle = new myHandler();

        sb1 = (SeekBar)findViewById(R.id.sb1);
        tvDisplay = (TextView)findViewById(R.id.tvDisplay);
        tvDisplay.setText("Timer : 0s");
        buStart = (Button)findViewById(R.id.buStart);
        buStart.setText("Start");
        sb1.setMax(MaxCounter);
        isRunning = false;

    }

    public void buStop(View view) {
        isRunning = false;
        counterUp = 0;
        sb1.setProgress(counterUp);
        tvDisplay.setText("Time : 0s");
        buStart.setText("Start");
    }

    public void buStart(View view) {
        if(buStart.getText().toString().equalsIgnoreCase("start")){
            MaxCounter = Integer.parseInt(etTimer.getText().toString()) * 10;
            sb1.setMax(MaxCounter);
        }
        isRunning = !isRunning;
        if(isRunning == true) {
            myThread t = new myThread();
            t.start();
            buStart.setText("Pause");
        }
        else{
            buStart.setText("Resume");

        }
    }

    public void nextLayout(View view) {
        Intent intent = new Intent(this, phoneNos.class);
        startActivity(intent);
    }

    class myThread extends Thread{
        @Override
        public void run() {
            while(isRunning){
                if(counterUp <= MaxCounter){

                    Message msg = handle.obtainMessage();
                    Bundle b = new Bundle();
                    b.putInt("counter", counterUp);
                    msg.setData(b);
                    handle.sendMessage(msg);
                    // call UI
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            sb1.setProgress(counterUp);
//                            tvDisplay.setText("Time : " + (counterUp/10.0) + "s");
//                        }
//                    });
                    ++counterUp;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    isRunning = false;
                    counterUp = 0;
                    Message msg = handle.obtainMessage();
                    Bundle b = new Bundle();
                    b.putInt("counter", counterUp);
                    msg.setData(b);
                    handle.sendMessage(msg);
                    //call UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buStart.setText("Start");

                        }
                    });
                }
            }
        }
    }
    class myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            int count = msg.getData().getInt("counter");
            sb1.setProgress(count);
            tvDisplay.setText("Time : " + (count/10.0) + "s");
        }
    }
}