package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class buttonActivity extends AppCompatActivity {

    CheckBox cbStatus;
    RadioButton rbMale, rbFemale, rbOthers;
    Switch swGrad;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        cbStatus = (CheckBox)findViewById(R.id.cbStatus);
        rbMale = (RadioButton)findViewById(R.id.rbMale);
        rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        rbOthers = (RadioButton)findViewById(R.id.rbOthers);
        swGrad = (Switch)findViewById(R.id.swGrad);
        if(swGrad.isChecked())
            swGrad.setText("Yes");
        else
            swGrad.setText("No");
        tvResult = (TextView)findViewById(R.id.tvDisplay);
    }

    public void buCheck(View view) {
        String Result = "";
        if(cbStatus.isChecked())
            Result = "He is married";
        else
            Result = "He is single";

        if(rbMale.isChecked())
            Result += "\nHe is male";
        else if(rbFemale.isChecked())
            Result += "\nShe is female";
        else
            Result += "\nHe/She is trans";

        if (swGrad.isChecked())
            Result += "\nHe is a graduate";
        else
            Result += "\nHe is an undergrad";

        tvResult.setText(Result);
    }

    public void checkGrad(View view) {
        if(swGrad.isChecked())
            swGrad.setText("Yes");
        else
            swGrad.setText("No");

    }

    public void nextLayout(View view) {
//        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, showActivity.class);
        startActivity(intent);
    }
}