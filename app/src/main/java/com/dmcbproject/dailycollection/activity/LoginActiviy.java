package com.dmcbproject.dailycollection.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dmcbproject.dailycollection.R;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActiviy extends AppCompatActivity {

    Spinner spinner_brcode;
    String [] branchname={"Select Branch","postogola branch","satkhira branch",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiy);

        spinner_brcode = findViewById(R.id.brcode_spiner);

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(branchname));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arrayList);
        spinner_brcode.setAdapter(adapter);

    }
}