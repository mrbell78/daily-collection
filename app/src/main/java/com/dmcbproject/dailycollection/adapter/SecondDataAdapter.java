package com.dmcbproject.dailycollection.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmcbproject.dailycollection.R;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SecondDataAdapter extends RecyclerView.Adapter<SecondDataAdapter.CustomClass> {


    List<ResponseReportdatum> data;
    Context context;
    String adaptergener;

    public SecondDataAdapter(List<ResponseReportdatum> data, Context context, String adaptergener) {
        this.data = data;
        this.context = context;
        this.adaptergener = adaptergener;
    }

    @NonNull
    @Override
    public CustomClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_view_managedata,parent,false);


        return new CustomClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomClass holder, int position) {

        if(adaptergener.equals("edit_report")){
            holder.btn_Edit.setVisibility(View.VISIBLE);
            holder.newdate.setVisibility(View.GONE);

            if(data.get(position).getbCode()!=null){
                holder.tv_date.setText(data.get(position).getbCode().toString());
                Log.d(TAG, "onBindViewHolder: the date is "+ data.get(position).getPostDate());
            }else{
                holder.tv_date.setText("Null");
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



            holder.btn_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View customdialog = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog,null);
                    TextView tv_date_cdialog;
                    EditText edt_micollection_cdialog,edt_deposit_cdialog;
                    Button btn_submit;

                    tv_date_cdialog=customdialog.findViewById(R.id.select_dateid_customdialog);
                    edt_deposit_cdialog=customdialog.findViewById(R.id.edt_depo_collectionid_customdialog);
                    edt_micollection_cdialog=customdialog.findViewById(R.id.edt_mi_collectionid_customdialog);

                    btn_submit=customdialog.findViewById(R.id.btn_submit_customdialog);


                    if(data.get(position).getPostDate()!=null){
                        tv_date_cdialog.setText(data.get(position).getPostDate().toString());
                        Log.d(TAG, "onBindViewHolder: the date is "+ data.get(position).getPostDate());
                    }else{
                        tv_date_cdialog.setText("Null");
                    }

                    if(data.get(position).getMiCollection()!=null){
                        edt_micollection_cdialog.setText("Mi  :"+data.get(position).getMiCollection());
                    }else{
                        edt_micollection_cdialog.setText("Null");
                    }


                    if(data.get(position).getDepositCollection()!=null){
                        edt_deposit_cdialog.setText("Deposit  :"+data.get(position).getDepositCollection());
                    }else{
                        edt_deposit_cdialog.setText("Null");
                    }

                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    builder.setView(customdialog);
                    builder.show();
                }
            });
        }



      //  holder.tv_deposit.setText(data.get(position).getDepositCollection());


    }



    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }


    public class CustomClass extends RecyclerView.ViewHolder {

        TextView tv_date,tv_micollection,tv_deposit,newdate;
        View view;
         ImageView btn_Edit;
        public CustomClass(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            tv_date =itemView.findViewById(R.id.dateid);
            tv_micollection =itemView.findViewById(R.id.micollid);
            tv_deposit=itemView.findViewById(R.id.depositid);
           // tv_date =itemView.findViewById(R.id.dateid);
            btn_Edit =itemView.findViewById(R.id.btn_edit);
            newdate=itemView.findViewById(R.id.fbdateid);

        }
    }
}
