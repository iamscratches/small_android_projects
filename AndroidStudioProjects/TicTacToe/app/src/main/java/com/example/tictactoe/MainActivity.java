package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buClick(View view) {
        Button buSelected = (Button) view;

        int cellID = 0;
        switch (buSelected.getId()){
            case R.id.bu1:
                cellID = 1;
                break;
            case R.id.bu2:
                cellID = 2;
                break;
            case R.id.bu3:
                cellID = 3;
                break;
            case R.id.bu4:
                cellID = 4;
                break;
            case R.id.bu5:
                cellID = 5;
                break;
            case R.id.bu6:
                cellID = 6;
                break;
            case R.id.bu7:
                cellID = 7;
                break;
            case R.id.bu8:
                cellID = 8;
                break;
            case R.id.bu9:
                cellID = 9;
                break;
        }
        playGame(cellID, buSelected);

    }
    int activePlayer = 1;    //1->first 2->second
    ArrayList<Integer> Player1 = new ArrayList<Integer>();  //hold player 1 data
    ArrayList<Integer> Player2 = new ArrayList<Integer>();  //hold player 2 data

    void playGame(int cellID, Button buSelected){

        Log.d("Player:", String.valueOf(cellID));
        if(activePlayer == 1){
            buSelected.setText("X");
            buSelected.setTextSize(30.0f);
            buSelected.setBackgroundColor(Color.RED);
            Player1.add(cellID);
            activePlayer = 2;
        }
        else{
            buSelected.setText("O");
            buSelected.setTextSize(30.0f);
            buSelected.setBackgroundColor(Color.GREEN);
            Player2.add(cellID);
            activePlayer = 1;
        }
        buSelected.setClickable(false);
        Toast.makeText(this,"selected", Toast.LENGTH_SHORT).show();
    }
}