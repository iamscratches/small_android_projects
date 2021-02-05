package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class grid_views extends AppCompatActivity {

    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    grid_views.MyCustomAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_views);
        GridView lvList = (GridView) findViewById(R.id.lvList);

        listnewsData.add(new AdapterItems(1, "developer", " develop apps"));
        listnewsData.add(new AdapterItems(2, "tester", " develop apps"));
        listnewsData.add(new AdapterItems(3, "admin", " develop apps"));

        myadapter = new grid_views.MyCustomAdapter(listnewsData);

        lvList.setAdapter(myadapter);//initialize with data

        listnewsData.add(new AdapterItems(4, "developer", " test apps"));
        listnewsData.add(new AdapterItems(5, "tester", " test apps"));
        listnewsData.add(new AdapterItems(6, "admin", " test apps"));
        myadapter.notifyDataSetChanged();
        listnewsData.add(new AdapterItems(7, "developer", " develop apps"));
        listnewsData.add(new AdapterItems(8, "tester", " develop apps"));
        listnewsData.add(new AdapterItems(9, "admin", " develop apps"));
        listnewsData.add(new AdapterItems(10, "developer", " test apps"));
        listnewsData.add(new AdapterItems(11, "tester", " test apps"));
        listnewsData.add(new AdapterItems(12, "admin", " test apps"));
        listnewsData.add(new AdapterItems(13, "developer", " develop apps"));
        listnewsData.add(new AdapterItems(14, "tester", " develop apps"));
        listnewsData.add(new AdapterItems(15, "admin", " develop apps"));
        listnewsData.add(new AdapterItems(16, "developer", " test apps"));
        listnewsData.add(new AdapterItems(17, "tester", " test apps"));
        listnewsData.add(new AdapterItems(18, "admin", " test apps"));
        myadapter.notifyDataSetChanged();


    }

    public void nextLayout(View view) {
        Intent intent = new Intent(this, spinner_layout.class);
        startActivity(intent);
    }

    //display news list
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater;

        public MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final AdapterItems s = listnewsDataAdpater.get(position);

            TextView tvID = (TextView) myView.findViewById(R.id.tvID);
            String str = "" + s.ID;
            tvID.setText(str);

            TextView tvTitle = (TextView) myView.findViewById(R.id.tvTitle);
            tvTitle.setText(s.JobTitle);

            TextView tvDesc = (TextView) myView.findViewById(R.id.tvDesc);
            tvDesc.setText(s.Description);

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), s.JobTitle, Toast.LENGTH_SHORT).show();
                }
            });

            return myView;
        }
    }
}