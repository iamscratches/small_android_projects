package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    EditText etUserName, etPassword;
    String username = "Scratches", password = "123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        String password = b.getString("password");
        etUserName = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUserName.setText(name);
        etPassword.setText(password);
    }

    public void buLogin(View view) {
        if(username.equals(etUserName.getText().toString()) &&
                password.equals(etPassword.getText().toString())){
            // go to second activity

            Intent intent = new Intent(this, testActivity.class);
            intent.putExtra("Key",etUserName.getText().toString());
            startActivity(intent);
        }
        else{
            finish();
        }
    }

    public void closeActiviy(View view) {
        finish();
    }
}