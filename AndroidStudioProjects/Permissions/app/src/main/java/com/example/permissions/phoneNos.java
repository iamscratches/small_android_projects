package com.example.permissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class phoneNos extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    MyCustomAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_nos);
        CheckUserPermissions();
    };

    ArrayList<ContactItems> listContact = new ArrayList<ContactItems>();
    void readContact(){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            listContact.add(new ContactItems(name, phoneNumber));

        }
        ContactItems ci;
        for(int i=0; i<listContact.size()-1; i++){
            for(int j=i+1; j<listContact.size(); j++){
                if(listContact.get(i).name.compareTo(listContact.get(i+1).name)>0){
                    ci = listContact.get(i);
                    listContact.set(i,listContact.get(j));
                    listContact.set(j, ci);
                }
            }
        }
        for(int i=0; i<listContact.size()-1; i++){
            if(listContact.get(i).name.equalsIgnoreCase(listContact.get(i+1).name) && listContact.get(i).phoneNumber.equalsIgnoreCase(listContact.get(i+1).phoneNumber)){
                listContact.remove(i + 1);
                --i;
            }
        }

        myAdapter = new MyCustomAdapter(listContact);
        ListView lsNews = (ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myAdapter);
        TextView tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(listContact.size()));
    }
    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        readContact();// init the contact list

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContact();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(this, "you denied contact access", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<ContactItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<ContactItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.list_item, null);

            final ContactItems s = listnewsDataAdpater.get(position);

            TextView tvName=( TextView)myView.findViewById(R.id.tvName);
            String str = "" + s.name;
            tvName.setText(str);

            TextView tvNumber=( TextView)myView.findViewById(R.id.tvNumber);
            tvNumber.setText(s.phoneNumber);

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), s.name, Toast.LENGTH_SHORT).show();
                }
            });

            return myView;
        }

    }
}