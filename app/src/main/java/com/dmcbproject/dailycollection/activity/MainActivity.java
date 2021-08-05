package com.dmcbproject.dailycollection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.fragments.HomeFragment;
import com.dmcbproject.dailycollection.fragments.SecondFragment;
import com.dmcbproject.dailycollection.fragments.ThirdFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {


    boolean isLogin = false;

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigation=findViewById(R.id.bottom_navid);


        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_notifications_none_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_show_chart_24));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment =new SecondFragment();
                        break;
                    case 2:
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        fragment = new ThirdFragment();

                }

                loadfragment(fragment);
            }
        });

        bottomNavigation.setCount(1,"10");
        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(),"u selected "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(),"u re-selected "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadfragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout,fragment)
                .commit();
    }

}