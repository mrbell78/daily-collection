package com.dmcbproject.dailycollection.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ThirdDataAdapter extends RecyclerView.Adapter<ThirdDataAdapter.CustomClass> {


    List<ResponseReportdatum> data;
    Context context;
    String adaptergener;

    public ThirdDataAdapter(List<ResponseReportdatum> data, Context context, String adaptergener) {
        this.data = data;
        this.context = context;
        this.adaptergener = adaptergener;
    }

    @NonNull
    @Override
    public CustomClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_view_search,parent,false);


        return new CustomClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomClass holder, int position) {

        if(data.get(position).getbCode()!=null){
            holder.tv_brcode.setText(data.get(position).getbCode().toString());
            Log.d(TAG, "onBindViewHolder: the date is "+ data.get(position).getbCode());
        }else{
            holder.tv_brcode.setText("Null");
        }
        if(data.get(position).getMiCollection()!=null){
            holder.tv_micollection.setText(data.get(position).getMiCollection());
        }else{
            holder.tv_micollection.setText("Null");
        }

        if(data.get(position).getDepositCollection()!=null){
            holder.tv_deposit.setText(data.get(position).getDepositCollection());
        }else{
            holder.tv_deposit.setText("Null");
        }


      //  holder.tv_deposit.setText(data.get(position).getDepositCollection());


    }



    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }


    public class CustomClass extends RecyclerView.ViewHolder {

        TextView tv_brcode,tv_micollection,tv_deposit;
        View view;
         ImageView btn_Edit;
        public CustomClass(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            tv_brcode =itemView.findViewById(R.id.br_codeid);
            tv_micollection =itemView.findViewById(R.id.micollid_search);
            tv_deposit=itemView.findViewById(R.id.depositid_search);


        }
    }
}
