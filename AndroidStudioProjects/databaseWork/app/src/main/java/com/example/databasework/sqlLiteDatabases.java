package com.example.databasework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class sqlLiteDatabases extends AppCompatActivity {

        DBManager db;
    EditText tUsername, tPassword;
    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;
    ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite_databases);
        db = new DBManager(this);
        tUsername = (EditText) findViewById(R.id.tUsername);
        tPassword = (EditText) findViewById(R.id.tPassword);
        lvList = (ListView)findViewById(R.id.lvList);
    }

    public void buLoad(View view) {

        if(tUsername.getText().toString().length() != 0) {
            String projections[] = {db.colPassword};
            String selection = db.colUserName + " like ? ";
            String selectionArgs[] = {tUsername.getText().toString()};
            Cursor cursor = db.query(projections, selection, selectionArgs, null);
            String password = "";
            if (cursor.moveToFirst()) {
                password = cursor.getString(cursor.getColumnIndex(db.colPassword));
                tPassword.setText(password);
            } else
                Toast.makeText(this, "UserName could not be found", Toast.LENGTH_LONG).show();
            listnewsData.clear();
            myadapter=new MyCustomAdapter(listnewsData);
            lvList.setAdapter(myadapter);//initialize with data

        }
        else
            updateListView();

    }
    private void updateListView(){
        Cursor cursor = db.query(null, null, null, db.colUserName);
        listnewsData.clear();
        if(cursor.moveToFirst()){
            String tableData = "";
            do{
//                    tableData += cursor.getString(cursor.getColumnIndex("ID")) + " " +
//                            cursor.getString(cursor.getColumnIndex(db.colUserName)) + " " +
//                            cursor.getString(cursor.getColumnIndex(db.colPassword));
                listnewsData.add(new AdapterItems(
                                Long.parseLong(cursor.getString(cursor.getColumnIndex(db.colID))),
                                cursor.getString(cursor.getColumnIndex(db.colUserName)),
                                cursor.getString(cursor.getColumnIndex(db.colPassword))
                        )
                );
            }while (cursor.moveToNext());
        }
        myadapter=new MyCustomAdapter(listnewsData);
        lvList.setAdapter(myadapter);//initialize with data
    }
    //display news list
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
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
            View myView = mInflater.inflate(R.layout.layout_ticket, null);

            final   AdapterItems s = listnewsDataAdpater.get(position);

            TextView tvID=( TextView)myView.findViewById(R.id.tvID);
            String str = "" + s.ID;
            tvID.setText(str);

            TextView tvTitle=( TextView)myView.findViewById(R.id.tvUserName);
            tvTitle.setText(s.UserName);

            TextView tvDesc=( TextView)myView.findViewById(R.id.tvPassword);
            tvDesc.setText(s.Password);

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), s.UserName, Toast.LENGTH_SHORT).show();
                    String[] selectionArgs = {s.UserName};
                    db.Delete("username like ?", selectionArgs);
                    Toast.makeText(getApplicationContext(), s.UserName + " deleted", Toast.LENGTH_SHORT).show();
                    updateListView();
                }
            });

            return myView;
        }

    }

    public void buSave(View view) {
        ContentValues values = new ContentValues();
        values.put(db.colUserName, tUsername.getText().toString());
        values.put(db.colPassword, tPassword.getText().toString());
        long ID = db.Insert(values);
        if(ID > 0 )
            Toast.makeText(this, "username and password saved with id : " + ID, Toast.LENGTH_LONG).show();
        else{
            String[] str = {tUsername.getText().toString()};
            db.Update(values,  db.colUserName + " = ? ", str);
            Toast.makeText(this, "user password updated", Toast.LENGTH_LONG).show();
        }
        tPassword.setText("");
        tUsername.setText("");
        updateListView();
    }
}