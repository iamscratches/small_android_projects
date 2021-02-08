package com.example.myalarm;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.app.DialogFragment;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

public class PopTime extends DialogFragment implements View.OnClickListener {

    View view;
    TimePicker tp;
    Button buDone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.activity_pop_time,container,false);
        tp=(TimePicker)view.findViewById(R.id.tp);
        buDone=(Button)view.findViewById(R.id.buDone);
        buDone.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
        MainActivity ma=(MainActivity)getActivity();
        if ((int) Build.VERSION.SDK_INT >= 23)
            ma.SetTime(tp.getHour(),tp.getMinute());
        else
            ma.SetTime(tp.getCurrentHour(),tp.getCurrentMinute());
    }
}