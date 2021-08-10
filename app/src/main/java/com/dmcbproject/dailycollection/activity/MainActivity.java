package com.dmcbproject.dailycollection.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.adapter.SecondDataAdapter;
import com.dmcbproject.dailycollection.apicall.Api;
import com.dmcbproject.dailycollection.fragments.HomeFragment;
import com.dmcbproject.dailycollection.fragments.SecondFragment;
import com.dmcbproject.dailycollection.fragments.ThirdFragment;
import com.dmcbproject.dailycollection.responsedata.ResponseBranchInfo;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;
import com.dmcbproject.dailycollection.responsedata.Root;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {





    MeowBottomNavigation bottomNavigation;
    List<ResponseReportdatum> bodydataarr;
    DataLoadedListener dataLoadedListener;
    Fragment fragment_second;
    Toolbar toolbar;
    String value="";

    String branchdata="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        toolbar=findViewById(R.id.actionbarid);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Daily Collection");
        bottomNavigation=findViewById(R.id.bottom_navid);

         branchdata= getIntent().getStringExtra("branchname");
        Log.d(TAG, "onCreate: the value is "+branchdata);

        setvalue(branchdata,new HomeFragment());

        SharedPreferences prefs =getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  branchnamedata = prefs.getString("branchname", "failed");
       String onlyBranchcode=prefs.getString("b_onlycode","null");

        fragment_second=new SecondFragment();
        dataLoadedListener= (DataLoadedListener) fragment_second;
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_edit_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_home_24));
        if(onlyBranchcode.equals("001")){
            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_search_24));
        }

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
                        if(onlyBranchcode.equals("001")){
                            fragment = new ThirdFragment();

                        }


                }

                        loadfragment(fragment);
            }
        });



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

    private void setvalue(String s,Fragment fragment) {

        Bundle bundle = new Bundle();
        bundle.putString("branchcode", s);
        fragment.setArguments(bundle);
    }


    @Override
    protected void onStart() {
        super.onStart();

           SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  loginstatus = prefs.getString("login", "failed");


        if(!loginstatus.equals("login_success")){

              Intent out =new Intent(MainActivity.this, LoginActiviy.class);
            out.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(out);




        }else {




        }

    }

    private void loadfragment(Fragment fragment) {
        //setvalue(branchdata,fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout,fragment)
                .commit();
    }


    public interface DataLoadedListener {
        public void onDataLoaded(List<ResponseReportdatum> bodydataarr);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return  true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutid:
                logout();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void overFlowitem(MenuItem item){

    }

    private void logout() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/demo_apps_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api apicallingjson = retrofit.create(Api.class);

        Call<ResponseBody> logout= apicallingjson.logout();

        logout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                SharedPreferences.Editor   editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("login", "logout");
                editor.apply();
                Intent login = new Intent(getApplicationContext(),LoginActiviy.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}