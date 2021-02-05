package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class spinner_layout extends AppCompatActivity {
    List<String> arr;
    PopTime popTime;
    CalendarView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_layout);
        Spinner sp1 = (Spinner)findViewById(R.id.sp1);
        TextView tv = (TextView)findViewById(R.id.tvDate);

        popTime=new PopTime();
        arr = new ArrayList<String>();
        arr.add("Admin");
        arr.add("Manager");
        arr.add("Tester");
        arr.add("Developer");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arr);
        sp1.setAdapter(arrayAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), arr.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setDate(String date){
        TextView tv = (TextView)findViewById(R.id.tvDate);
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
        tv.setText(date);
    }

    public void buShow(View view) {
        popTime.show(getSupportFragmentManager(),"Show fragment");
    }


    public void notify(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("ARE you SURE you WANT to PROCEED further").setTitle("CoNfIrMaTiOn")
                .setPositiveButton("yEs", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "is delete", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("nO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "is canceled", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }
}