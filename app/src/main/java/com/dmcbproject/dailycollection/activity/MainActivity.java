package com.dmcbproject.dailycollection.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dmcbproject.dailycollection.R;

public class MainActivity extends AppCompatActivity {


    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Dailycollection);
        setContentView(R.layout.activity_main);





    }

    @Override
    protected void onStart() {

        if(isLogin==false){
            Intent i = new Intent(getApplicationContext(),LoginActiviy.class);
            startActivity(i);
        }
        super.onStart();
    }
}