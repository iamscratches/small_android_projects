package com.example.calculator;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopTime extends DialogFragment implements View.OnClickListener{
    View view;
    CalendarView dp;
    Button buDone;
    spinner_layout sl;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.pop_time, container, false);
        dp = (CalendarView)view.findViewById(R.id.calendarView);
        sl = (spinner_layout)getActivity();

        dp.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth){
                String dom, m;
                m = (month + 1)>9?(1+month) + "" : "0" + (month+1);
                dom = (dayOfMonth>9)? dayOfMonth + "" : "0" + dayOfMonth;
                String Date = dom + "-" + m + "-" + year;
                assert sl != null;
                sl.setDate(Date);
            }
        });
        buDone = (Button)view.findViewById(R.id.button6);
        buDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getDialog().dismiss();
    }

}
