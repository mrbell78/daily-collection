package com.dmcbproject.dailycollection.fragments;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.adapter.SecondDataAdapter;
import com.dmcbproject.dailycollection.adapter.ThirdDataAdapter;
import com.dmcbproject.dailycollection.apicall.Api;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class ThirdFragment extends Fragment {


    DatePickerDialog.OnDateSetListener setListener;
    String datefinal="";
    TextView textView,tv_date_thfrgmnt;
    RecyclerView recyclerView_search;
    public ThirdFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third,container,false);

        recyclerView_search=view.findViewById(R.id.recylerview_serchid);
        recyclerView_search.setHasFixedSize(true);
        recyclerView_search.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_date_thfrgmnt=view.findViewById(R.id.dateid_thfrmntid);
        tv_date_thfrgmnt.setVisibility(View.GONE);

        //Log.d(TAG, "onOptionsItemSelected: the date is in textview "+datefinal.toString());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchid);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
       textView = (TextView) menuItem.getActionView();

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                datefinal =dayOfMonth+"-"+month+"-"+year;
                textView.setTextSize(18);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textView.setText(datefinal);
                String prams = textView.getText().toString();
                String baseurl = "https://dmcbproject.com/demo_apps_api/api/search/"+prams+"/";
                Log.d(TAG, "onDateSet: url==="+baseurl);
                Log.d(TAG, "onDateSet: the data-------------------------"+prams);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseurl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api apicallingjson = retrofit.create(Api.class);
                Call<List<ResponseReportdatum>> searchapi= apicallingjson.getSearchdata();
                searchapi.enqueue(new Callback<List<ResponseReportdatum>>() {
                    @Override
                    public void onResponse(Call<List<ResponseReportdatum>> call, Response<List<ResponseReportdatum>> response) {
                        if(response.code()==200){

                            List<ResponseReportdatum> data = response.body();

                            if(!data.get(0).getPostDate().toString().isEmpty()){
                                tv_date_thfrgmnt.setVisibility(View.VISIBLE);
                                tv_date_thfrgmnt.setText("Date : "+data.get(0).getPostDate().toString());
                            }else{
                                tv_date_thfrgmnt.setVisibility(View.GONE);
                            }

                            ThirdDataAdapter adapter_search= new ThirdDataAdapter(data,getActivity(),"searchadapter");
                            recyclerView_search.setAdapter(adapter_search);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseReportdatum>> call, Throwable t) {

                    }
                });

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectdate();
                        if(!textView.getText().toString().isEmpty()){
                            String prams = textView.getText().toString();
                            Retrofit retrofit_textview = new Retrofit.Builder()
                                    .baseUrl("https://dmcbproject.com/demo_apps_api/api/search/"+prams+"/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            Api apicallingjson = retrofit_textview.create(Api.class);
                            Call<List<ResponseReportdatum>> searchapi_textview= apicallingjson.getSearchdata();
                            searchapi_textview.enqueue(new Callback<List<ResponseReportdatum>>() {
                                @Override
                                public void onResponse(Call<List<ResponseReportdatum>> call, Response<List<ResponseReportdatum>> response) {

                                    if(response.code()==200){

                                        List<ResponseReportdatum> data = response.body();
                                        if(!data.get(0).getPostDate().toString().isEmpty()){
                                            tv_date_thfrgmnt.setVisibility(View.VISIBLE);
                                            tv_date_thfrgmnt.setText(data.get(0).getPostDate().toString());
                                        }
                                        ThirdDataAdapter adapter_search= new ThirdDataAdapter(data,getActivity(),"searchadapter");
                                        recyclerView_search.setAdapter(adapter_search);
                                    }

                                }

                                @Override
                                public void onFailure(Call<List<ResponseReportdatum>> call, Throwable t) {

                                }
                            });
                        }

                    }
                });

                //tv_selectdate.setText(date);
            }
        };


      /*textView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {

               Log.d(TAG, "onDateSet: date is "+query);

               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {

               Log.d(TAG, "onQueryTextChange: searchvar value is "+newText);

               return true;
           }
       });*/


        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.searchid:
                selectdate();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void selectdate() {

        final Calendar myCalendar = Calendar.getInstance();
        final int year =  myCalendar.get(Calendar.YEAR);
        final int month =  myCalendar.get(Calendar.MONTH);
        final int day =  myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datedialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,setListener,year,month,day);
        datedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datedialog.show();
    }


}