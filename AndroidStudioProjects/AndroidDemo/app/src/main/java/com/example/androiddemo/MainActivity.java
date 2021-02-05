package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etn1, etn2;
    TextView tvResult, intro;
    Button buClick;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"onCreate",Toast.LENGTH_LONG).show();


        etn1 = (EditText)findViewById(R.id.edtn1);
        etn2 = (EditText)findViewById(R.id.edtn2);
        tvResult = (TextView)findViewById(R.id.tvResult);
        intro = (TextView)findViewById(R.id.intro);
        buClick = (Button)findViewById(R.id.buSum);

        buClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((intro.getText().toString()).equals("Button is clicked") || i>0) {
                    intro.setText("Button is clicked " + (i++) + " times");
                }
                else{
                    intro.setText("Button is clicked");
                }
                buClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"onRestart",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show();
    }

    public void buClick() {
        // Will fire when button is clicked
        int n1 = Integer.parseInt(etn1.getText().toString());
        int n2 = Integer.parseInt(etn2.getText().toString());
        int sum = n1 + n2;

        tvResult.setText(String.valueOf(sum));


    }

    public void findAge(View view) {
        EditText dob = (EditText)findViewById(R.id.edtn3);
        TextView tvAge = (TextView)findViewById(R.id.tvShowAge);
        int DOB = Integer.parseInt(dob.getText().toString());
        // get device current time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int age = year - DOB;
        tvAge.setText("Your age is: " + age);
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("name","Scratches");
        intent.putExtra("password","123");
        startActivity(intent);

    }
}