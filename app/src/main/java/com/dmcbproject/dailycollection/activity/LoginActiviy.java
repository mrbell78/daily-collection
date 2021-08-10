package com.dmcbproject.dailycollection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.apicall.Api;
import com.dmcbproject.dailycollection.fragments.HomeFragment;
import com.dmcbproject.dailycollection.responsedata.ResponseBranchInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class LoginActiviy extends AppCompatActivity {


    Spinner spinner_brcode;
    String [] branchname={"Select Branch","postogola branch","satkhira branch",};
    Button btn_login;
    List<ResponseBranchInfo> branchinfo=new ArrayList<>();

    EditText edt_email,edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiy);



        edt_email=findViewById(R.id.inputEmail);
        edt_password=findViewById(R.id.inputPassword);

        spinner_brcode = findViewById(R.id.brcode_spiner);

               Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/demo_apps_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api apicallingjson = retrofit.create(Api.class);

        Call<List<ResponseBranchInfo>> branchinfo= apicallingjson.getBranchdata();

        branchinfo.enqueue(new Callback<List<ResponseBranchInfo>>() {
            @Override
            public void onResponse(Call<List<ResponseBranchInfo>> call, Response<List<ResponseBranchInfo>> response) {

                Log.d(TAG, "onResponse: the value is "+ response.code());
                List<ResponseBranchInfo> data = new ArrayList<>();
                data = response.body();
                ArrayList branchname=new ArrayList() ;
                for(ResponseBranchInfo singledata : data){
                    branchname.add(singledata.getBranchName());
                }
                //ArrayList<ResponseBranchInfo> arrayList = new ArrayList<ResponseBranchInfo>(Arrays.asList(arrayList));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActiviy.this, R.layout.spiner_placeholder,branchname);
                spinner_brcode.setAdapter(adapter);

                Api apicallingjson = retrofit.create(Api.class);

                List<ResponseBranchInfo> finalData = data;
                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edt_email.getText().toString().isEmpty()){
                            edt_email.setError("this field can't be empty");
                        }

                        if(edt_password.getText().toString().isEmpty()){
                            edt_password.setError("this field can't be empty");
                        }

                        if(!edt_password.getText().toString().isEmpty() && !edt_email.getText().toString().isEmpty() && spinner_brcode.getSelectedItem()!=null){


                            Log.d(TAG, "onClick: spinervalue "+spinner_brcode.getSelectedItem());
                            Log.d(TAG, "onClick: branchcode "+ finalData.get(spinner_brcode.getSelectedItemPosition()).getBranchCode());
                            Log.d(TAG, "onClick: email "+edt_email.getText().toString());
                            Log.d(TAG, "onClick: pass "+edt_password.getText().toString());

                            Call<ResponseBody> login = apicallingjson.loginUser(finalData.get(spinner_brcode.getSelectedItemPosition()).getBranchCode().toString(),edt_email.getText().toString(),edt_password.getText().toString());
                            login.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    if(response.code()==200){
                                        Toast.makeText(LoginActiviy.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                                        editor.putString("login", "login_success");

                                        String spinervalue = spinner_brcode.getSelectedItem().toString()+"   "+finalData.get(spinner_brcode.getSelectedItemPosition()).getBranchCode();
                                        Log.d(TAG, "onResponse: combine value is "+spinervalue);

                                        editor.putString("branchname",spinervalue);
                                        editor.putString("b_onlycode",finalData.get(spinner_brcode.getSelectedItemPosition()).getBranchCode());
                                        editor.apply();



                                        Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.putExtra("branchname",spinervalue);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        Toast.makeText(LoginActiviy.this, "MainActiviy is called", Toast.LENGTH_SHORT).show();
                                    }else{

                                        Toast.makeText(LoginActiviy.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                                        edt_email.setError("error");
                                        edt_password.setError("error");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<ResponseBranchInfo>> call, Throwable t) {

            }
        });




        btn_login = findViewById(R.id.btnLogin);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


           SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  loginstatus = prefs.getString("login", "failed");

        if(loginstatus=="login_success"){
            Toast.makeText(getApplicationContext(), "you already logedin", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(this, "Login activity is called", Toast.LENGTH_SHORT).show();


        }else {

            Log.d(TAG, "onStart: you r not loged in");

        }
    }
}