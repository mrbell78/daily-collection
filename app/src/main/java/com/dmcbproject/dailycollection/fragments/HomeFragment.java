package com.dmcbproject.dailycollection.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.activity.LoginActiviy;
import com.dmcbproject.dailycollection.activity.MainActivity;
import com.dmcbproject.dailycollection.apicall.Api;
import com.dmcbproject.dailycollection.apicall.RetrofitClient;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;
import com.dmcbproject.dailycollection.responsedata.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    Button btn_submit;
    TextView tv_selectdate;
    EditText edt_micollection, edt_depocollection;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener setListener;

    Spinner spinner_brcode;
    ArrayList<String> branchname=new ArrayList<>();
    List<ResponseReportdatum> bodydataarr;
    String onlyBranchcode= "";
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);


        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  branchnamedata = prefs.getString("branchname", "failed");
        onlyBranchcode=prefs.getString("b_onlycode","null");
        branchname.add(branchnamedata);
        dialog= new ProgressDialog(getContext());
        dialog.setContentView(R.layout.progressbar_dialog);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.addContentView(new ProgressBar(getContext()), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        btn_submit=view.findViewById(R.id.btn_submit);
        tv_selectdate=view.findViewById(R.id.select_dateid);
        edt_depocollection=view.findViewById(R.id.edt_depo_collectionid);
        edt_micollection=view.findViewById(R.id.edt_mi_collectionid);

        spinner_brcode = view.findViewById(R.id.branch_codeid);

       // ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(branchname));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spiner_placeholder,branchname);
        spinner_brcode.setAdapter(adapter);


        final int year =  myCalendar.get(Calendar.YEAR);
        final int month =  myCalendar.get(Calendar.MONTH);
        final int day =  myCalendar.get(Calendar.DAY_OF_MONTH);

        tv_selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datedialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,setListener,year,month,day);
                datedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datedialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date =dayOfMonth+"-"+month+"-"+year;
                tv_selectdate.setText(date);
            }
        };


        RetrofitClient retrofitClient = new RetrofitClient();

      //  Retrofit mb = retrofitClient.getRetrofit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/demo_apps_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //bodydataarr=new ArrayList<>();
       // ResponseReportdatum bodydata = new ResponseReportdatum("001",edt_micollection.getText().toString(),edt_depocollection.getText().toString(),tv_selectdate.getText().toString());
        //bodydataarr.add(bodydata);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edt_micollection.getText().toString().isEmpty()){
                    edt_micollection.setError("Mi collection shouldn't be empty");
                }

                if(edt_depocollection.getText().toString().isEmpty()){
                    edt_depocollection.setError("Deposit feild shouldn't be empty");
                }

                if(tv_selectdate.getText().toString().isEmpty()){
                    tv_selectdate.setError("Date feild shouldn't be empty");
                }

                if(!onlyBranchcode.isEmpty()
                        && !edt_depocollection.getText().toString().isEmpty() &&
                        !edt_micollection.getText().toString().isEmpty()  && !tv_selectdate.getText().toString().isEmpty()){



                    dialog.show();


                      Api apicallingjson = retrofit.create(Api.class);
                Call<ResponseBody> callcoment= apicallingjson.postUser(
                        onlyBranchcode,edt_micollection.getText().toString(),edt_depocollection.getText().toString(),tv_selectdate.getText().toString()

                );

                callcoment.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.code()==200){
                            edt_depocollection.setText("");
                            edt_micollection.setText("");
                            tv_selectdate.setText("");
                            Toast.makeText(getContext(), "data insert success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });





                }else{

                    Toast.makeText(getContext(), "please insert value", Toast.LENGTH_SHORT).show();
                }
            }

        });




        // Inflate the layout for this fragment
        return view;
    }

    private void cheklogin() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String name = prefs.getString("login", "failed");
        Log.d(TAG, "onStart: the login status == login_success");
        Log.d(TAG, "onStart: the login status"+name);
        if(name=="login_success"){
            Toast.makeText(getContext(), "you already logedin", Toast.LENGTH_SHORT).show();

        }else{

            Intent intent =new Intent(getActivity(), LoginActiviy.class);
            startActivity(intent);
        }
    }
}