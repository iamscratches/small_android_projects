package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class showActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
    }

    public void buShow(View view) {

        LayoutInflater myLayoutInflator = getLayoutInflater();
        View view1 = myLayoutInflator.inflate(R.layout.activity_button, null);
        TextView tv = (TextView) view1.findViewById(R.id.tvDisplay);
        tv.setText("HEllo WOrld");
        String str = tv.getText().toString();
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
//        if(tv.getText().toString().length()>0){
            Toast toast = new Toast(getApplicationContext());
            toast.setView(view1);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
//        }
//        else{
//            Log.d("hello", "world");
//            Toast.makeText(this,"No activity to show",Toast.LENGTH_LONG).show();
//        }

    }

    public void nextLayout(View view) {
        Intent intent = new Intent(this, list_Views.class);
        startActivity(intent);
    }
}