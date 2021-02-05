package com.example.databasework;

import android.content.Context;
import android.content.SharedPreferences;

public class shareRef {
    SharedPreferences SharedRef;
    public shareRef(Context context){
        SharedRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
    }

    public void saveData(String username, String password){
        SharedPreferences.Editor editor = SharedRef.edit();
        editor.putString("Username", username);
        editor.putString("Password", password);
        editor.commit();
    }
    public String[] loadData(){
        String username = SharedRef.getString("Username", "NA");
        String password = SharedRef.getString("Password", "NA");
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        return str;
    }
}
