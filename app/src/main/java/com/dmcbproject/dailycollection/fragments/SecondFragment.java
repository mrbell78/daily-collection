package com.dmcbproject.dailycollection.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.activity.MainActivity;
import com.dmcbproject.dailycollection.adapter.SecondDataAdapter;
import com.dmcbproject.dailycollection.apicall.Api;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class SecondFragment extends Fragment implements MainActivity.DataLoadedListener {


    List<ResponseReportdatum> bodydataarr;
    RecyclerView recyclerView;
    Dialog customDialog;
    String branchcode="";
    TextView tv_fixdate;
    public SecondFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);


        recyclerView=view.findViewById(R.id.recylerviewid);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bodydataarr=new ArrayList<>();
        tv_fixdate=view.findViewById(R.id.dateid_parent);

        SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  branchnamedata = prefs.getString("branchname", "failed");
        branchcode=prefs.getString("b_onlycode","null");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/demo_apps_api/api/report/"+branchcode+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apicallingjson = retrofit.create(Api.class);
        Call<List<ResponseReportdatum>> callcoment= apicallingjson.getReportdata();

        callcoment.enqueue(new Callback<List<ResponseReportdatum>>() {
            @Override
            public void onResponse(Call<List<ResponseReportdatum>> call, Response<List<ResponseReportdatum>> response) {

                List<ResponseReportdatum> data  = response.body();

                bodydataarr.addAll( response.body());
                if(bodydataarr.get(0).getPostDate()!=null){
                    tv_fixdate.setVisibility(View.VISIBLE);
                    tv_fixdate.setText("Date : "+bodydataarr.get(0).getPostDate().toString());
                }else{
                    tv_fixdate.setVisibility(View.GONE);
                }
                SecondDataAdapter adapter  = new SecondDataAdapter(bodydataarr,view.getContext(),"edit_report");
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "onResponse: the value is "+bodydataarr.size());

            }

            @Override
            public void onFailure(Call<List<ResponseReportdatum>> call, Throwable t) {

            }
        });

        if(bodydataarr!=null){



        }else{
            Log.d(TAG, "onCreateView: the data is null ");
        }

        return view;
    }


    @Override
    public void onDataLoaded(List<ResponseReportdatum> bodydataarr) {
        this.bodydataarr=bodydataarr;

    }
}