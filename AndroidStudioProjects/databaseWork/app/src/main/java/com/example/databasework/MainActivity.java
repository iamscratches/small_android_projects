package com.example.databasework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    shareRef sharedRef;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedRef = new shareRef(this);
        username = (EditText)findViewById(R.id.tusername);
        password = (EditText)findViewById(R.id.tpassword);
    }

    public void buLoad(View view) {
        String[] str = sharedRef.loadData();
        username.setText(str[0]);
        password.setText(str[1]);
    }

    public void buSave(View view) {
        sharedRef.saveData(username.getText().toString(), password.getText().toString());
        Toast.makeText(this, "Content is saved successfully", Toast.LENGTH_LONG).show();
    }

    public void nextDatabase(View view) {
        Intent intent = new Intent(this, sqlLiteDatabases.class);
        startActivity(intent);
    }
}