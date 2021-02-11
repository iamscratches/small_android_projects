package com.example.goscratches;


import android.content.Context;
import android.content.SharedPreferences;

public class shareRef {
    SharedPreferences SharedRef;
    public shareRef(Context context){
        SharedRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
        if(SharedRef.contains("Name")==false){
            SharedPreferences.Editor editor = SharedRef.edit();
            editor.putString("Name", "Guest");
            editor.putInt("Age", 21);
            editor.putInt("Cherry", 0);
            editor.putFloat("Distance", 0.0f);
            editor.putInt("SuperCherry", 0);
            editor.commit();
        }
    }

    public void saveData(String name, int age, int cherry, float distance, int supercherry){
        SharedPreferences.Editor editor = SharedRef.edit();
        editor.putString("Name", name);
        editor.putInt("Age", age);
        editor.putInt("Cherry", cherry);
        editor.putFloat("Distance", distance);
        editor.putInt("SuperCherry", supercherry);
        editor.commit();
    }
    public void saveData(String name, int age){
        SharedPreferences.Editor editor = SharedRef.edit();
        editor.putString("Name", name);
        editor.putInt("Age", age);
        editor.commit();
    }
    public StoredData loadData(){
        StoredData sd = new StoredData();
        sd.Name = SharedRef.getString("Name", "Guest");
        sd.Age = SharedRef.getInt("Age", 21);
        sd.Cherry = SharedRef.getInt("Cherry", 0);
        sd.SuperCherry = SharedRef.getInt("SuperCherry", 0);
        sd.Distance = SharedRef.getFloat("Distance", 0);

        return sd;
    }
}
