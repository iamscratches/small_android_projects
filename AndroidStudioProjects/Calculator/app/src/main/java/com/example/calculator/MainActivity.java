package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    EditText etn1, etn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = (TextView)findViewById(R.id.tvResult);
        etn1 = (EditText)findViewById(R.id.etn1);
        etn2 = (EditText)findViewById(R.id.etn2);
    }

    public void calculate(View view) {
        double n1 = Double.parseDouble(etn1.getText().toString());
        double n2 = Double.parseDouble(etn2.getText().toString());
        double r = n1 + n2;
        txtResult.setText("sum = " + r + "\n" + r);
        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
    }

    public void nextLayout(View view) {
        Intent intent = new Intent(this, buttonActivity.class);
        startActivity(intent);
        finish();
    }
}